package mysql;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

import entity.Employee;
import entity.Order;
import entity.Subscriber;
import entity.Visitor;
import entity.VisitorReport;
import message.ServerMessage;

public interface IMySQLConnection {
	public Visitor validateVisitor(String id) throws SQLException;
	public Subscriber validateSubscriber(String subNum) throws SQLException;
	public Employee validateEmployee(String[] employeeNumberAndPassword) throws SQLException;
	public Map<Integer, VisitorReport> getVisitionReport(String namePark) throws SQLException;
	public ServerMessage registerSubscriber(Subscriber subscriber) throws SQLException;
	public Object OccasionalcreateOrder(Order order) throws SQLException, NumberFormatException, ParseException;
}
