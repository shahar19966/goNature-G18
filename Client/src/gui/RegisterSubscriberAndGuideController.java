package gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class RegisterSubscriberController {

    @FXML
    private TextField idTextFiled;

    @FXML
    private Spinner<?> familiyCount;

    @FXML
    private TextField fNameTextFiled;

    @FXML
    private TextField cardNumber;

    @FXML
    private CheckBox isCard;

    @FXML
    private Button registerBtn;

    @FXML
    void clearCreditCardDes(ActionEvent event) {

    }

//    @FXML
//    void registerBtnClick(ActionEvent event) {
//    	if(!IsInputFieldsForRegister())
//    	{
//    		// show error 
//    		return;
//    	}
//    	SendInfoToServer();
//    }
//    
    

}
