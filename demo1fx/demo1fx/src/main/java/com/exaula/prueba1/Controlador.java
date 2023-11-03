package com.exaula.prueba1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controlador {

  @FXML
  private Button botonIncrementar;
  @FXML
  private Label textoEtiqueta;
  private Contador contador;

  @FXML
  public void initialize() {
    contador = new Contador();
    botonIncrementar.setOnAction(e -> {
      contador.incrementar();
      textoEtiqueta.setText("Contador : " + contador.get());
    });
  }

}
