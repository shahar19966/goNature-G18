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

public class DepartmentManagerVisitionReportController implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();

    @FXML
    private Label month;

    @FXML
    private Label park1;

    @FXML
    private BarChart<String,Number> barPark1;

    @FXML
    private CategoryAxis xPark1;

    @FXML
    private NumberAxis yPark1;

    @FXML
    private Label park2;

    @FXML
    private BarChart<String,Number > barPark2;

    @FXML
    private CategoryAxis xPark2;

    @FXML
    private NumberAxis yPark2;

    @FXML
    private Label park3;

    @FXML
    private BarChart<String,Number> barPark3;

    @FXML
    private CategoryAxis xPark3;

    @FXML
    private NumberAxis yPark3;
  
    
    @Override
   	public void initialize(URL location, ResourceBundle resources) 
       {
    	Calendar c=Calendar.getInstance();
    	month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
    	
    	 park1.setText("1");
    	 park2.setText("2");
    	 park3.setText("3");
    	 dataBar(barPark1,"1");
    	 dataBar(barPark2, "2");
    	 dataBar(barPark3, "3");
    	
       }

    private void dataBar(BarChart<String,Number > bar,String nameP)
    {
    	guiControl.sendToServer(new ClientMessage(ClientMessageType.DEP_MNG_VISITION_REPORT, nameP));
        Map<Integer, VisitorReport> parkReportMap = (Map<Integer, VisitorReport>) guiControl.getServerMsg().getMessage();
       XYChart.Series<String, Number> SubscribersP1 = new XYChart.Series<>();
       XYChart.Series<String, Number> GroupsP1 = new XYChart.Series<>();
       XYChart.Series<String, Number> IndividualsP1 = new XYChart.Series<>();
        SubscribersP1.setName("Subscribers");
        GroupsP1.setName("Groups");
        IndividualsP1.setName("Individuals");
		for(VisitorReport vr : parkReportMap.values())
		{
			SubscribersP1.getData().add(new XYChart.Data<>(vr.timeSring(),(vr.getAvgSubscriber())/60));
		   GroupsP1.getData().add(new XYChart.Data<>(vr.timeSring(), (vr.getAvgGuid())/60));
		   IndividualsP1.getData().add(new XYChart.Data<>(vr.timeSring(), (vr.getAvgRegular())/60));
		
		}
		bar.getData().addAll(SubscribersP1,GroupsP1,IndividualsP1);
    }
}
