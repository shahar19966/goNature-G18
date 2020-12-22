package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.fxml.Initializable;

public class ParkManagerVisitationReportController implements Initializable {

    @FXML
    private BarChart<?, ?> visitationReport;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	
    	XYChart.Series set1 = new XYChart.Series<> ();
    	set1.getData().add(new XYChart.Data("Subscribers",500));
    	set1.getData().add(new XYChart.Data("Groups",500));
    	set1.getData().add(new XYChart.Data("Individuals",500));
    	
    	visitationReport.getData().addAll(set1);
    	
    	
    	
    }
}

