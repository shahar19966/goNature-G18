package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.util.ResourceBundle;

import java.util.List;

import entity.Employee;
import entity.EntityConstants;
import entity.ParkCapacityReport;

import javafx.fxml.Initializable;

import message.ClientMessage;
import message.ClientMessageType;

public class ParkManagerCapacityReportController implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();

	@FXML
	private Label month;

	@FXML
	private Label parkName;

	@FXML
	private ScatterChart<String, Double> scatterChart;
	 @FXML
	private CategoryAxis x;

	    @FXML
	private NumberAxis y;

	    @FXML
	    private Label year2021;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	

		Calendar c = Calendar.getInstance();
		List<ParkCapacityReport> list = new ArrayList<>();
		String park = ((Employee) (guiControl.getUser())).getParkName();
		parkName.setText(park);
		 year2021.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
		
		guiControl.sendToServer(new ClientMessage(ClientMessageType.PARK_MNG_CAPACITY_REPORT, park));
		list = (List<ParkCapacityReport>) guiControl.getServerMsg().getMessage();
		XYChart.Series<String, Double> date = new XYChart.Series<>();

		for (int i = EntityConstants.PARK_OPEN; i <= EntityConstants.PARK_CLOSED; i++) {
			String temp = "";
			temp=i>10?temp+i+":00":temp+"0"+i+":00";

		    date.getData().add(new XYChart.Data<>(temp,1.2));
			
		}
		scatterChart.getData().add(date);
	}
}