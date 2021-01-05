package gui;

import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import entity.VisitorReport;
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
/*
 * 
 * This class responsible for the control page of the department manager's cancellation report
 *
 */
public class DepartmentManagerCancellationReportController  implements Initializable{
	GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private BarChart<String, Integer> cancellationReport;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private Label month;
    @FXML
	private Label year;
    /*
     * 
     * 
     * initialize all the Data report before the page uploaded and displayed to the user
     */
    @Override
   	public void initialize(URL location, ResourceBundle resources) 
       {
    	Calendar c=Calendar.getInstance();
    	month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
    	year.setText(String.valueOf(c.getInstance().get(Calendar.YEAR)));
    	
    	guiControl.sendToServer(new ClientMessage(ClientMessageType.DEP_MNG_CANCELLATION_REPORT, null));
    	Map<String, VisitorReport> parkReportMap = (Map<String, VisitorReport>) guiControl.getServerMsg().getMessage();
		
		for(VisitorReport vr : parkReportMap.values())
		{
	    Series<String, Integer> park1 = new XYChart.Series<>();
		park1.setName(vr.getNamePark());
		park1.getData().add(new XYChart.Data<>("Canceled orders",new Integer(vr.getCountCancellations())));
		park1.getData().add(new XYChart.Data<>("Unrealized orders",new Integer( vr.getCountNotRealized())));
		cancellationReport.getData().add(park1);
		}
    	
       }
}
