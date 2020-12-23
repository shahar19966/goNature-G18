package gui;

import java.net.URL;

import java.util.Map;
import java.util.ResourceBundle;

import entity.Employee;

import entity.VisitorReport;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import message.ClientMessage;
import message.ClientMessageType;


public class ParkManagerVisitationReportController implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();
	@FXML
	private BarChart<String, Number> visitationReport;

	@FXML
	private CategoryAxis x;

	@FXML
	private NumberAxis y;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Employee emp = (Employee) guiControl.getUser();
		VisitorReport temp;
		guiControl.sendToServer(new ClientMessage(ClientMessageType.VISITOR_REPORT, null));
		Map<String, VisitorReport> parkReportMap = (Map<String, VisitorReport>) guiControl.getServerMsg().getMessage();
		//ObservableList<XYChart.Series> parkReport = FXCollections.observableArrayList();
		if (emp.getRole().equals("PARK_MANAGER")) {
			temp=parkReportMap.get(emp.getParkName());
			XYChart.Series<String, Number> park1 = new XYChart.Series<>();
			park1.setName(temp.getNamePark());
			park1.getData().add(new XYChart.Data<>("Subscribers", temp.getCountSubscriber()));
			park1.getData().add(new XYChart.Data<>("Groups", temp.getCountGuid()));
			park1.getData().add(new XYChart.Data<>("Individuals", temp.getCountRegular()));
			visitationReport.getData().add(park1);
			return;
			
		}
		for(VisitorReport vr:parkReportMap.values())
		{
			
			XYChart.Series<String, Number> park1 = new XYChart.Series<>();
			park1.setName(vr.getNamePark());
			park1.getData().add(new XYChart.Data<>("Subscribers", vr.getCountSubscriber()));
			park1.getData().add(new XYChart.Data<>("Groups", vr.getCountGuid()));
			park1.getData().add(new XYChart.Data<>("Individuals",vr.getCountRegular()));
			visitationReport.getData().add(park1);
		}

		
	}


}
