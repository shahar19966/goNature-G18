package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entity.EntityConstants;
import entity.Order;
import entity.ParameterUpdate;
import entity.ParkDiscount;
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
    private TableView<ParkDiscount> discountTableView;

    @FXML
    private TableColumn<ParkDiscount, String> parkNameCol;

    @FXML
    private TableColumn<ParkDiscount, String> startDateCol;

    @FXML
    private TableColumn<ParkDiscount, String> finishDateCol;

    @FXML
    private TableColumn<ParkDiscount, Integer> discountAmountCol;

    @FXML
    private TableColumn<ParkDiscount, EntityConstants.RequestStatus> discountStatusCol;

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
	private ObservableList<ParkDiscount> discountRequests = FXCollections.observableArrayList();



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
		guiControl.sendToServer(new ClientMessage(ClientMessageType.DEP_MANAGER_GET_DISCOUNT_REQUESTS, null));
		List<ParkDiscount> discountList=(List<ParkDiscount>)guiControl.getServerMsg().getMessage();
		discountRequests.addAll(discountList);
		parkNameCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, String>("parkName"));
		startDateCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, String>("startDate"));
		finishDateCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, String>("finishDate"));
		discountAmountCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, Integer>("discountAmount"));
		discountStatusCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, EntityConstants.RequestStatus>("discountStatus"));
		discountTableView.setItems(discountRequests);
		
	}
	

}
