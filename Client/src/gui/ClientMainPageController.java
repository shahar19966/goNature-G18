package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClientMainPageController {

    @FXML
    private AnchorPane enableDisablePane;

    @FXML
    private AnchorPane fillPane;

    @FXML
    private VBox buttonVBox;

    @FXML
    private Label mainLabel;

    @FXML
    private VBox alertPane;

    @FXML
    private Label alertTitle;

    @FXML
    private ScrollPane alertDesPane;

    @FXML
    private AnchorPane alertBody;

    @FXML
    private HBox alertButtons;

    @FXML
    private ImageView goNatureSymbol;

}
