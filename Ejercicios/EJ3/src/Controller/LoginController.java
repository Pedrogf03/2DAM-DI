package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginController implements Initializable {

  int pass = 12345;

  @FXML
  private Button first;
  @FXML
  private Button second;
  @FXML
  private Button third;
  @FXML
  private Button fourth;
  @FXML
  private Button fifth;
  @FXML
  private Button sixth;
  @FXML
  private Button seventh;
  @FXML
  private Button eighth;
  @FXML
  private Button ninth;
  @FXML
  private Button tenth;

  @FXML
  private PasswordField passwd;

  @FXML
  private Button clear;
  @FXML
  private Button enter;

  @FXML
  private Label error;

  @FXML
  void writeInPasswd(ActionEvent event) {
    Button b = (Button) event.getSource();
    passwd.setText(passwd.getText() + b.getText());
    error.setOpacity(0);
  }

  @FXML
  void clear() {
    passwd.setText("");
    error.setOpacity(0);
  }

  @FXML
  void checkPasswd() {
    if (Integer.parseInt(passwd.getText()) == pass) {
      startApp();
    } else {
      error.setOpacity(1);
    }
  }

  private static void startApp() {
    System.out.println("Aplicación iniciada");
    System.exit(0);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    List<Button> botones = new ArrayList<>();

    botones.add(first);
    botones.add(second);
    botones.add(third);
    botones.add(fourth);
    botones.add(fifth);
    botones.add(sixth);
    botones.add(seventh);
    botones.add(eighth);
    botones.add(ninth);
    botones.add(tenth);

    List<Integer> numbersList = new ArrayList<>();

    numbersList.add(1);
    numbersList.add(2);
    numbersList.add(3);
    numbersList.add(4);
    numbersList.add(5);
    numbersList.add(6);
    numbersList.add(7);
    numbersList.add(8);
    numbersList.add(9);
    numbersList.add(0);

    Collections.shuffle(numbersList);

    for (int i = 0; i < botones.size(); i++) {
      botones.get(i).setText("" + numbersList.get(i));
    }

  }

}
