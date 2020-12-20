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
/*
 * controller class for the server UI
 */
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
    /*
     * client list in order to show connected clients in GUI
     */
    ObservableList<ConnectionToClient> clientsConnectedObservableList;
    /*
     * method that's called upon pressing the start button,sets up the client list in the GUI and calls ServerMain.runServer()
     * note that upon being pressed,the button changes it's text to Exit, and upon being pressed again it shuts down the server
     */
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
