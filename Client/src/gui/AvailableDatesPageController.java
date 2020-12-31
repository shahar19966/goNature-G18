package gui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import entity.Order;
import gui.ClientConstants.AlertType;
import gui.ClientConstants.Sizes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import message.ClientMessage;
import message.ClientMessageType;

public class AvailableDatesPageController implements Initializable {

		GUIControl guiControl=GUIControl.getInstance();
	    @FXML
	    private GridPane gridPane;

	    @FXML
	    private Label titleLabel;

	    @FXML
	    private HBox mainHBox;
    
    public void setTable(Map<String,List<String>> dateMap,List<Object> orderDes) {
    	Set keySet=dateMap.keySet();
    	String[] dates = Arrays.copyOf(keySet.toArray(), keySet.toArray().length, String[].class);
    	int i=0;
    	for(Node node:mainHBox.getChildren()) {
    		VBox mainVBox=(VBox)node;
    		Label title=(Label)mainVBox.getChildren().get(0);
    		title.setText(dates[i]);
			VBox secondaryVBox=(VBox)mainVBox.getChildren().get(1);
			for(String hour:dateMap.get(dates[i])) {
				final String nextDate=dates[i];
				String niceHour=hour.substring(0, 5);
				GuiButton button=new GuiButton(niceHour,AlertType.Info,Sizes.Medium);
				button.setOnAction(e->{
					Order order = (Order) orderDes.get(0);
					order.setDateOfOrder(nextDate);
					order.setTimeOfOrder(hour);
					guiControl.sendToServer(new ClientMessage(ClientMessageType.ORDER,orderDes));
					guiControl.getClientMainPageController().showAlertWithOkButton(AlertType.Success, "Order Succeeded",
							((Order) guiControl.getServerMsg().getMessage()).toString(), null);
				});
				secondaryVBox.getChildren().add(button);
			}
			i++;
    		
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		mainHBox.prefWidthProperty().bind(gridPane.widthProperty());
		for(Node node:mainHBox.getChildren()) {
			VBox mainVBox=(VBox)node;
			VBox secondaryVBox=(VBox)mainVBox.getChildren().get(1);
			secondaryVBox.prefHeightProperty().bind(mainHBox.heightProperty().subtract(40));
			secondaryVBox.minWidthProperty().bind(mainHBox.widthProperty().divide(7));
			}
	}
    

}
