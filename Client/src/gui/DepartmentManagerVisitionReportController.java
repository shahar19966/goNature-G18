package gui;

import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import entity.Park;
import entity.VisitorReport;
import gui.LoginPageController.ClientServerCommunication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessage;

/**
 * 
 * This class responsible for the control page of the department manager's
 * visition report
 *
 */

public class DepartmentManagerVisitionReportController implements Initializable {
	static GUIControl guiControl = GUIControl.getInstance();
	static IClientServerCommunication clientServerCommunication = new ClientServerCommunication();

	static class ClientServerCommunication implements IClientServerCommunication {

		@Override
		public void popUpError(String msg) {
			GUIControl.popUpError(msg);
		}

		@Override
		public void sendToServer(Object msg) {
			guiControl.sendToServer(msg);

		}

		@Override
		public ServerMessage getServerMsg() {
			return guiControl.getServerMsg();
		}

		@Override
		public void setUser(Object user) {
			guiControl.setUser(user);
		}

	}

	@FXML
	private Label month;

	@FXML
	public Label park1;

	@FXML
	public BarChart<String, Number> barPark1;

	@FXML
	private CategoryAxis xPark1;

	@FXML
	private NumberAxis yPark1;

	@FXML
	private Label park2;

	@FXML
	private BarChart<String, Number> barPark2;

	@FXML
	private CategoryAxis xPark2;

	@FXML
	private NumberAxis yPark2;

	@FXML
	private Label park3;

	@FXML
	private BarChart<String, Number> barPark3;

	@FXML
	private CategoryAxis xPark3;

	@FXML
	private NumberAxis yPark3;

	/**
	 * initialize all the Data report before the page uploaded and displayed to the
	 * user
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Calendar c = Calendar.getInstance();
		month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
		clientServerCommunication.sendToServer(new ClientMessage(ClientMessageType.GET_PARKS, null));
		List<Park> parks = (List<Park>) clientServerCommunication.getServerMsg().getMessage();

		park1.setText(parks.get(0).getParkName());
		park2.setText(parks.get(1).getParkName());
		park3.setText(parks.get(2).getParkName());
		dataBar(barPark1, parks.get(0).getParkName());
		dataBar(barPark2, parks.get(1).getParkName());
		dataBar(barPark3, parks.get(2).getParkName());

	}

	/**
	 * initialize data into a graph of the appropriate park
	 * 
	 * @param bar   -bar chart suitable for the park
	 * @param nameP - The name of the park you want to initialize the graph
	 */
	private void dataBar(BarChart<String, Number> bar, String nameP) {
		clientServerCommunication.sendToServer(new ClientMessage(ClientMessageType.DEP_MNG_VISITION_REPORT, nameP));
		Map<Integer, VisitorReport> parkReportMap = (Map<Integer, VisitorReport>) clientServerCommunication
				.getServerMsg().getMessage();
		XYChart.Series<String, Number> SubscribersP1 = new XYChart.Series<>();
		XYChart.Series<String, Number> GroupsP1 = new XYChart.Series<>();
		XYChart.Series<String, Number> IndividualsP1 = new XYChart.Series<>();
		SubscribersP1.setName("Subscribers");
		GroupsP1.setName("Groups");
		IndividualsP1.setName("Individuals");
		for (VisitorReport vr : parkReportMap.values()) {
			SubscribersP1.getData().add(new XYChart.Data<>(vr.timeSring(), (vr.getAvgSubscriber())));
			GroupsP1.getData().add(new XYChart.Data<>(vr.timeSring(), (vr.getAvgGuid())));
			IndividualsP1.getData().add(new XYChart.Data<>(vr.timeSring(), (vr.getAvgRegular())));

		}
		bar.getData().addAll(SubscribersP1, GroupsP1, IndividualsP1);
	}

	public static void setClientServerCommunication(IClientServerCommunication clientServerCommunication) {
		DepartmentManagerVisitionReportController.clientServerCommunication = clientServerCommunication;
	}
}
