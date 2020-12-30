package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

//import com.sun.media.jfxmedia.events.NewFrameEvent;

import entity.Employee;
import entity.EntityConstants;
import entity.Order;
import entity.ParameterUpdate;
import entity.Park;
import entity.ParkDiscount;
import entity.ParkCapacityReport;
import entity.Subscriber;
import entity.Visitor;
import entity.VisitorReport;
import message.ServerMessage;
import message.ServerMessageType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entity.EntityConstants.OrderStatus;
import entity.EntityConstants.OrderType;
import entity.EntityConstants.ParkParameter;
import entity.EntityConstants.RequestStatus;

/*
 * class that holds static methods related to database actions such as connection, queries,updates and more
 */
public class MySQLConnection {
	private static Connection con;

	public static void connectToDB()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		System.out.println("Driver definition succeed");
		con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/S7BzDq6Xs6?serverTimezone=IST",
				"S7BzDq6Xs6", "puC0UgMgeM");
		System.out.println("SQL connection succeed");
	}

	/*
	 * 
	 */
	public static Visitor validateVisitor(String id) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM visitor where id=? LIMIT 1;");
		logInPreparedStatement.setString(1, id);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Visitor(rs.getString(1));
		} else {
			PreparedStatement insertVisitorPreparedStatement;
			insertVisitorPreparedStatement = con.prepareStatement("INSERT INTO visitor (id) VALUES (?);");
			insertVisitorPreparedStatement.setString(1, id);
			insertVisitorPreparedStatement.executeUpdate();
			return new Visitor(id);
		}
	}

	public static Subscriber validateSubscriber(String subNum) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM subscriber where subNum=? LIMIT 1;");
		logInPreparedStatement.setString(1, subNum);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Subscriber(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), Integer.parseInt(rs.getString(7)), rs.getString(8),
					rs.getString(9).contentEquals("0") ? false : true);
		}
		return null;
	}

	public static Employee validateEmployee(String[] idAndPassword) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("SELECT * FROM employee where employeeNum=? AND password=? LIMIT 1;");
		logInPreparedStatement.setString(1, idAndPassword[0]);
		logInPreparedStatement.setString(2, idAndPassword[1]);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					EntityConstants.EmployeeRole.valueOf(rs.getString(6)), rs.getString(8));
		}
		return null;
	}

	public static List<Park> getParks() throws SQLException {
		ArrayList<Park> parkList = new ArrayList<>();
		Statement getParkStatement;
		getParkStatement = con.createStatement();
		ResultSet rs = getParkStatement.executeQuery("SELECT * FROM park;");
		while (rs.next()) {
			parkList.add(new Park(rs.getString(1), Integer.parseInt(rs.getString(2)), Integer.parseInt(rs.getString(3)),
					Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5))));
		}
		return parkList;
	}

	private static Park getCertainPark(String parkName) throws NumberFormatException, SQLException {
		PreparedStatement getParkStatement;
		getParkStatement = con.prepareStatement("SELECT * FROM park where parkName=?;");
		getParkStatement.setString(1, parkName);
		ResultSet rs = getParkStatement.executeQuery();
		if (rs.next()) {
			return new Park(rs.getString(1), Integer.parseInt(rs.getString(2)), Integer.parseInt(rs.getString(3)),
					Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5)));
		}
		return null;
	}

	public static Map<String, VisitorReport> getVisitorReport() throws SQLException {
		Map<String, VisitorReport> reportVisitorMap = new HashMap<String, VisitorReport>();
		// List<VisitorReport> reportVisitorList = new ArrayList<>();
		Statement getReportVisitorStatement;
		getReportVisitorStatement = con.createStatement();
		ResultSet rs = getReportVisitorStatement.executeQuery(
				"SELECT  orders.parkName_fk,orders.type,sum(finishedOrders.actualNumOfVisitors ) as sumvisit "
						+ "FROM orders" + " JOIN finishedOrders ON (orders.orderNum = finishedOrders.orderNum_fk) "
						+ "WHERE (MONTH(NOW()) = MONTH(orders.dateOfOrder)) AND (YEAR(NOW()) = YEAR(orders.dateOfOrder)) "
						+ "GROUP by orders.type, orders.parkName_fk");
		List<Park> parkList = getParks();
		for (Park p : parkList)
			reportVisitorMap.put(p.getParkName(), new VisitorReport(p.getParkName()));
		if (!rs.next()) {
			return reportVisitorMap;
		}
		String namePark = rs.getString(1);
		do {
			if (!rs.getString(1).equals(namePark))
				namePark = rs.getString(1);
			if (rs.getString(2).equals("GUIDE"))
				reportVisitorMap.get(namePark).setCountGuid(Integer.parseInt(rs.getString(3)));
			if (rs.getString(2).equals("SUBSCRIBER"))
				reportVisitorMap.get(namePark).setCountSubscriber(Integer.parseInt(rs.getString(3)));
			if (rs.getString(2).equals("REGULAR"))
				reportVisitorMap.get(namePark).setCountRegular(Integer.parseInt(rs.getString(3)));
		} while (rs.next());
		return reportVisitorMap;

	}

	public static Map<Integer, VisitorReport> getVisitionReport(String namePark) throws SQLException {
		Map<Integer, VisitorReport> reportVisitorMap = new LinkedHashMap<Integer, VisitorReport>();

		PreparedStatement GetIncomeReport;
		GetIncomeReport = con.prepareStatement(
				"SELECT orders.type,sum(finishedOrders.visitDuration)/sum(finishedOrders.actualNumOfVisitors ),finishedOrders.actualTimeOfArrival "
						+ "FROM orders" + " JOIN finishedOrders ON (orders.orderNum = finishedOrders.orderNum_fk) "
						+ "WHERE (MONTH(NOW()) = MONTH(orders.dateOfOrder)) AND (YEAR(NOW()) = YEAR(orders.dateOfOrder)) and orders.parkName_fk=?"
						+ " GROUP by orders.type ,finishedOrders.actualTimeOfArrival");
		GetIncomeReport.setString(1, namePark);
		ResultSet rs = GetIncomeReport.executeQuery();
		for (int i = EntityConstants.PARK_OPEN; i <= EntityConstants.PARK_CLOSED; i++) {
			reportVisitorMap.put(i, new VisitorReport(i));
		}

		if (!rs.next()) {
			return reportVisitorMap;
		}

		do {
			String temp = rs.getString(3);
			temp = temp.substring(0, 2);
			if (rs.getString(1).equals("GUIDE"))
				reportVisitorMap.get(Integer.parseInt(temp)).setAvgGuid(Double.parseDouble(rs.getString(2)));
			if (rs.getString(1).equals("SUBSCRIBER"))
				reportVisitorMap.get(Integer.parseInt(temp)).setAvgSubscriber(Double.parseDouble(rs.getString(2)));
			if (rs.getString(1).equals("REGULAR"))
				reportVisitorMap.get(Integer.parseInt(temp)).setAvgRegular(Double.parseDouble(rs.getString(2)));
		} while (rs.next());
		return reportVisitorMap;

	}

	public static Map<String, VisitorReport> getCancellationReport() throws SQLException {
		Map<String, VisitorReport> reportVisitorMap = new HashMap<String, VisitorReport>();
		Statement getCancellationReport;
		getCancellationReport = con.createStatement();
		ResultSet rs = getCancellationReport.executeQuery("SELECT  orders.parkName_fk,COUNT(*) FROM orders "
				+ "WHERE (MONTH(NOW()) = MONTH(orders.dateOfOrder)) AND (YEAR(NOW()) = YEAR(orders.dateOfOrder)) AND "
				+ "(orders.status='APPROVED' AND CURDATE()>=orders.dateOfOrder ) GROUP by orders.parkName_fk");
		List<Park> parkList = getParks();
		for (Park p : parkList)
			reportVisitorMap.put(p.getParkName(), new VisitorReport(p.getParkName()));
		if (rs.next()) {
			String namePark = rs.getString(1);
			do {
				reportVisitorMap.get(namePark).setCountNotRealized((Integer.parseInt(rs.getString(2))));

			} while (rs.next());

		}
		rs = getCancellationReport.executeQuery("SELECT orders.parkName_fk,COUNT(*) FROM orders "
				+ "WHERE (MONTH(NOW()) = MONTH(orders.dateOfOrder)) AND (YEAR(NOW()) = YEAR(orders.dateOfOrder)) "
				+ "AND orders.status='CANCELLED' GROUP by orders.parkName_fk");
		if (rs.next()) {
			String namePark = rs.getString(1);
			do {
				reportVisitorMap.get(namePark).setCountCancellations((Integer.parseInt(rs.getString(2))));

			} while (rs.next());

		}
		return reportVisitorMap;
	}

	public static List<ParkCapacityReport> getParkCapacityReport(String parkName) throws SQLException {
		List<ParkCapacityReport> dateList = new ArrayList<>();
		PreparedStatement getParkCapacityReport;
		getParkCapacityReport = con.prepareStatement(
				"SELECT parkFull.dateFull,parkFull.timeFull FROM parkFull WHERE (MONTH(NOW()) = MONTH(parkFull.dateFull)) "
						+ "AND (YEAR(NOW()) = YEAR(parkFull.dateFull)) AND parkFull.parkName_fk=?");
		getParkCapacityReport.setString(1, parkName);
		ResultSet rs = getParkCapacityReport.executeQuery();
		while (rs.next()) {
			dateList.add(new ParkCapacityReport(rs.getString(1), rs.getString(2)));
		}
		return dateList;

	}

	public static String getIncomeReport(String namePark) throws SQLException {
		PreparedStatement GetIncomeReport;
		GetIncomeReport = con.prepareStatement("SELECT sum(finishedOrders.actualPrice ) " + "FROM finishedOrders "
				+ "JOIN orders ON (orders.orderNum = finishedOrders.orderNum_fk) "
				+ "WHERE (MONTH(NOW()) = MONTH(orders.dateOfOrder)) AND (YEAR(NOW()) = YEAR(orders.dateOfOrder)) "
				+ "AND orders.parkName_fk=?" + "GROUP by orders.parkName_fk");
		GetIncomeReport.setString(1, namePark);
		ResultSet rs = GetIncomeReport.executeQuery();

		if (rs.next()) {
			String amount = rs.getString(1);
			return amount;
		}
		return "0";

	}

	public static boolean validateDate(Order orderToValidate)
			throws NumberFormatException, SQLException, ParseException {
		Park park = getCertainPark(orderToValidate.getParkName());
		if (park == null)
			throw new SQLException();
		String[] splitTime = orderToValidate.getTimeOfOrder().split(":");
		Map<Integer, Integer> parkCapacity = parkCapacityForDuration(park, orderToValidate.getDateOfOrder(),
				orderToValidate.getTimeOfOrder(), orderToValidate.getTimeOfOrder());
		for (int hour : parkCapacity.keySet()) {
			if (hour < Integer.parseInt(splitTime[0]))
				continue;
			if (orderToValidate.getNumOfVisitors() + parkCapacity.get(hour) > park.getParkMaxVisitorsDefault()
					- park.getParkDiffFromMax())
				return false;
		}
		return true;
	}

	private static Map<Integer, Integer> parkCapacityForDuration(Park park, String date, String startTime,
			String finishTime) throws NumberFormatException, SQLException {
		String[] splitTimeStart = startTime.split(":");
		String[] splitTimeFinish = finishTime.split(":");
		if (Integer.parseInt(splitTimeStart[0]) - park.getParkVisitDuration() < EntityConstants.PARK_OPEN)
			startTime = "08:00:00";
		else {
			startTime = String.valueOf(Integer.parseInt(splitTimeStart[0]) - park.getParkVisitDuration()) + ":00:00";
		}
		finishTime = String.valueOf(Integer.parseInt(splitTimeFinish[0]) + park.getParkVisitDuration() - 1) + ":00:00";
		Map<Integer, Integer> parkCapacity = new LinkedHashMap<Integer, Integer>();
		String query = "SELECT timeOfOrder,SUM(numOfVisitors) FROM orders WHERE (status='ACTIVE' OR status='PENDING_APPROVAL_FROM_WAITING_LIST'"
				+ " OR status='PENDING_FINAL_APPROVAL' OR status='APPROVED') AND orders.dateOfOrder=?"
				+ " AND timeOfOrder>=? AND timeOfOrder <=? AND parkName_fk=? GROUP BY timeOfOrder;";
		PreparedStatement validateDatePrepStmt = con.prepareStatement(query);
		validateDatePrepStmt.setString(1, date);
		validateDatePrepStmt.setString(2, startTime);
		validateDatePrepStmt.setString(3, finishTime);
		validateDatePrepStmt.setString(4, park.getParkName());
		ResultSet rs = validateDatePrepStmt.executeQuery();
		Map<Integer, Integer> timeOfOrderForHour = new HashMap<Integer, Integer>();
		while (rs.next()) {
			timeOfOrderForHour.put(Integer.parseInt(rs.getString(1).split(":")[0]), Integer.parseInt(rs.getString(2)));
		}
		query = "SELECT actualTimeOfArrival,SUM(actualNumOfVisitors) FROM finishedOrders WHERE orderNum_fk IN (SELECT orderNum FROM orders WHERE orders.dateOfOrder=? AND parkName_fk=?) AND actualTimeOfLeave IS NOT NULL GROUP BY actualTimeOfArrival";
		PreparedStatement validateDateInParkPrepStmt = con.prepareStatement(query);
		validateDateInParkPrepStmt.setString(1, date);
		validateDateInParkPrepStmt.setString(2, park.getParkName());
		rs = validateDateInParkPrepStmt.executeQuery();
		while (rs.next()) {
			if (timeOfOrderForHour.containsKey(Integer.parseInt(rs.getString(1).split(":")[0]))) {
				int newAmount = timeOfOrderForHour.get(Integer.parseInt(rs.getString(1).split(":")[0]))
						+ Integer.parseInt(rs.getString(2));
				timeOfOrderForHour.replace(Integer.parseInt(rs.getString(1).split(":")[0]), newAmount);
			} else

				timeOfOrderForHour.put(Integer.parseInt(rs.getString(1).split(":")[0]),
						Integer.parseInt(rs.getString(2)));
		}
		for (int i = Integer.parseInt(startTime.split(":")[0]); i <= Integer.parseInt(finishTime.split(":")[0]); i++) {
			int sum = 0;
			for (int j = park.getParkVisitDuration() - 1; j >= 0; j--) {
				if (timeOfOrderForHour.containsKey(i - j))
					sum += timeOfOrderForHour.get(i - j);
			}
			parkCapacity.put(i, sum);
		}

		return parkCapacity;
	}

	private static double calculateOrder(Order orderToRequest, boolean isOccasional, Boolean payInAdvance)
			throws SQLException {
		double priceForTicket = EntityConstants.TICKET_PRICE;

		double priceForOrder;
		if (orderToRequest.getType().equals(EntityConstants.OrderType.GUIDE)) {
			if (!isOccasional) {
				priceForTicket *= 0.75;
				if (payInAdvance.booleanValue())
					priceForTicket *= 0.88;
				priceForOrder = (orderToRequest.getNumOfVisitors() - 1) * priceForTicket;
			} else {
				priceForTicket *= 0.9;
				priceForOrder = (orderToRequest.getNumOfVisitors()) * priceForTicket;
			}

		} else {
			if (!isOccasional)
				priceForTicket *= 0.85;
			if (orderToRequest.getType().equals(EntityConstants.OrderType.REGULAR))
				priceForOrder = (orderToRequest.getNumOfVisitors()) * priceForTicket;
			else {
				String query = "SELECT familyMembers FROM subscriber WHERE id_fk=?";
				PreparedStatement familyMembersStatement = con.prepareStatement(query);
				familyMembersStatement.setString(1, orderToRequest.getId());
				ResultSet rs = familyMembersStatement.executeQuery();
				if (rs.next()) {
					int familyMembers = Integer.parseInt(rs.getString(1));
					if (familyMembers >= orderToRequest.getNumOfVisitors()) {
						priceForOrder = (orderToRequest.getNumOfVisitors()) * priceForTicket * 0.8;

					} else {
						priceForOrder = (familyMembers) * priceForTicket * 0.8
								+ (orderToRequest.getNumOfVisitors() - familyMembers) * priceForTicket;
					}
				} else
					priceForOrder = (orderToRequest.getNumOfVisitors()) * priceForTicket;
			}

		}
		String query = "SELECT discountAmount FROM discounts WHERE startDate<=? AND finishDate>=? AND parkName_fk=? AND status='APPROVED'";
		PreparedStatement discountStatement = con.prepareStatement(query);
		discountStatement.setString(1, orderToRequest.getDateOfOrder());
		discountStatement.setString(2, orderToRequest.getDateOfOrder());
		discountStatement.setString(3, orderToRequest.getParkName());
		ResultSet rs = discountStatement.executeQuery();
		while (rs.next())
			priceForOrder = priceForOrder * ((100 - Integer.parseInt(rs.getString(1))) / 100);
		return priceForOrder;

	}

	private static Order insertNewOrder(Order orderToInsert, OrderStatus orderStatus, boolean isOccasional,
			Boolean payInAdvance) throws SQLException {
		PreparedStatement insertOrderStatement = con.prepareStatement(
				"INSERT INTO orders (id_fk,parkName_fk,orderCreationDate,numOfVisitors,status,type,dateOfOrder, timeOfOrder, price,email,phone) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNow = formatter.format(date);
		insertOrderStatement.setString(1, orderToInsert.getId());
		insertOrderStatement.setString(2, orderToInsert.getParkName());
		insertOrderStatement.setString(3, dateNow);
		insertOrderStatement.setString(4, String.valueOf(orderToInsert.getNumOfVisitors()));
		insertOrderStatement.setString(5, orderStatus.name());
		insertOrderStatement.setString(6, orderToInsert.getType().name());
		insertOrderStatement.setString(7, orderToInsert.getDateOfOrder());
		insertOrderStatement.setString(8, orderToInsert.getTimeOfOrder());
		double price = calculateOrder(orderToInsert, isOccasional, payInAdvance);
		insertOrderStatement.setString(9, String.valueOf((int) price));
		insertOrderStatement.setString(10, orderToInsert.getEmail());
		insertOrderStatement.setString(11, orderToInsert.getPhone());
		insertOrderStatement.executeUpdate();
		String query = "SELECT orderNum FROM orders where id_fk=? AND parkName_fk=? AND orderCreationDate=?;";
		PreparedStatement getOrderNumStatement = con.prepareStatement(query);
		getOrderNumStatement.setString(1, orderToInsert.getId());
		getOrderNumStatement.setString(2, orderToInsert.getParkName());
		getOrderNumStatement.setString(3, dateNow);
		ResultSet rs = getOrderNumStatement.executeQuery();
		if (rs.next()) {
			orderToInsert.setOrderNum(rs.getString(1));
			orderToInsert.setOrderCreationDate(dateNow);
			orderToInsert.setPrice((int) price);
			orderToInsert.setStatus(orderStatus);
			return orderToInsert;
		}
		throw new SQLException();
	}

	public static Order createOrder(Order orderRequest, Boolean payInAdvance)
			throws SQLException, NumberFormatException, ParseException {
		if (validateDate(orderRequest)) {
			return insertNewOrder(orderRequest, OrderStatus.ACTIVE, false, payInAdvance);
		}
		return null;
	}

	// liron
	public static ParameterUpdate createParameterUpdate(ParameterUpdate parameterUpdate) throws SQLException {
		PreparedStatement parameterPreparedStatement = con
				.prepareStatement("INSERT INTO parameterUpdate (parameter,newValue,parkName_fk) VALUES (?,?,?);");
		parameterPreparedStatement.setString(1, parameterUpdate.getParameter());
		parameterPreparedStatement.setInt(2, parameterUpdate.getNewValue());
		parameterPreparedStatement.setString(3, parameterUpdate.getParkName());
		parameterPreparedStatement.executeUpdate();
		return parameterUpdate;
	}

	public static List<ParameterUpdate> getParameterRequests() throws SQLException {// liron
		List<ParameterUpdate> parametersUpdateRequestList = new ArrayList<>();
		String query = "Select * From parameterUpdate ;";
		PreparedStatement parametersRequests = con.prepareStatement(query);
		ResultSet rs = parametersRequests.executeQuery();
		while (rs.next()) {
			ParameterUpdate tmpParameter = new ParameterUpdate(ParkParameter.valueOf(rs.getString(1)), rs.getInt(2),
					rs.getString(3));
			parametersUpdateRequestList.add(tmpParameter);
		}
		return parametersUpdateRequestList;
	}

	public static Order enterWaitingist(Order orderRequest, Boolean payInAdvanceWaitingList)
			throws SQLException, NumberFormatException, ParseException {
		return insertNewOrder(orderRequest, OrderStatus.WAITING, false, payInAdvanceWaitingList);
	}

	public static ParkDiscount insertNewDiscountRequest(ParkDiscount newDiscountRequest)
			throws SQLException, ParseException {
		PreparedStatement insertDiscountRequestStatement = con.prepareStatement(
				"INSERT INTO discounts (parkName_fk,startDate,finishDate,discountAmount,status,employeeId) VALUES (?,?,?,?,?,?);");
		insertDiscountRequestStatement.setString(1, newDiscountRequest.getParkName());
		insertDiscountRequestStatement.setString(2, newDiscountRequest.getStartDate());
		insertDiscountRequestStatement.setString(3, newDiscountRequest.getFinishDate());
		insertDiscountRequestStatement.setInt(4, newDiscountRequest.getDiscountAmount());
		insertDiscountRequestStatement.setString(5, newDiscountRequest.getDiscountStatus().name());
		insertDiscountRequestStatement.setString(6, newDiscountRequest.getEmployeeNumber());
		insertDiscountRequestStatement.executeUpdate();

		return newDiscountRequest;
	}

	public static Map<String, List<String>> getAvailableDates(Order order)
			throws ParseException, NumberFormatException, SQLException {
		Park park = getCertainPark(order.getParkName());
		Map<String, List<String>> dateMap = new LinkedHashMap<>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(order.getDateOfOrder());
		String startHour = EntityConstants.PARK_OPEN + ":00:00";
		if (EntityConstants.PARK_OPEN < 10)
			startHour = "0" + startHour;
		String finishHour = EntityConstants.PARK_CLOSED + ":00:00";
		if (EntityConstants.PARK_CLOSED < 10)
			finishHour = "0" + finishHour;
		for (int i = 0; i < 7; i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, i); // minus number would decrement the days
			Date nextDate = cal.getTime();
			dateMap.put(format.format(nextDate), new ArrayList<String>());
			Map<Integer, Integer> parkCapacity;
			Date today = new Date();
			LocalTime localTime = today.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			boolean isToday = false;
			if (!format.format(nextDate).equals(format.format(today)))
				parkCapacity = parkCapacityForDuration(park, format.format(nextDate), startHour, finishHour);
			else {
				isToday = true;
				String startHourForToday = (localTime.getHour() + 1) + ":00+:00";
				if (localTime.getHour() + 1 < 10)
					startHourForToday = "0" + startHourForToday;
				parkCapacity = parkCapacityForDuration(park, format.format(nextDate), startHourForToday, finishHour);
			}
			for (int hour : parkCapacity.keySet()) {
				if (isToday) {
					if (hour < localTime.getHour() + 1)
						continue;
				}
				if (hour > EntityConstants.PARK_CLOSED)
					continue;
				int maxCapacity = parkCapacity.get(hour);
				for (int j = 1; j < park.getParkVisitDuration(); j++) {
					if (parkCapacity.get(hour + j) != null) {
						if (maxCapacity < parkCapacity.get(hour + j))
							maxCapacity = parkCapacity.get(hour + j);
					}
				}
				if (order.getNumOfVisitors() + maxCapacity <= park.getParkMaxVisitorsDefault()
						- park.getParkDiffFromMax()) {
					String hourFree = hour + ":00:00";
					if (hour < 10)
						hourFree = "0" + hourFree;
					dateMap.get(format.format(nextDate)).add(hourFree);
				}
			}
		}
		return dateMap;
	}

	public static Object OccasionalcreateOrder(Order order) throws SQLException, NumberFormatException, ParseException {

		if (order.getType().equals(OrderType.REGULAR)) {
			Visitor visitor = validateVisitor(order.getId());
		}
		if (order.getType().equals(OrderType.SUBSCRIBER)) {
			Subscriber subscriber = validateSubscriber(order.getId());
			if (subscriber == null)
				return "Subscriber Number" + order.getId() + " " + "is not in the system";
			order.setId(subscriber.getID());
		}
		if (order.getType().equals(OrderType.GUIDE)) {
			Subscriber subscriber = validateSubscriber(order.getId());
			if (subscriber == null)
				return "Subscriber Number" + order.getId() + " " + "is not in the system";
			if (!subscriber.getIsGuide())
				return "Subscriber Number" + order.getId() + " " + "is not a guide";
			order.setId(subscriber.getID());
		}
		Park park=(Park)getCertainPark(order.getParkName());
		if(park==null)
			return null;
		if (park.getParkCurrentVisitors()+order.getNumOfVisitors()<park.getParkMaxVisitorsDefault()) {
			return insertNewOrder(order, OrderStatus.APPROVED, true, new Boolean(false));
		}
		return null;
	}
	public static List<Order> getUnfinishedOrdersById(String id) throws SQLException {
		List<Order> orders = new ArrayList<Order>();
		String query = "Select * From orders where id_fk=? AND (status='WAITING' OR status='PENDING_APPROVAL_FROM_WAITING_LIST' OR status='ACTIVE' OR status='PENDING_FINAL_APPROVAL');";
		PreparedStatement getOrdersForId = con.prepareStatement(query);
		getOrdersForId.setString(1, id);
		ResultSet rs = getOrdersForId.executeQuery();
		while (rs.next()) {
			Order tmpOrder = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					OrderStatus.valueOf(rs.getString(6)), OrderType.valueOf(rs.getString(7)), rs.getString(8),
					rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12));
			orders.add(tmpOrder);
		}
		return orders;
	}

	public static Boolean changeOrderStatus(String orderNum, OrderStatus newStatus) throws SQLException {
		String query = "Update orders SET status=? WHERE orderNum=?";
		String queryDelSms = "DELETE FROM smsSend WHERE orderNum_fk=?";
		PreparedStatement delSms = con.prepareStatement(queryDelSms);
		PreparedStatement approveOrder = con.prepareStatement(query);
		delSms.setString(1, orderNum);
		approveOrder.setString(1, newStatus.name());
		approveOrder.setString(2, orderNum);
		approveOrder.executeUpdate();
		delSms.executeUpdate();
		return true;
	}

	public static Order activateOrderFromWatingList(Order order) throws SQLException {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTimeOfOrder = LocalDateTime.parse(order.getDateOfOrder() + " " + order.getTimeOfOrder(),
				formatter);
		OrderStatus newStatus;
		boolean res;
		long diff = ChronoUnit.MINUTES.between(now, dateTimeOfOrder);
		if (diff < 24 * 60) {
			if (diff < 2 * 60) {
				res = changeOrderStatus(order.getOrderNum(), OrderStatus.APPROVED);
				newStatus = OrderStatus.APPROVED;
			} else {
				res = changeOrderStatus(order.getOrderNum(), OrderStatus.PENDING_FINAL_APPROVAL);
				newStatus = OrderStatus.PENDING_FINAL_APPROVAL;
				String nowString = now.format(formatter);
				String querySendSms = "INSERT INTO smsSend (orderNum_fk,smsRecviedDate,smsRecviedTime) VALUES (?,?,?)";
				PreparedStatement sendSmStatement = con.prepareStatement(querySendSms);
				sendSmStatement.setString(1, order.getOrderNum());
				sendSmStatement.setString(2, nowString.split(" ")[0]);
				sendSmStatement.setString(3, nowString.split(" ")[1]);
				sendSmStatement.executeUpdate();
			}
		} else {
			res = changeOrderStatus(order.getOrderNum(), OrderStatus.ACTIVE);
			newStatus = OrderStatus.ACTIVE;
		}
		if (!res)
			return null;
		else {
			order.setStatus(newStatus);
			return order;
		}
	}

	public static List<ParkDiscount> getDiscountRequests(String employeeId) throws SQLException {
		List<ParkDiscount> parkDiscountRequestList = new ArrayList<>();
		String query = "Select * From discounts where employeeId=? ;";
		PreparedStatement discountsRequestsForId = con.prepareStatement(query);
		discountsRequestsForId.setString(1, employeeId);
		ResultSet rs = discountsRequestsForId.executeQuery();
		while (rs.next()) {
			ParkDiscount tmp = new ParkDiscount(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
					RequestStatus.valueOf(rs.getString(5)), rs.getString(6));
			parkDiscountRequestList.add(tmp);
		}
		return parkDiscountRequestList;
	}

	public static List<ParkDiscount> getDepManagerDiscountRequests() throws SQLException {
		List<ParkDiscount> parkDiscountRequestList = new ArrayList<>();
		String query = "Select * from discounts;";
		PreparedStatement discountRequests = con.prepareStatement(query);
		ResultSet rs = discountRequests.executeQuery();
		while (rs.next()) {
			ParkDiscount tmp = new ParkDiscount(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
					RequestStatus.valueOf(rs.getString(5)), rs.getString(6));
			parkDiscountRequestList.add(tmp);
		}
		return parkDiscountRequestList;
	}

	public static Integer validateOrderAndReturnPrice(String[] idVisitorsAndParkName) throws SQLException {
		Order orderToValidate = getCurrentOrderByIDAndParkName(idVisitorsAndParkName[0], idVisitorsAndParkName[2]);
		if (orderToValidate == null)
			return null;
		if (orderToValidate.getNumOfVisitors() < Integer.parseInt(idVisitorsAndParkName[1]))
			return new Integer(-1);
		if (!updateOrderToDone(orderToValidate))
			throw new SQLException("FAILED TO UPDATE ORDER TO DONE");
		int actualPrice = (orderToValidate.getPrice() / orderToValidate.getNumOfVisitors())
				* Integer.parseInt(idVisitorsAndParkName[1]);
		SimpleDateFormat formatter = new SimpleDateFormat("HH:00:00");
		String timeNow = formatter.format(new Date());
		if (!insertFinishedOrder(orderToValidate.getOrderNum(), idVisitorsAndParkName[1], timeNow,
				String.valueOf(actualPrice)))
			throw new SQLException("FAILED TO INSERT FINISHED ORDER");
		if (!addToCurrentParkVisitors(idVisitorsAndParkName[2], Integer.parseInt(idVisitorsAndParkName[1])))
			throw new SQLException("FAILED TO UPDATE CURRENT PARK VISITORS");
		return new Integer(actualPrice);

	}

	private static Order getCurrentOrderByIDAndParkName(String id, String parkName) throws SQLException {
		Order order = null;
		String date = LocalDate.now().toString();
		String earliestTime = LocalTime.now().toString();
		String latestTime = LocalTime.now().plusMinutes(30).toString();
		String query = "Select * From orders where id_fk=? AND status='APPROVED' AND dateOfOrder=? AND timeOfOrder>=? AND timeOfOrder<=? AND parkName_fk=? ;";
		PreparedStatement getOrder = con.prepareStatement(query);
		getOrder.setString(1, id);
		getOrder.setString(2, date);
		getOrder.setString(3, earliestTime);
		getOrder.setString(4, latestTime);
		getOrder.setString(5, parkName);
		ResultSet rs = getOrder.executeQuery();
		if (rs.next())
			order = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					OrderStatus.valueOf(rs.getString(6)), OrderType.valueOf(rs.getString(7)), rs.getString(8),
					rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12));
		return order;
	}

	private static boolean updateOrderToDone(Order order) {
		try {
			String query = "Update orders SET status='DONE' WHERE orderNum=?";
			PreparedStatement approveOrder = con.prepareStatement(query);
			approveOrder.setString(1, order.getOrderNum());
			approveOrder.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	private static boolean insertFinishedOrder(String orderNum, String numOfVisitors, String timeOfArrival,
			String price) {
		try {
			String query = "INSERT INTO finishedOrders (orderNum_fk,actualNumOfVisitors,actualTimeOfArrival,actualPrice) VALUES (?,?,?,?);";
			PreparedStatement insertFinishedOrder = con.prepareStatement(query);
			insertFinishedOrder.setString(1, orderNum);
			insertFinishedOrder.setString(2, numOfVisitors);
			insertFinishedOrder.setString(3, timeOfArrival);
			insertFinishedOrder.setString(4, price);
			insertFinishedOrder.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	private static boolean addToCurrentParkVisitors(String parkName, int amount) {
		try {
			String query = "Update park SET currentVisitors=currentVisitors+? WHERE parkName=?";
			PreparedStatement editCurrentVisitors = con.prepareStatement(query);
			editCurrentVisitors.setInt(1, amount);
			editCurrentVisitors.setString(2, parkName);
			editCurrentVisitors.executeUpdate();
			if (updateParkFull(parkName))
				return true;
			else
				return false;
		} catch (SQLException e) {
			return false;
		}
	}

	public static boolean updateParkFull(String parkName) {
		try {
			PreparedStatement checkParkFull = con
					.prepareStatement("SELECT * from park where parkName=? and maxVisitors<=currentVisitors;");
			checkParkFull.setString(1, parkName);
			ResultSet rs = checkParkFull.executeQuery();
			if (rs.next()) {
				String updateParkFullQuery = "INSERT IGNORE INTO parkFull (parkName_fk,dateFull,timeFull) VALUES (?,?,?)";
				PreparedStatement updateParkFull = con.prepareStatement(updateParkFullQuery);
				updateParkFull.setString(1, parkName);
				updateParkFull.setString(2, LocalDate.now().toString());
				Date dateNow = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("HH:00:00");
				String timeNow = formatter.format(new Date());
				updateParkFull.setString(3, timeNow);
				updateParkFull.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public static void main(String[] args) throws ParseException, InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		connectToDB();
		sendSmsToCancelOrders();

	}

	public static Boolean validateOrderAndRegisterExit(String[] idAndParkName) {
		try {
			String orderNum, timeOfArrival;
			int amountOfVisitors;
			String date = LocalDate.now().toString();
			String query = "SELECT finishedOrders.orderNum_fk,finishedOrders.actualTimeOfArrival,finishedOrders.actualNumOfVisitors"
					+ " from finishedOrders join orders on (orders.orderNum=finishedOrders.orderNum_fk) where orders.id_fk=? and orders.parkName_fk=? and orders.dateOfOrder=? and (finishedOrders.actualTimeOfLeave is null);";
			PreparedStatement getOrder = con.prepareStatement(query);
			getOrder.setString(1, idAndParkName[0]);
			getOrder.setString(2, idAndParkName[1]);
			getOrder.setString(3, date);
			ResultSet rs = getOrder.executeQuery();
			if (rs.next()) {
				orderNum = rs.getString(1);
				timeOfArrival = rs.getString(2);
				amountOfVisitors = -rs.getInt(3);
			} else
				return null;
			query = "UPDATE finishedOrders set actualTimeOfLeave=? , visitDuration=? where orderNum_fk=?;";
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			String timeNow = format.format(new Date());
			Date date1 = format.parse(timeOfArrival);
			Date date2 = format.parse(timeNow);
			long difference = (date2.getTime() - date1.getTime()) / 60000; // in minutes
			if (difference % 60 > 30)
				difference = 1 + difference / 60;
			else
				difference = difference / 60;
			String visitDuration = String.valueOf(difference);
			PreparedStatement updateFinishedOrder = con.prepareStatement(query);
			updateFinishedOrder.setString(1, timeNow);
			updateFinishedOrder.setString(2, visitDuration);
			updateFinishedOrder.setString(3, orderNum);
			updateFinishedOrder.executeUpdate();
			if (!addToCurrentParkVisitors(idAndParkName[1], amountOfVisitors))
				throw new SQLException("FAILED TO UPDATE CURRENT PARK VISITORS");
			return new Boolean(true);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean approveParameterUpdate(ParameterUpdate parameterToApprove) throws SQLException {
		String query1 = "DELETE FROM parameterUpdate WHERE parameter=? AND newValue=? AND parkName_fk=?;";
		String query2 = "UPDATE park SET maxVisitors=? WHERE parkName=?;";
		String query3 = "UPDATE park SET diffFromMax=? WHERE parkName=?;";
		String query4 = "UPDATE park SET visitDur=? WHERE parkName=?;";

		PreparedStatement deleteParameter = con.prepareStatement(query1);

		deleteParameter.setString(1, parameterToApprove.getParameter());
		deleteParameter.setInt(2, parameterToApprove.getNewValue());
		deleteParameter.setString(3, parameterToApprove.getParkName());
		PreparedStatement updateParameter;
		if (parameterToApprove.getParameter().equals("CAPACITY"))
			updateParameter = con.prepareStatement(query2);
		else {
			if (parameterToApprove.getParameter().equals("DIFFERENCE"))
				updateParameter = con.prepareStatement(query3);

			else
				updateParameter = con.prepareStatement(query4);
		}
		updateParameter.setInt(1, parameterToApprove.getNewValue());
		updateParameter.setString(2, parameterToApprove.getParkName());
		updateParameter.executeUpdate();
		deleteParameter.executeUpdate();
		return true;
	}

	public static boolean declineParameterUpdate(ParameterUpdate parameterToDecline) throws SQLException {
		String query1 = "DELETE FROM parameterUpdate WHERE parameter=? AND newValue=? AND parkName_fk=?;";
		PreparedStatement deleteParameter = con.prepareStatement(query1);

		deleteParameter.setString(1, parameterToDecline.getParameter());
		deleteParameter.setInt(2, parameterToDecline.getNewValue());
		deleteParameter.setString(3, parameterToDecline.getParkName());
		deleteParameter.executeUpdate();

		return true;
	}

	public static boolean approveDiscountUpdate(ParkDiscount discountToApprove) throws SQLException {
		String query1 = "UPDATE discounts SET status=? WHERE parkName_fk=? AND startDate=? AND finishDate=? AND discountAmount=?;";
		PreparedStatement approveDiscount = con.prepareStatement(query1);
		approveDiscount.setString(1, entity.EntityConstants.RequestStatus.APPROVED.name());
		approveDiscount.setString(2, discountToApprove.getParkName());
		approveDiscount.setString(3, discountToApprove.getStartDate());
		approveDiscount.setString(4, discountToApprove.getFinishDate());
		approveDiscount.setInt(5, discountToApprove.getDiscountAmount());
		approveDiscount.executeUpdate();

		return true;
	}

	public static ServerMessage registerSubscriber(Subscriber subscriber) throws SQLException {
		// TODO!!!!!!!!! maybe to check in the visitor table for existing id!!!!!
		PreparedStatement registerPreparedStatement;
		registerPreparedStatement = con.prepareStatement("SELECT * FROM subscriber WHERE id_fk=? ");
		registerPreparedStatement.setString(1, subscriber.getID());
		ResultSet rs = registerPreparedStatement.executeQuery();
		if (rs.next()) {
			Subscriber registered = new Subscriber(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), Integer.parseInt(rs.getString(7)), rs.getString(8),
					rs.getString(9).contentEquals("0") ? false : true);
			return new ServerMessage(ServerMessageType.REGISTRATION_FAILED, registered);
		}
		validateVisitor(subscriber.getID());
		registerPreparedStatement = con.prepareStatement("INSERT INTO subscriber "
				+ "(id_fk,firstName,lastName, phone, email, familyMembers, cardDetails,isGuide) VALUES "
				+ "(?,?,?,?,?,?,?,?);");
		// registerPreparedStatement.setString(1, subscriber.getSubscriberNumber());
		registerPreparedStatement.setString(1, subscriber.getID());
		registerPreparedStatement.setString(2, subscriber.getFirstName());
		registerPreparedStatement.setString(3, subscriber.getLastName());
		registerPreparedStatement.setString(4, subscriber.getPhone());
		registerPreparedStatement.setString(5, subscriber.getEmail());
		registerPreparedStatement.setInt(6, subscriber.getSubscriberFamilyMembers());
		registerPreparedStatement.setString(7, subscriber.getSubscriberCardDetails());
		registerPreparedStatement.setBoolean(8, subscriber.getIsGuide());
		registerPreparedStatement.executeUpdate();

		registerPreparedStatement = con.prepareStatement("SELECT * FROM subscriber WHERE id_fk=? ");
		registerPreparedStatement.setString(1, subscriber.getID());
		ResultSet rs1 = registerPreparedStatement.executeQuery();
		if (rs1.next()) {
			subscriber.setSuibscriberNum(rs1.getString(1));
		}

		return new ServerMessage(ServerMessageType.REGISTRATION_SUCCESSED, subscriber);

	}

	public static boolean declineDiscountUpdate(ParkDiscount discountToDecline) throws SQLException {
		String query1 = "UPDATE discounts SET status=? WHERE parkName_fk=? AND startDate=? AND finishDate=? AND discountAmount=?;";
		PreparedStatement declineDiscount = con.prepareStatement(query1);
		declineDiscount.setString(1, entity.EntityConstants.RequestStatus.DECLINED.name());
		declineDiscount.setString(2, discountToDecline.getParkName());
		declineDiscount.setString(3, discountToDecline.getStartDate());
		declineDiscount.setString(4, discountToDecline.getFinishDate());
		declineDiscount.setInt(5, discountToDecline.getDiscountAmount());
		declineDiscount.executeUpdate();

		return true;
	}

	public static List<Order> sendSmsToActiveOrders() throws SQLException {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime tommorow = now.plusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String nowString = now.format(formatter);
		String tommorowString = now.format(formatter);
		List<Order> orders = new ArrayList<>();
		String query = "SELECT orders.* FROM orders WHERE dateOfOrder=? AND HOUR(timeOfOrder)=? AND status='ACTIVE';";
		PreparedStatement getOrdersToSendSms = con.prepareStatement(query);
		getOrdersToSendSms.setString(1, tommorowString.split(" ")[0]);
		getOrdersToSendSms.setInt(2, tommorow.getHour());
		ResultSet rs = getOrdersToSendSms.executeQuery();
		while (rs.next()) {
			Order tmpOrder = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					OrderStatus.valueOf(rs.getString(6)), OrderType.valueOf(rs.getString(7)), rs.getString(8),
					rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12));
			orders.add(tmpOrder);
		}
		if (orders.size() > 0) {
			String queryUpdateStatus = "Update orders SET status='PENDING_FINAL_APPROVAL' WHERE dateOfOrder=? AND HOUR(timeOfOrder)=? AND status='ACTIVE'";
			PreparedStatement updateStatus = con.prepareStatement(queryUpdateStatus);
			updateStatus.setString(1, tommorowString.split(" ")[0]);
			updateStatus.setInt(2, tommorow.getHour());
			updateStatus.executeUpdate();
			PreparedStatement sendSms;
			String sendSmsQuery = "INSERT INTO smsSend (orderNum_fk,smsRecviedDate,smsRecviedTime) VALUES ";
			for (int i = 0; i < orders.size(); i++) {
				sendSmsQuery += "(" + orders.get(i).getOrderNum() + ",'"+nowString.split(" ")[0]+"','" + nowString.split(" ")[1] + "')";
				if (i != orders.size() - 1) {
					sendSmsQuery += ",";
				}
				System.out.println(orders.get(i).toString());
			}
			sendSms = con.prepareStatement(sendSmsQuery);
			sendSms.executeUpdate();
		}
		return orders;
	}

	public static List<Order> sendSmsToCancelOrders() throws SQLException {
		List<Order> orders = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String timeNow = formatter.format(new Date());
		String query = "SELECT * FROM orders JOIN smsSend on orders.orderNum=smsSend.orderNum_fk WHERE "
				+ "smsSend.smsRecviedDate=DATE(NOW()) AND ((orders.status='PENDING_FINAL_APPROVAL' AND HOUR(TIMEDIFF(?,smsSend.smsRecviedTime))>=2) OR ((orders.status='PENDING_APPROVAL_FROM_WAITING_LIST' AND HOUR(TIMEDIFF(?,smsSend.smsRecviedTime))>=1)))";
		PreparedStatement getOrdersToSendSms = con.prepareStatement(query);
		getOrdersToSendSms.setString(1, timeNow);
		getOrdersToSendSms.setString(2, timeNow);
		ResultSet rs = getOrdersToSendSms.executeQuery();
		while (rs.next()) {
			Order tmpOrder = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					OrderStatus.valueOf(rs.getString(6)), OrderType.valueOf(rs.getString(7)), rs.getString(8),
					rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12));
			orders.add(tmpOrder);
		}
		if (orders.size() > 0) {
			String queryCancelOrders = "UPDATE orders SET status='CANCELLED' WHERE ";
			String queryDelSms = "DELETE FROM smsSend WHERE ";
			for (int i = 0; i < orders.size(); i++) {
				queryCancelOrders += "orderNum=" + orders.get(i).getOrderNum();
				queryDelSms += "orderNum_fk=" + orders.get(i).getOrderNum();
				if (i != orders.size() - 1) {
					queryCancelOrders += " OR ";
					queryDelSms += " OR ";
				}
				System.out.println(orders.get(i).toString());
			}
			con.prepareStatement(queryCancelOrders).executeUpdate();
			con.prepareStatement(queryDelSms).executeUpdate();
		}
		return orders;
	}

	public static void expiredApprovedOrders() throws SQLException {
		String queryUpdateStatus = "Update orders SET status='EXPIRED' WHERE ((dateOfOrder=? AND HOUR(TIMEDIFF(?,timeOfOrder))>=1 AND ?>timeOfOrder) OR dateOfOrder<?) AND status='APPROVED'";
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date now = new Date();
		String timeNow = timeFormat.format(now);
		String dateNow = dateFormat.format(now);
		PreparedStatement expirePreparedStatement = con.prepareStatement(queryUpdateStatus);
		expirePreparedStatement.setString(1, dateNow);
		expirePreparedStatement.setString(2, timeNow);
		expirePreparedStatement.setString(3, timeNow);
		expirePreparedStatement.setString(4, dateNow);
		expirePreparedStatement.executeUpdate();
	}
}
