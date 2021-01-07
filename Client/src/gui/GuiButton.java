package gui;

/**
 * This is a way to create stylish buttons
 */
import gui.ClientConstants.AlertType;
import gui.ClientConstants.Sizes;
import javafx.scene.control.Button;

public class GuiButton extends Button {
	public GuiButton(String text, AlertType alert, Sizes size) {
		super(text);
		getStyleClass().add("text-field-" + size);
		getStyleClass().add(alert.getAlertTypeStyleClass());
		setWrapText(true);
	}
}
