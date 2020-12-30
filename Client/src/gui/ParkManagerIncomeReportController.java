package gui;

import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.chart.CategoryAxis;

import javafx.scene.chart.NumberAxis;
import entity.Employee;
import entity.VisitorReport;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import message.ClientMessage;
import message.ClientMessageType;


public class ParkManagerIncomeReportController  implements Initializable{
	GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private LineChart<String, Integer> lineChar;
    @FXML
    private Label year;

    @FXML
    private Label parkName;

    @FXML
    private Label total;

    @FXML
    private Label month;
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) 
    {  Calendar c=Calendar.getInstance();
       String park=((Employee) (guiControl.getUser())).getParkName();
       year.setText(String.valueOf(c.getInstance().get(Calendar.YEAR)));
       parkName.setText(park);
    month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
    	guiControl.sendToServer(new ClientMessage(ClientMessageType.INCOME_REPORT, park));
    	Map<Integer, VisitorReport> parkReportMap=(Map<Integer, VisitorReport> ) guiControl.getServerMsg().getMessage();
        XYChart.Series<String, Integer> price = new XYChart.Series<>();
    int total_price=0;
    int i=1;
	for(VisitorReport vr : parkReportMap.values())
	{
		price.getData().add(new XYChart.Data<>(String.valueOf(i),new Integer(vr.getPrice())));
	i++;
		total_price=total_price+vr.getPrice();
	}
	lineChar.getData().add(price);
    String totalP=String.valueOf(total_price);
		total.setText(totalP+" "+"NIS");
    	
    }


}
