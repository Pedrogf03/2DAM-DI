package Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Deportes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;

public class EncuestaController implements Initializable {

  File f = new File("resultadoEncuesta.dat");

  @FXML
  private Button aceptar;

  @FXML
  private Button cancelar;

  @FXML
  private Slider cine;

  @FXML
  private Slider compras;

  @FXML
  private ChoiceBox<Deportes> deporte;

  @FXML
  private TextField edad;

  @FXML
  private Spinner<Integer> hermanos;

  @FXML
  private CheckBox practica;

  @FXML
  private TextField profesion;

  @FXML
  private ToggleGroup sexo;

  @FXML
  private RadioButton sexoH;

  @FXML
  private RadioButton sexoM;

  @FXML
  private Slider tele;

  @FXML
  private Label error;

  @FXML
  void enableDeporte(ActionEvent event) {
    if (practica.isSelected()) {
      deporte.setDisable(false);
    } else {
      deporte.setDisable(true);
    }
  }

  @FXML
  void aceptar(ActionEvent event) {

    try (FileWriter bw = new FileWriter(f, true);) {

      if (!profesion.getText().equals("")
          && !edad.getText().equals("")
          && sexo.getSelectedToggle() != null) {

        int comprasValue = Math.round((compras.valueProperty().floatValue() / 10));
        int teleValue = Math.round((tele.valueProperty().floatValue() / 10));
        int cineValue = Math.round((cine.valueProperty().floatValue() / 10));

        RadioButton selectedRadioButton = (RadioButton) sexo.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();

        if (practica.isSelected()) {
          if (deporte.getValue() == null) {
            error.setOpacity(1);
            return;
          }

          bw.write(
              profesion.getText() + ";;" + hermanos.getValue() + ";;" + edad.getText() + ";;" + toggleGroupValue
                  + ";;" + practica.isSelected() + ";;" + deporte.getValue() + ";;" + comprasValue + ";;" + teleValue
                  + ";;" + cineValue + "\n");

        } else {

          bw.write(
              profesion.getText() + ";;" + hermanos.getValue() + ";;" + edad.getText() + ";;" + toggleGroupValue
                  + ";;" + practica.isSelected() + ";;NULL;;" + comprasValue + ";;" + teleValue
                  + ";;" + cineValue + "\n");

        }

        System.exit(0);

      } else {
        error.setOpacity(1);
        return;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @FXML
  void cancelar(ActionEvent event) {

    profesion.setText("");
    hermanos.getValueFactory().setValue(0);
    edad.setText("");
    sexo.selectToggle(null);
    practica.setSelected(false);
    deporte.setValue(null);
    deporte.setDisable(true);
    cine.valueProperty().set(50);
    compras.valueProperty().set(50);
    tele.valueProperty().set(50);

    error.setOpacity(0);
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    // Poner los sliders a la mitad por defecto.
    cine.valueProperty().set(50);
    compras.valueProperty().set(50);
    tele.valueProperty().set(50);

    // Configuración del spinner
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
    hermanos.setValueFactory(valueFactory);

    // Edad como valor numerico
    edad.addEventFilter(KeyEvent.KEY_TYPED, event -> {
      if (!event.getCharacter().matches("\\d*")) {
        event.consume();
      }
    });

    // Valores de la lista seleccionable
    deporte.getItems().addAll(Deportes.values());

  }

}