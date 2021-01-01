package gui;

import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import entity.Employee;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import entity.VisitorReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.scene.chart.XYChart;

import message.ClientMessage;
import message.ClientMessageType;


public class ParkManagerVisitationReportController implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();
	
	@FXML
    private Label year;

    @FXML
    private Label parkName;

    @FXML
    private Label total;

    @FXML
    private Label month;

  
    @FXML
    private AnchorPane lastMAncchorPane;

    @FXML
    private LineChart<?, ?> lineCharLastM;

    @FXML
    private CategoryAxis x11;

    @FXML
    private NumberAxis y11;

    @FXML
    private PieChart pieChartLastM;

    @FXML
    private Label lastM;

    @FXML
    private Label totalM;


    @FXML
    private LineChart<String, Integer> lineChar;

    @FXML
    private CategoryAxis x1;

    @FXML
    private NumberAxis y1;
    @FXML
    private PieChart pieChart;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Calendar c=Calendar.getInstance();
	       String park=((Employee) (guiControl.getUser())).getParkName();
	       year.setText(String.valueOf(c.getInstance().get(Calendar.YEAR)));
	       parkName.setText(park);
	    month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
		guiControl.sendToServer(new ClientMessage(ClientMessageType.VISITOR_REPORT, park));
		Map<Integer, VisitorReport> parkReportMap=(Map<Integer, VisitorReport> ) guiControl.getServerMsg().getMessage();
	       XYChart.Series<String, Integer> SubscribersP1 = new XYChart.Series<>();
	       XYChart.Series<String, Integer> GroupsP1 = new XYChart.Series<>();
	       XYChart.Series<String, Integer> IndividualsP1 = new XYChart.Series<>();
	        SubscribersP1.setName("Subscribers");
	        GroupsP1.setName("Groups");
	        IndividualsP1.setName("Individuals");
	        int i=1,sum=0,sumSub=0,sumGroup=0,sumIndivi=0;
	        for(VisitorReport vr : parkReportMap.values())
	    	{
	        	SubscribersP1.getData().add(new XYChart.Data<>(String.valueOf(i),new Integer(vr.getCountSubscriber())));
	        	GroupsP1.getData().add(new XYChart.Data<>(String.valueOf(i),new Integer(vr.getCountGuid())));
	        	IndividualsP1.getData().add(new XYChart.Data<>(String.valueOf(i),new Integer(vr.getCountRegular())));
			   i++;
			   sum=sum+vr.getCountSubscriber()+vr.getCountGuid()+vr.getCountRegular();
			   sumSub+=sumSub+vr.getCountSubscriber();
			   sumGroup+=vr.getCountGuid();
			   sumIndivi+=vr.getCountRegular();
			}
			lineChar.getData().addAll(SubscribersP1,GroupsP1,IndividualsP1);
			
			total.setText(String.valueOf(sum));
			ObservableList<PieChart.Data> pieCharData=FXCollections.observableArrayList(
					new PieChart.Data("Subscribers("+sumSub+")",sumSub),
					new PieChart.Data("Groups("+sumGroup+")",sumGroup),
					new PieChart.Data("Individuals("+sumIndivi+")",sumIndivi));
			pieChart.setData(pieCharData);
			
		}
	   @FXML
	    void yesBtnClick(ActionEvent event) {
		   
		   lastMAncchorPane.setVisible(true);
		   

	    }
	 
	
	

}
