package Controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class guessController implements Initializable {

  private int secretNumber = new Random().nextInt(100) + 1;
  private int intentos = 5;

  @FXML
  private Label msg;

  @FXML
  private Label triesLeft;

  @FXML
  private Button newNumber;

  @FXML
  private TextField number;

  @FXML
  private Button submit;

  @FXML
  void newNumber(ActionEvent event) {
    number.setDisable(false);
    intentos = 5;
    secretNumber = new Random().nextInt(100) + 1;
    msg.setText("New number generated.");
    triesLeft.setText("Tries left: " + intentos);
  }

  @FXML
  void submit(ActionEvent event) {
    if (!number.getText().equals("")) {
      if (intentos > 0) {
        intentos--;
        int userNum = Integer.parseInt(number.getText());
        number.setText("");
        if (userNum == secretNumber) {
          msg.setText("Congratulations! You've guessed the number!");
          number.setDisable(true);
        } else if (userNum > secretNumber) {
          msg.setText("Wrong! Your number is too high!");
        } else if (userNum < secretNumber) {
          msg.setText("Wrong! Your number is too low!");
        }
        triesLeft.setText("Tries left: " + intentos);
      } else if (intentos == 0) {
        msg.setText("You lost! The number was " + secretNumber);
        number.setDisable(true);
      }
    }
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    number.addEventFilter(KeyEvent.KEY_TYPED, event -> {
      String input = number.getText() + event.getCharacter();
      if (!input.matches("\\d*") || input.length() > 3 || Integer.parseInt(input) > 100) {
        event.consume();
      }
    });

  }

}
