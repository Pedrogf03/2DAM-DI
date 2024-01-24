package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class SorteoController implements Initializable {

  @FXML
  private ListView<HBox> listaDeAlumnos;

  private List<String> alumnos;

  @FXML
  private TextField newAlumno;

  @FXML
  void addNewAlumno(ActionEvent event) {
    String nombre = newAlumno.getText();

    if (nombre != null && !nombre.equals("")) {

      alumnos.add(nombre);

      HBox hbox = new HBox();

      Label label = new Label(nombre);

      Button button = new Button("Borrar");

      Region region = new Region();
      HBox.setHgrow(region, Priority.ALWAYS);

      hbox.getChildren().addAll(label, region, button);

      button.setOnAction(e -> {
        listaDeAlumnos.getItems().remove(hbox);
        alumnos.remove(nombre);
      });

      listaDeAlumnos.getItems().add(hbox);

      newAlumno.setText("");

    }
  }

  @FXML
  void escogerAlumno(ActionEvent event) {

    if (!alumnos.isEmpty()) {
      String alumno = alumnos.get(new Random().nextInt(alumnos.size()));

      for (int i = 0; i < listaDeAlumnos.getItems().size(); i++) {
        HBox hbox = (HBox) listaDeAlumnos.getItems().get(i);
        Label label = (Label) hbox.getChildren().get(0);
        if (label.getText().equals(alumno)) {
          listaDeAlumnos.getItems().remove(i);
          break;
        }
      }

      alumnos.remove(alumno);

      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Alumno elegido");
      alert.setHeaderText(null);
      alert.setContentText("El alumno elegido ha sido " + alumno);
      alert.showAndWait();

    } else {

      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Error");
      alert.setHeaderText(null);
      alert.setContentText("No hay alumnos para sortear");
      alert.showAndWait();

    }

  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    alumnos = new ArrayList<>();
  }

}
