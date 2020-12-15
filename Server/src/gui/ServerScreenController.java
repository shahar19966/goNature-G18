package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class ServerScreenController {

    @FXML
    private AnchorPane enableDisablePane;

    @FXML
    private AnchorPane fillPane;

    @FXML
    private Circle _serverLedIndicator;

    @FXML
    private Circle _dbLedIndicator;

    @FXML
    private ProgressIndicator loadingSpinner;

    @FXML
    private Button _startBtn;

    @FXML
    private TextField _serverPortTextField;

    @FXML
    private TextField serverIPLabel;

    @FXML
    private TitledPane _logTitledPane;

    @FXML
    private TextArea _logTextArea;

    @FXML
    private VBox secondPane;

    @FXML
    private Label mainLabel;

    @FXML
    void startServerClicked(ActionEvent event) {

    }

}
