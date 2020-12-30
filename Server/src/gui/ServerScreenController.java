package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.ServerMain;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import ocsf.server.ConnectionToClient;
/*
 * controller class for the server UI
 */
public class ServerScreenController implements Initializable {
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
    	serverDisconnected();
    	dataBaseDisconnected();
    	if(_startBtn.getText().equals("Start")) {
    		if(ServerMain.runServer()==true) {
    			_startBtn.setText("Exit");
    		}
    			
    	}
    	else {
    		ServerMain.stopServer();
    	}
    }
    public void serverConnected() {
    	_serverLedIndicator.setFill(Paint.valueOf("LIGHTGREEN"));
    }
    private void serverDisconnected() {
    	_serverLedIndicator.setFill(Paint.valueOf("RED"));
    }
    public void dataBaseConnected() {
    	_dbLedIndicator.setFill(Paint.valueOf("LIGHTGREEN"));
    }
    private void dataBaseDisconnected() {
    	_dbLedIndicator.setFill(Paint.valueOf("RED"));
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clientsConnectedObservableList = FXCollections.observableArrayList();
		clientsConnectedList.setItems(clientsConnectedObservableList);
		_startBtn.setOnMouseEntered(e->{
			_startBtn.setEffect(new DropShadow());
		});
		_startBtn.setOnMouseExited(e->{
			_startBtn.setEffect(null);
		});
	}

}
