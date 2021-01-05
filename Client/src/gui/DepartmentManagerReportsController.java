package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * 
 * This class responsible for the control page of selecting a report that the department manager will want to view
 *
 */

public class DepartmentManagerReportsController {

	/**
	 * Upload of the page cancellation report
	 * @param event click on cancellation report btn 
	 */
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

    /**
     * 
     * Upload of the page visitation report
	 * @param event click on visitation report btn
     */
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

