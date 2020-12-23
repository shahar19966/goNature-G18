package gui;

import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

import java.util.ResourceBundle;

import entity.Employee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import message.ClientMessage;
import message.ClientMessageType;


public class ParkManagerIncomeReportController  implements Initializable{
	GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private Label parkName;

    @FXML
    private Label month;

    @FXML
    private Label total;
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) 
    {  Calendar c=Calendar.getInstance();
    String park=((Employee) (guiControl.getUser())).getParkName();
 
    	parkName.setText(park);
    	month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
    	guiControl.sendToServer(new ClientMessage(ClientMessageType.INCOME_REPORT, park));
    String temp=(String) guiControl.getServerMsg().getMessage();
    temp=temp+" "+"NIS";
		total.setText(temp);
    	
    }


}
