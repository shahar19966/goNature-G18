package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DepartmentManagerReportsController {

    @FXML
    void cancellationReport(ActionEvent event) {
		Parent root ;
        try {
        	 root = FXMLLoader.load(getClass().getResource(ClientConstants.Screens.DEPARTMENT_CANCELLATION_REPORT.toString()));
            Stage stage = new Stage();
            stage.setTitle("department manager cancellation report");
           stage.setScene(new Scene(root,753, 579));
            stage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
    }

    @FXML
    void visitationReport(ActionEvent event) {
    	Parent root ;
        try {
        	 root = FXMLLoader.load(getClass().getResource(ClientConstants.Screens.DEPARTMENT_VISITATION_REPORT.toString()));
            Stage stage = new Stage();
            stage.setTitle("department manager visitation report");
           stage.setScene(new Scene(root,707, 893));
            stage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
    }
    	

    

}

