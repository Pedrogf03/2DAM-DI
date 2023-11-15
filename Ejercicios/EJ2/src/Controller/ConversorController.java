package Controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConversorController implements Initializable {

  @FXML
  private TextField cField;

  @FXML
  private Label celsiusText;

  @FXML
  private TextField fField;

  @FXML
  private Label farenheitText;

  DecimalFormat formato = new DecimalFormat("#,##");

  @FXML
  void farenheitToCelsius(ActionEvent event) {
    double f = Double.parseDouble(fField.getText());
    cField.setText("" + Double.parseDouble(formato.format((f - 32.0) * 5 / 9)));
  }

  @FXML
  void celsiusToFarenheit(ActionEvent event) {
    double c = Double.parseDouble(cField.getText());
    fField.setText("" + Double.parseDouble(formato.format((c * 9 / 5) + 32)));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

}
