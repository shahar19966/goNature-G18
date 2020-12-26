package gui;


import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import java.util.ResourceBundle;


import java.util.List;

import entity.Employee;
import entity.ParkCapacityReport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import message.ClientMessage;
import message.ClientMessageType;

public class ParkManagerCapacityReportController  implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();

    @FXML
    private Label month;

    @FXML
    private Label parkName;
    @FXML
    private TableView<ParkCapacityReport> tabel;

    @FXML
    private TableColumn<ParkCapacityReport, String> date;

    @FXML
    private TableColumn<ParkCapacityReport, String> time;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
   	
       Calendar c=Calendar.getInstance();
      List<ParkCapacityReport> list = new ArrayList<>();
    	  String park=((Employee) (guiControl.getUser())).getParkName();
      	parkName.setText(park);
      	month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
      	guiControl.sendToServer(new ClientMessage(ClientMessageType.PARK_MNG_CAPACITY_REPORT, park));
      	list=(List<ParkCapacityReport> ) guiControl.getServerMsg().getMessage();
      	date.setCellValueFactory(new PropertyValueFactory<ParkCapacityReport,String>("date"));  
    	time.setCellValueFactory(new PropertyValueFactory<ParkCapacityReport,String>("time"));
    	
        tabel.getItems().addAll(list);
	
      	
       }

}