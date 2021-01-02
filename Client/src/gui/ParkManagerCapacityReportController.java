package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ResourceBundle;

import java.util.List;

import entity.Employee;
import entity.EntityConstants;
import entity.ParkCapacityReport;

import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import message.ClientMessage;
import message.ClientMessageType;

public class ParkManagerCapacityReportController implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();

	@FXML
	private Label month;

	@FXML
	private Label parkName;
	

	    @FXML
	    private Label year2021;
	    @FXML
	    private ListView<String> list8;

	    @FXML
	    private ListView<String> list9;

	    @FXML
	    private ListView<String> list10;

	    @FXML
	    private ListView<String> list11;

	    @FXML
	    private ListView<String> list12;

	    @FXML
	    private ListView<String> list13;

	    @FXML
	    private ListView<String> list14;

	    @FXML
	    private ListView<String> list15;

	    @FXML
	    private ListView<String> list16;

	    @FXML
	    private ListView<String> list17;

	    @FXML
	    private ListView<String> listDate;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	

		Calendar c = Calendar.getInstance();
		
		String park = ((Employee) (guiControl.getUser())).getParkName();
		parkName.setText(park);
		 year2021.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
		int intMonth=Calendar.MONTH-1;
		int thisDay = c.get(Calendar.DAY_OF_MONTH);
		guiControl.sendToServer(new ClientMessage(ClientMessageType.PARK_MNG_CAPACITY_REPORT, park));
		Map<Integer, boolean[]> capacityMap = (Map<Integer, boolean[]> ) guiControl.getServerMsg().getMessage();
		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		ObservableList list=FXCollections.observableArrayList();
		int key=EntityConstants.PARK_OPEN;
		
		HashMap<Integer, ListView<String>> map = new LinkedHashMap<Integer, ListView<String>>();
	map.put(key++,list8);
	map.put(key++, list9);
	map.put(key++,list10);	
	map.put(key++,list11);
	map.put(key++,list12);
	map.put(key++,list13);
	map.put(key++,list14);
	map.put(key++,list15);
	map.put(key++,list16);
	map.put(key++,list17);
	 key=EntityConstants.PARK_OPEN;
		for(boolean[] b: capacityMap.values())
		{ list.removeAll(list);
		list.add(key>=10?key+":00":"0"+key+":00");
			for(int i=1;i<=thisDay;i++)
			{
				if(b[i]==false)
				{
					list.add(i<10?"0"+i+"/"+(intMonth<10?"0"+intMonth:intMonth):i+"/"+(intMonth<10?"0"+intMonth:intMonth));
				}
				else
					list.add("");
				
			}
			
			map.get(key).getItems().addAll(list);
			key++;
			if(key>EntityConstants.PARK_CLOSED)
				break;
		}
	       
	      
		
	
	}
}