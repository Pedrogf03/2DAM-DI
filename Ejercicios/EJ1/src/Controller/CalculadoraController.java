package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculadoraController implements Initializable {

  @FXML
  private Label fNumber;
  @FXML
  private Label sNumber;
  @FXML
  private Label result;

  @FXML
  private TextField fField;
  @FXML
  private TextField sField;
  @FXML
  private TextField rField;

  @FXML
  private Button plus;
  @FXML
  private Button minus;
  @FXML
  private Button multiply;
  @FXML
  private Button divide;
  @FXML
  private Button resto;
  @FXML
  private Button clear;

  private int toNumber(String s) {
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  @FXML
  private void sumar() {
    rField.setText("" + (toNumber(fField.getText()) + toNumber(sField.getText())));
  }

  @FXML
  private void restar() {
    rField.setText("" + (toNumber(fField.getText()) - toNumber(sField.getText())));
  }

  @FXML
  private void multiplicar() {
    rField.setText("" + (toNumber(fField.getText()) * toNumber(sField.getText())));
  }

  @FXML
  private void dividir() {
    rField.setText("" + (toNumber(fField.getText()) / toNumber(sField.getText())));
  }

  @FXML
  private void resto() {
    rField.setText("" + (toNumber(fField.getText()) % toNumber(sField.getText())));
  }

  @FXML
  private void borrar() {
    fField.setText("");
    sField.setText("");
    rField.setText("");
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

  }

}
