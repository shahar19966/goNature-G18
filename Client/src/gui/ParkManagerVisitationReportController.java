package gui;

import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;



import entity.Employee;
import entity.ReportDate;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import entity.VisitorReport;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.scene.chart.XYChart;

import message.ClientMessage;
import message.ClientMessageType;

/**
 * 
 * This class responsible for the control page of the park manager's visit report
 *
 */
public class ParkManagerVisitationReportController implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();
	
	@FXML
    private Label year;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView icon;
    @FXML
    private Label parkName;

    @FXML
    private Label total;

    @FXML
    private Label month;

  
    @FXML
    private AnchorPane lastMAncchorPane;

    @FXML
    private LineChart<String, Integer> lineCharLastM;

    @FXML
    private CategoryAxis x11;

    @FXML
    private NumberAxis y11;

    @FXML
    private PieChart pieChartLastM;

   
    @FXML
    private Label totalM;
    @FXML
    private ComboBox<String> yearComboBox;

    @FXML
    private ComboBox<String> monthComboBox;

  
    @FXML
    private LineChart<String, Integer> lineChar;
    @FXML
    private Button yesBtn;

    @FXML
    private CategoryAxis x1;

    @FXML
    private NumberAxis y1;
    @FXML
    private PieChart pieChart;
    private ReportDate reportDate;
    
    private int currentMonth=Calendar.MONTH-1;
    private Calendar c=Calendar.getInstance();
    private int currentYear=Calendar.getInstance().get(Calendar.YEAR);
	private ObservableList<String> yearObsList = FXCollections.observableArrayList("2021", "2020");
	private ObservableList<String> monthObsList = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12");
	 /**
     * initialize all the Data report before the page uploaded and displayed to the user
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		yearComboBox.setPrefWidth(100);
		yearComboBox.setItems(yearObsList);
		monthComboBox.setPrefWidth(105);
		monthComboBox.setItems(monthObsList);
		yesBtn.disableProperty().bind(Bindings.createBooleanBinding(
				() ->  yearComboBox.getValue() ==null || monthComboBox.getValue() ==null, monthComboBox.valueProperty(),yearComboBox.valueProperty()));
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
	
	       String park=((Employee) (guiControl.getUser())).getParkName();
	       reportDate=new ReportDate(park);
	       year.setText(String.valueOf(c.getInstance().get(Calendar.YEAR)));
	       reportDate.setYear(String.valueOf(currentYear));
	       reportDate.setMonth(String.valueOf( currentMonth));
	       parkName.setText(park);
	       month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
	       setDataCurrentMonth();
			
		}
	/***
	 * 
	 * @param event click on yes btn 
	 */
	   @FXML
	    void yesBtnClick(ActionEvent event) {
		   
		   
			scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			icon.setVisible(true);
			reportDate.setYear(yearComboBox.getValue()) ;
			reportDate.setMonth(monthComboBox.getValue());
			
			if(Integer.parseInt(reportDate.getYear())==currentYear  && Integer.parseInt(reportDate.getMonth())>currentMonth )
				GUIControl.popUpError("Future date does not exist ! try again");
			
			setDataSecondReport();
		
		   

	    }
	   /**
	     * initialize all the Data report to Current Month and year
	     */

	   private void setDataCurrentMonth()
	   {
		   guiControl.sendToServer(new ClientMessage(ClientMessageType.VISITOR_REPORT, reportDate));
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
				   sumSub=sumSub+vr.getCountSubscriber();
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
		/**
	     * initialize all the Data report to month and year that the user has selected
	     */
	   private void setDataSecondReport()
	   {
		   lineCharLastM.getData().clear();
		   guiControl.sendToServer(new ClientMessage(ClientMessageType.VISITOR_REPORT, reportDate));
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
				   sumSub=sumSub+vr.getCountSubscriber();
				   sumGroup+=vr.getCountGuid();
				   sumIndivi+=vr.getCountRegular();
				}
				lineCharLastM.getData().addAll(SubscribersP1,GroupsP1,IndividualsP1);
				
				totalM.setText(String.valueOf(sum));
				ObservableList<PieChart.Data> pieCharData=FXCollections.observableArrayList(
						new PieChart.Data("Subscribers("+sumSub+")",sumSub),
						new PieChart.Data("Groups("+sumGroup+")",sumGroup),
						new PieChart.Data("Individuals("+sumIndivi+")",sumIndivi));
				pieChartLastM.setData(pieCharData);
	   }
	 
	
	

}
