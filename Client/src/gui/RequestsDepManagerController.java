package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entity.EntityConstants;
import entity.Order;
import entity.ParameterUpdate;
import entity.ParkDiscount;
import gui.ClientConstants.AlertType;
import gui.ClientConstants.Sizes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import message.ClientMessage;
import message.ClientMessageType;
/**
 * 
 * this class responsible for department manager requests for parameters and discounts
 *
 */
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
    private TableColumn approveCol;

    @FXML
    private TableColumn declineCol;

    @FXML
    private TableView<ParameterUpdate> parametersTable;

    @FXML
    private TableColumn<ParameterUpdate, EntityConstants.ParkParameter> parameter;

    @FXML
    private TableColumn<ParameterUpdate, Integer> newvalue;

    @FXML
    private TableColumn<ParameterUpdate, String> parkname;
    @FXML
    private TableColumn approveCol1;

    @FXML
    private TableColumn declineCol1;
    
	private ObservableList<ParameterUpdate> parameters = FXCollections.observableArrayList();
	private ObservableList<ParkDiscount> discountRequests = FXCollections.observableArrayList();


	/**
	 * initialize discount requests table with waiting discount requests
	 * initialize parameter requests table with parameter requests   
	 */
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
		for( ParkDiscount pd:discountList)//flip start date and finish date so they will start with day and end with year and not the other way around.	
		{
			StringBuilder rebuildStartDate= new StringBuilder();
			StringBuilder rebuildFinishDate= new StringBuilder();
			String reverseStartDate[]=pd.getStartDate().split("-");
			String reverseFinishDate[]=pd.getFinishDate().split("-");
			for(int i=reverseStartDate.length-1;i>=0;i--)
			{
				rebuildStartDate.append(reverseStartDate[i]+"-");
				rebuildFinishDate.append(reverseFinishDate[i]+"-");
			}
			rebuildStartDate.delete(rebuildStartDate.length()-1, rebuildStartDate.length());
			rebuildFinishDate.delete(rebuildFinishDate.length()-1, rebuildFinishDate.length());
			pd.setStartDate(rebuildStartDate.toString());
			pd.setFinishDate(rebuildFinishDate.toString());
			discountRequests.add(pd);	
			
			
		}
		parkNameCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, String>("parkName"));
		startDateCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, String>("startDate"));
		finishDateCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, String>("finishDate"));
		discountAmountCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, Integer>("discountAmount"));
		discountStatusCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, EntityConstants.RequestStatus>("discountStatus"));
		discountTableView.setItems(discountRequests);
		setApprovedCol1();
		setDeclineCol1();
		setApprovedCol();
		setDeclineCol();
		
		
	}
	
	/**
	 * this method called from initialize to initialize every row in the parameter table with approve button
	 * when approve button being pressed the parameter is update in the data base and the request is deleted from data base and from table 
	 */
	private void setApprovedCol1()
	{
		approveCol1.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		Callback<TableColumn<ParameterUpdate, String>, TableCell<ParameterUpdate, String>> cellFactory = //
				new Callback<TableColumn<ParameterUpdate, String>, TableCell<ParameterUpdate, String>>() {

			@Override
			public TableCell<ParameterUpdate, String> call(TableColumn<ParameterUpdate, String> param) {
				final TableCell<ParameterUpdate, String> cell = new TableCell<ParameterUpdate, String>() {
					final GuiButton btn = new GuiButton("Approve", AlertType.Success, Sizes.Small);
		
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btn.setOnAction(e -> {
							guiControl.sendToServer(new ClientMessage(ClientMessageType.APPROVE_PARAMETER,
									(ParameterUpdate)getTableView().getItems().get(getIndex())));
							if (guiControl.getServerMsg().getMessage() != null) {
								GUIControl.popUpMessage("Parameter Updated",
										"parameter updated successfully");
								parameters.remove(getTableView().getItems().get(getIndex()));
							}
						});
						setGraphic(btn);
						setText(null);
					}

				}
			};
			return cell;

		}

	};

	approveCol1.setCellFactory(cellFactory);

	/**
	 * this method called from initialize to initialize every row in the parameter table with decline button
	 * when decline button being pressed the request is deleted from data base and from table
	 */
	}
	private void setDeclineCol1()
	{
		declineCol1.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		Callback<TableColumn<ParameterUpdate, String>, TableCell<ParameterUpdate, String>> cellFactory = //
				new Callback<TableColumn<ParameterUpdate, String>, TableCell<ParameterUpdate, String>>() {

			@Override
			public TableCell<ParameterUpdate, String> call(TableColumn<ParameterUpdate, String> param) {
				final TableCell<ParameterUpdate, String> cell = new TableCell<ParameterUpdate, String>() {
					final GuiButton btn = new GuiButton("Decline", AlertType.Danger, Sizes.Small);
		
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btn.setOnAction(e -> {
							guiControl.sendToServer(new ClientMessage(ClientMessageType.DECLINE_PARAMETER,
									(ParameterUpdate)getTableView().getItems().get(getIndex())));
							if (guiControl.getServerMsg().getMessage() != null) {
								GUIControl.popUpMessage("Parameter Declined",
										"parameter deleted successfully");
								parameters.remove(getTableView().getItems().get(getIndex()));
							}
						});
						setGraphic(btn);
						setText(null);
					}

				}
			};
			return cell;

		}

	};

	declineCol1.setCellFactory(cellFactory);

	}
	
	/**
	 * this method called from initialize to initialize every row in the discount table with approve button
	 * when approve button being pressed the discount status updates to approved in the data base and the request is deleted from table 
	 */
	private void setApprovedCol()
	{
		approveCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		Callback<TableColumn<ParkDiscount, String>, TableCell<ParkDiscount, String>> cellFactory = //
				new Callback<TableColumn<ParkDiscount, String>, TableCell<ParkDiscount, String>>() {

			@Override
			public TableCell<ParkDiscount, String> call(TableColumn<ParkDiscount, String> param) {
				final TableCell<ParkDiscount, String> cell = new TableCell<ParkDiscount, String>() {
					final GuiButton btn = new GuiButton("Approve", AlertType.Success, Sizes.Small);
		
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btn.setOnAction(e -> {
							guiControl.sendToServer(new ClientMessage(ClientMessageType.APPROVE_DISCOUNT,
									(ParkDiscount)getTableView().getItems().get(getIndex())));
							if (guiControl.getServerMsg().getMessage() != null) {
								GUIControl.popUpMessage("Discount Updated",
										"discount updated successfully");
								
								discountRequests.remove(getTableView().getItems().get(getIndex()));
							}
						});
						setGraphic(btn);
						setText(null);
					}

				}
			};
			return cell;

		}

	};

	approveCol.setCellFactory(cellFactory);

	}
	
	/**
	 * this method called from initialize to initialize every row in the discount table with decline button
	 * when decline button being pressed the request status is updated to declined and it is deleted from table
	 */
	private void setDeclineCol()
	{
		declineCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		Callback<TableColumn<ParkDiscount, String>, TableCell<ParkDiscount, String>> cellFactory = //
				new Callback<TableColumn<ParkDiscount, String>, TableCell<ParkDiscount, String>>() {

			@Override
			public TableCell<ParkDiscount, String> call(TableColumn<ParkDiscount, String> param) {
				final TableCell<ParkDiscount, String> cell = new TableCell<ParkDiscount, String>() {
					final GuiButton btn = new GuiButton("Decline", AlertType.Danger, Sizes.Small);
		
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btn.setOnAction(e -> {
							guiControl.sendToServer(new ClientMessage(ClientMessageType.DECLINE_DISCOUNT,
									(ParkDiscount)getTableView().getItems().get(getIndex())));
							if (guiControl.getServerMsg().getMessage() != null) {
								GUIControl.popUpMessage("Discount Declined",
										"discount deleted successfully");
								discountRequests.remove(getTableView().getItems().get(getIndex()));
							}
						});
						setGraphic(btn);
						setText(null);
					}

				}
			};
			return cell;

		}

	};

	declineCol.setCellFactory(cellFactory);

	}

}
