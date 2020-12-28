package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entity.EntityConstants;
import entity.Order;
import entity.ParameterUpdate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import message.ClientMessage;
import message.ClientMessageType;

public class RequestsDepManagerController implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();

    @FXML
    private TableView<?> discountTableView;

    @FXML
    private TableColumn<?, ?> discountParkNumberCol;

    @FXML
    private TableColumn<?, ?> parkNameCol;

    @FXML
    private TableColumn<?, ?> startDateCol;

    @FXML
    private TableColumn<?, ?> finishDateCol;

    @FXML
    private TableColumn<?, ?> discountAmountCol;

    @FXML
    private TableColumn<?, ?> discountStatusCol;

    @FXML
    private TableColumn<?, ?> approveCol;

    @FXML
    private TableColumn<?, ?> declineCol;

    @FXML
    private TableView<ParameterUpdate> parametersTable;

    @FXML
    private TableColumn<ParameterUpdate, EntityConstants.ParkParameter> parameter;

    @FXML
    private TableColumn<ParameterUpdate, Integer> newvalue;

    @FXML
    private TableColumn<ParameterUpdate, String> parkname;
    @FXML
    private TableColumn<ParameterUpdate, ?> approveCol1;

    @FXML
    private TableColumn<?, ?> declineCol1;
	private ObservableList<ParameterUpdate> parameters = FXCollections.observableArrayList();


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		guiControl.sendToServer(new ClientMessage(ClientMessageType.REQUESTS_PARAMETERS, null));
		List<ParameterUpdate> parameterUpdate = (List<ParameterUpdate>) guiControl.getServerMsg().getMessage();
		parameters.addAll(parameterUpdate);
		parameter.setCellValueFactory(new PropertyValueFactory<ParameterUpdate, EntityConstants.ParkParameter>("parameter"));
		newvalue.setCellValueFactory(new PropertyValueFactory<ParameterUpdate, Integer>("newValue"));
		parkname.setCellValueFactory(new PropertyValueFactory<ParameterUpdate, String>("parkName"));

		parametersTable.setItems(parameters);
		
	}
	

}
