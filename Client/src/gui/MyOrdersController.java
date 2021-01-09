package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entity.Order;
import entity.Subscriber;
import entity.Visitor;
import entity.EntityConstants;
import entity.EntityConstants.OrderStatus;
import gui.ClientConstants.AlertType;
import gui.ClientConstants.Sizes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
 * This is the Controller for MyOrdersPane fxml fills the table with orders of
 * the logged in user
 */
public class MyOrdersController implements Initializable {

	@FXML
	private TableView<Order> orderTable;

	@FXML
	private TableColumn<Order, String> orderNumberCol;

	@FXML
	private TableColumn<Order, String> parkNameCol;

	@FXML
	private TableColumn<Order, String> dateCol;

	@FXML
	private TableColumn<Order, String> timeCol;

	@FXML
	private TableColumn<Order, Integer> amountCol;

	@FXML
	private TableColumn<Order, EntityConstants.OrderType> typeCol;
	@FXML
	private TableColumn<Order, EntityConstants.OrderStatus> statusCol;

	@FXML
	private TableColumn action1Col;

	@FXML
	private TableColumn action2Col;

	private ObservableList<Order> myOrders = FXCollections.observableArrayList();

	private GUIControl guiControl = GUIControl.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String id = null;
		if (guiControl.getUser() instanceof Visitor) {
			Visitor visitor = (Visitor) guiControl.getUser();
			id = visitor.getId();
		}
		if (guiControl.getUser() instanceof Subscriber) {
			Subscriber subscriber = (Subscriber) guiControl.getUser();
			id = subscriber.getID();
		}
		guiControl.sendToServer(new ClientMessage(ClientMessageType.GET_ORDERS_BY_ID, id));
		List<Order> orders = (List<Order>) guiControl.getServerMsg().getMessage();
		myOrders.addAll(orders);
		orderTable.setItems(myOrders);
		createCols();
	}

	private void createCols() {
		setOrderNumberCol();
		setParkNameCol();
		setDateCol();
		setTimeCol();
		setPeopleAmountCol();
		setTypeCol();
		setStatusCol();
		setAction1Col();
		setAction2Col();
	}

	private void setOrderNumberCol() {
		orderNumberCol.setCellValueFactory(new PropertyValueFactory<Order, String>("orderNum"));
		orderNumberCol.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.1));
	}

	private void setParkNameCol() {
		parkNameCol.setCellValueFactory(new PropertyValueFactory<Order, String>("parkName"));
		parkNameCol.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.1));
	}

	private void setDateCol() {

		dateCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFactory = //
				new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {

					@Override
					public TableCell<Order, String> call(TableColumn<Order, String> param) {
						final TableCell<Order, String> cell = new TableCell<Order, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									String[] orderDateSplit = getTableView().getItems().get(getIndex()).getDateOfOrder()
											.split("-");
									setText(orderDateSplit[2] + "-" + orderDateSplit[1] + "-" + orderDateSplit[0]);
								}

							}
						};
						return cell;

					}

				};

		dateCol.setCellFactory(cellFactory);
		dateCol.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.1));
	}

	private void setTimeCol() {
		timeCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFactory = //
				new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {

					@Override
					public TableCell<Order, String> call(TableColumn<Order, String> param) {
						final TableCell<Order, String> cell = new TableCell<Order, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									setText(getTableView().getItems().get(getIndex()).getTimeOfOrder().substring(0, 5));
								}

							}
						};
						return cell;

					}

				};

		timeCol.setCellFactory(cellFactory);
		timeCol.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.05));
	}

	private void setPeopleAmountCol() {
		amountCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("numOfVisitors"));
		amountCol.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.1));
	}

	private void setTypeCol() {
		typeCol.setCellValueFactory(new PropertyValueFactory<Order, EntityConstants.OrderType>("type"));
		typeCol.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.1));
	}

	private void setStatusCol() {
		statusCol.setCellValueFactory(new PropertyValueFactory<Order, EntityConstants.OrderStatus>("status"));
		statusCol.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.24));
	}

	private void setAction1Col() {
		action1Col.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFactory = //
				new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {

					@Override
					public TableCell<Order, String> call(TableColumn<Order, String> param) {
						final TableCell<Order, String> cell = new TableCell<Order, String>() {
							// final Button btn = new Button("Just Do It");
							final GuiButton btn = new GuiButton("Cancel", AlertType.Danger, Sizes.Small);

							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btn.setOnAction(e -> {
										guiControl.sendToServer(new ClientMessage(ClientMessageType.CANCEL_ORDER,
												getTableView().getItems().get(getIndex()).getOrderNum()));
										if (guiControl.getServerMsg().getMessage() != null) {
											GUIControl.popUpMessage("Order Cancelled",
													"Order " + getTableView().getItems().get(getIndex()).getOrderNum()
															+ " is cancelled successfully");
											myOrders.remove(getTableView().getItems().get(getIndex()));
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

		action1Col.setCellFactory(cellFactory);
		action1Col.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.1));
	}

	/**
	 * for every order if OrderStatus is PENDING_FINAL_APPROVAL or
	 * PENDING_APPROVAL_FROM_WAITING_LIST a Button is shown to Approve or Activate
	 * the Order
	 */
	private void setAction2Col() {
		action2Col.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
		Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFactory = //
				new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {

					@Override
					public TableCell<Order, String> call(TableColumn<Order, String> param) {
						final TableCell<Order, String> cell = new TableCell<Order, String>() {
							// final Button btn = new Button("Just Do It");
							final GuiButton btn = new GuiButton("", AlertType.Success, Sizes.Small);

							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {

									if (getTableView().getItems().get(getIndex()).getStatus()
											.equals(OrderStatus.PENDING_FINAL_APPROVAL)
											|| getTableView().getItems().get(getIndex()).getStatus()
													.equals(OrderStatus.PENDING_APPROVAL_FROM_WAITING_LIST)) {
										if (getTableView().getItems().get(getIndex()).getStatus()
												.equals(OrderStatus.PENDING_FINAL_APPROVAL)) {
											btn.setText("Approve");
											btn.setOnAction(e -> {
												guiControl.sendToServer(new ClientMessage(
														ClientMessageType.APPROVE_ORDER,
														getTableView().getItems().get(getIndex()).getOrderNum()));
												if (guiControl.getServerMsg().getMessage() != null) {
													GUIControl.popUpMessage("Order Approved", "Order "
															+ getTableView().getItems().get(getIndex()).getOrderNum()
															+ " is approved successfully");
													getTableView().getItems().get(getIndex())
															.setStatus(OrderStatus.APPROVED);
												}
											});
										} else {
											btn.setText("Activate");
											btn.setOnAction(e -> {
												guiControl.sendToServer(new ClientMessage(
														ClientMessageType.ACTIVATE_ORDER_FROM_WATING_LIST,
														getTableView().getItems().get(getIndex())));
												myOrders.remove(getTableView().getItems().get(getIndex()));
												if (guiControl.getServerMsg().getMessage() != null) {
													myOrders.add((Order) guiControl.getServerMsg().getMessage());
													switch ((((Order) guiControl.getServerMsg().getMessage())
															.getStatus())) {
													case APPROVED:
														GUIControl.popUpMessage("Order Status", "Order "
																+ ((Order) guiControl.getServerMsg().getMessage())
																		.getOrderNum()
																+ " is now Approved.");
														break;
													case PENDING_FINAL_APPROVAL:
														GUIControl.popUpMessage("Order Status", "Order "
																+ ((Order) guiControl.getServerMsg().getMessage())
																		.getOrderNum()
																+ " is now Pending for final Approval!\nPlease Do it in 2 hours");
														break;
													case ACTIVE:
														GUIControl.popUpMessage("Order Status", "Order "
																+ ((Order) guiControl.getServerMsg().getMessage())
																		.getOrderNum()
																+ " is now ACTIVE. Please wait for final approval request");
														break;
													default:
														break;
													}
												} else {
													GUIControl.popUpError("Order "
															+ getTableView().getItems().get(getIndex()).getOrderNum()
															+ " was cancelled or expired by the system");
												}
											});
										}
										setGraphic(btn);
										setText(null);
									} else {
										setGraphic(null);
										setText(null);
									}
								}

							}
						};
						return cell;

					}

				};

		action2Col.setCellFactory(cellFactory);
		action2Col.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.1));
	}

}