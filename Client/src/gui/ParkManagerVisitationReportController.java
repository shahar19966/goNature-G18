package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entity.Employee;
import entity.Park;
import entity.VisitorReport;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import message.ClientMessage;
import message.ClientMessageType;
import javafx.fxml.Initializable;

public class ParkManagerVisitationReportController implements Initializable {
	GUIControl guiControl=GUIControl.getInstance();
    @FXML
    private BarChart<?, ?> visitationReport;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	Employee emp=(Employee)guiControl.getUser();
    	guiControl.sendToServer(new ClientMessage(ClientMessageType.VISITOR_REPORT,null));
    	List<VisitorReport> parkReportArr=(List<VisitorReport>)guiControl.getServerMsg().getMessage();
    
    	VisitorReport presenPark=null;
    	for(VisitorReport vr:parkReportArr)
   	{
			if(emp.getParkName().equals(vr.getNamePark()))
			{
    		 presenPark=vr;
    		   	break;
			}
    	}
    	
    	XYChart.Series set1 = new XYChart.Series<> ();
    	set1.getData().add(new XYChart.Data("Subscribers",presenPark.getCountSubscriber()));
    	set1.getData().add(new XYChart.Data("Groups",presenPark.getCountGuid()));
    	set1.getData().add(new XYChart.Data("Individuals",presenPark.getCountRegular()));
    	
    	visitationReport.getData().addAll(set1);
   
    }
}

