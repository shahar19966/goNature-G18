package gui;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import entity.Employee;
import entity.Park;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;

public class ParkManagerParametersUpdateController implements Initializable{
	GUIControl guiControl = GUIControl.getInstance();

	   @FXML
	    private Label labelCapacity;
	   @FXML
	    private Label labelDifference;
	   @FXML
	    private Label labelVisitationTime;
    @FXML
    private Button updateCapacity;

    @FXML
    private Button updateDifference;

    @FXML
    private Button updateVisitationTime;

    @FXML 
    void capacityUpdate(ActionEvent event) {
    	Parent root ;
        try {
        	 root = FXMLLoader.load(getClass().getResource(ClientConstants.Screens.CAPACITY_UPDATE.toString()));
            Stage stage = new Stage();
            stage.setTitle("park manager park capacity update");
            stage.setScene(new Scene(root,688, 438));
            stage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
    }
    @FXML
    void differenceUpdate(ActionEvent event) {
    	Parent root ;
        try {
        	 root = FXMLLoader.load(getClass().getResource(ClientConstants.Screens.DIFFERENCE_PARAMETER_UPDATE.toString()));
            Stage stage = new Stage();
            stage.setTitle("park manager difference parameter update");
            stage.setScene(new Scene(root,688, 438));
            stage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
    }

    @FXML
    void visitationTimeUpdate(ActionEvent event) {
    	Parent root ;
        try {
        	 root = FXMLLoader.load(getClass().getResource(ClientConstants.Screens.VISITATION_TIME_UPDATE.toString()));
            Stage stage = new Stage();
            stage.setTitle("park manager visitation time update");
            stage.setScene(new Scene(root,688, 438));
            stage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Employee emp = (Employee) guiControl.getUser();
		String parkname=emp.getParkName();
		guiControl.sendToServer(new ClientMessage(ClientMessageType.GET_PARK, parkname));
        Park park=(Park) guiControl.getServerMsg().getMessage();

        labelCapacity.setText(labelCapacity.getText()+" "+String.valueOf(park.getParkMaxVisitorsDefault()));
        labelDifference.setText(labelDifference.getText()+" "+String.valueOf(park.getParkDiffFromMax()));
        labelVisitationTime.setText(labelVisitationTime.getText()+" "+String.valueOf(park.getParkVisitDuration()));
        
	}

}





