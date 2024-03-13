package Controller;

import java.io.IOException;

import DAO.DB;
import Model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Registro {

  @FXML
  private TextField usuario;
  @FXML
  private PasswordField password;
  @FXML
  private PasswordField repetirPassword;

  @FXML
  private Text mensajeError;

  @FXML
  private Button botonRegistro;
  @FXML
  private Button botonLogin;

  DB database = new DB();

  @FXML
  void registrarse() throws IOException {

    Usuario u = database.getUsuario(usuario.getText().trim(), password.getText().trim());

    if (u == null) {
      if (database.registrarUsuario(usuario.getText().trim(), password.getText().trim())) {
        login();
      }
    } else {
      usuario.setText("");
      password.setText("");
      repetirPassword.setText("");
      mensajeError.setVisible(true);
    }

  }

  @FXML
  void login() throws IOException {
    CodeFlow.setRoot("login");
  }

}
