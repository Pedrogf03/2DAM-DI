package Controller;

import DAO.DB;
import Model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class login {

  @FXML
  private TextField usuario;
  @FXML
  private PasswordField password;

  @FXML
  private Text mensajeError;

  @FXML
  private Button botonLogin;
  @FXML
  private Button botonRegistro;

  DB database = new DB();

  @FXML
  void iniciarSesion(ActionEvent event) {

    Usuario u = database.getUsuario(usuario.getText().trim(), password.getText().trim());

    if (u == null) {
      usuario.setText("");
      password.setText("");
      mensajeError.setVisible(true);
    } else {
      // TODO: Ver proyectos del usuario
    }

  }

  @FXML
  void registrarse(ActionEvent event) {

  }

}
