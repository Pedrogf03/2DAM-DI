package com.exaula.prueba1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {

    // Cargar objeto parent.
    Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));

    // Crear nueva escena con el objeto root.
    Scene escena = new Scene(root);

    // Establecer la escena, el titulo y se muestra.
    stage.setScene(escena);
    stage.setTitle("Contador");
    stage.show();

  }

  public static void main(String[] args) {
    launch();
  }

}