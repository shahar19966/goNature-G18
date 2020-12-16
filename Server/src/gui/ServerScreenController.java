package gui;

import application.ServerMain;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import ocsf.server.ConnectionToClient;

public class ServerScreenController {
    @FXML
    private AnchorPane enableDisablePane;

    @FXML
    private AnchorPane fillPane;

    @FXML
    private TitledPane _clientTitledPane;

    @FXML
    private ListView<ConnectionToClient> clientsConnectedList;

    @FXML
    private Button _startBtn;

    @FXML
    private Circle _serverLedIndicator;

    @FXML
    private Circle _dbLedIndicator;

    @FXML
    private VBox secondPane;

    @FXML
    private Label mainLabel;

    @FXML
    private ImageView goNatureSymbol;
    
    ObservableList<ConnectionToClient> clientsConnectedObservableList;

    @FXML
    void startServerClicked(ActionEvent event) {
    	if(_startBtn.getText().equals("Start")) {
    		_startBtn.setText("Exit");
    		clientsConnectedObservableList = FXCollections.observableArrayList();
			clientsConnectedList.setItems(clientsConnectedObservableList);
    		ServerMain.runServer();
    	}
    	else {
    		ServerMain.stopServer();
    	}
    }
    public void serverConnected() {
    	_serverLedIndicator.setFill(Paint.valueOf("GREEN"));
    }
    public void dataBaseConnected() {
    	_dbLedIndicator.setFill(Paint.valueOf("GREEN"));
    }
	public void connectClient(ConnectionToClient client) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				clientsConnectedObservableList.add(client);
			}
		});

	}

	public void disconnectClient(ConnectionToClient client) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				clientsConnectedObservableList.remove(client);
			}
		});

	}

}
