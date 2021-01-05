package gui;

import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.chart.CategoryAxis;

import javafx.scene.chart.NumberAxis;
import entity.Employee;
import entity.ReportDate;
import entity.VisitorReport;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import message.ClientMessage;
import message.ClientMessageType;
/**
 * 
 * This class responsible for the control page of the park manager's income report
 *
 */
public class ParkManagerIncomeReportController implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();
	@FXML
	private LineChart<String, Integer> lineChar;
	@FXML
	private Label year;

	@FXML
	private ComboBox<String> yearComboBox;

	@FXML
	private ComboBox<String> monthComboBox;

	@FXML
	private Button yesBtn;

	@FXML
	private ImageView icon;

	@FXML
	private LineChart<String, Integer> lineCharSecond;

	@FXML
	private CategoryAxis x1;

	@FXML
	private NumberAxis y1;

	@FXML
	private Label totalSecond;

	@FXML
	private Label parkName;
	@FXML
	private Label total;
	@FXML
	private Label month;
	@FXML
	private CategoryAxis x;
	@FXML
	private NumberAxis y;
	@FXML
	private ScrollPane scrollPane;
	private ReportDate reportDate;

	private int currentMonth = Calendar.MONTH - 1;
	private Calendar c = Calendar.getInstance();
	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	private ObservableList<String> yearObsList = FXCollections.observableArrayList("2021", "2020");
	private ObservableList<String> monthObsList = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12");
	  /**
     * initialize all the Data report before the page uploaded and displayed to the user
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		yearComboBox.setPrefWidth(100);
		yearComboBox.setItems(yearObsList);
		monthComboBox.setPrefWidth(105);
		monthComboBox.setItems(monthObsList);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		yesBtn.disableProperty().bind(Bindings.createBooleanBinding(
				() ->  yearComboBox.getValue() ==null || monthComboBox.getValue() ==null, monthComboBox.valueProperty(),yearComboBox.valueProperty()));
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
	

		String park = ((Employee) (guiControl.getUser())).getParkName();
		reportDate = new ReportDate(park);
		year.setText(String.valueOf(c.getInstance().get(Calendar.YEAR)));
		reportDate.setYear(String.valueOf(currentYear));
		reportDate.setMonth(String.valueOf(currentMonth));
		parkName.setText(park);
		month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
		setDataCurrentMonth();

	}
/***
 * 
 * @param event click on yes btn 
 */
	@FXML
	void yesBtnClick(ActionEvent event) {

		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		icon.setVisible(true);
		reportDate.setYear(yearComboBox.getValue());
		reportDate.setMonth(monthComboBox.getValue());

		while (Integer.parseInt(reportDate.getYear()) == currentYear
				&& Integer.parseInt(reportDate.getMonth()) > currentMonth)
			GUIControl.popUpError("Future date does not exist ! try again");

		setDataSecondReport();
		

	}
	/**
     * initialize all the Data report to Current Month and year
     */

	private void setDataCurrentMonth() {
		guiControl.sendToServer(new ClientMessage(ClientMessageType.INCOME_REPORT, reportDate));
		Map<Integer, VisitorReport> parkReportMap = (Map<Integer, VisitorReport>) guiControl.getServerMsg()
				.getMessage();
		XYChart.Series<String, Integer> price = new XYChart.Series<>();
		int total_price = 0;
		int i = 1;
		for (VisitorReport vr : parkReportMap.values()) {
			price.getData().add(new XYChart.Data<>(String.valueOf(i), new Integer(vr.getPrice())));
			i++;
			total_price = total_price + vr.getPrice();
		}
		lineChar.getData().add(price);
		String totalP = String.valueOf(total_price);
		total.setText(totalP + " " + "NIS");

	}
	/**
     * initialize all the Data report to month and year that the user has selected
     */

	private void setDataSecondReport() {
		guiControl.sendToServer(new ClientMessage(ClientMessageType.INCOME_REPORT, reportDate));
		Map<Integer, VisitorReport> parkReportMap = (Map<Integer, VisitorReport>) guiControl.getServerMsg()
				.getMessage();
		lineCharSecond.getData().clear();
		XYChart.Series<String, Integer> price = new XYChart.Series<>();
		int total_price = 0;
		int i = 1;
		for (VisitorReport vr : parkReportMap.values()) {
			price.getData().add(new XYChart.Data<>(String.valueOf(i), new Integer(vr.getPrice())));
			i++;
			total_price = total_price + vr.getPrice();
		}
		lineCharSecond.getData().add(price);
		String totalP = String.valueOf(total_price);
		totalSecond.setText(totalP + " " + "NIS");
	}

}
