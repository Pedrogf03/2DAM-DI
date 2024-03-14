package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Model.Prioridad;
import Model.Proyecto;
import Model.Tarea;
import Model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class EditarTarea implements Initializable {

  private static Usuario usuario;
  private static Proyecto proyecto;
  private static Tarea tarea;

  @FXML
  private Button botonCerrar;

  @FXML
  private Button botonEditarTarea;

  @FXML
  private TextArea descripcion;

  @FXML
  private Text errorMsg;

  @FXML
  private DatePicker fecha_final;

  @FXML
  private DatePicker fecha_inicio;

  @FXML
  private TextField nombre;

  @FXML
  private ChoiceBox<Prioridad> prioridad;

  @FXML
  void cerrar(ActionEvent event) throws IOException {

    if (!nombre.getText().equals("") || !descripcion.getText().equals("") || fecha_inicio.getValue() != null || fecha_final.getValue() != null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmación");
      alert.setHeaderText("¿Deseas salir sin guardar los cambios?");
      alert.setContentText("Los datos introducidos se perderán.");

      ButtonType buttonTypeAceptar = new ButtonType("Aceptar");
      ButtonType buttonTypeCancelar = new ButtonType("Cancelar");
      alert.getButtonTypes().setAll(buttonTypeAceptar, buttonTypeCancelar);

      alert.showAndWait().ifPresent(buttonType -> {
        if (buttonType == buttonTypeAceptar) {
          try {
            Tareas.setUsuario(usuario);
            Tareas.setProyecto(proyecto);
            CodeFlow.setRoot("tareas");
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });

    } else {
      Tareas.setUsuario(usuario);
      Tareas.setProyecto(proyecto);
      CodeFlow.setRoot("tareas");
    }

  }

  @FXML
  void editarTarea(ActionEvent event) throws IOException {

    // Comprobación de que no haya campos obligatorios en blanco.
    if (nombre.getText().equals("")) {
      errorMsg.setText("Introduzca el nombre del proyecto");
      errorMsg.setVisible(true);
    } else if (descripcion.getText().equals("")) {
      errorMsg.setText("Introduzca la descripción del proyecto");
      errorMsg.setVisible(true);
    } else if (fecha_inicio.getValue() == null) {
      errorMsg.setText("Introduzca la fecha de inicio del proyecto.");
      errorMsg.setVisible(true);
    } else if (fecha_final.getValue() == null) {
      errorMsg.setText("Introduzca la fecha de final del proyecto.");
      errorMsg.setVisible(true);
    } else if (fecha_final.getValue().compareTo(fecha_inicio.getValue()) < 0) {
      errorMsg.setText("La fecha de final no puede ser anterior a la fecha de inicio.");
      errorMsg.setVisible(true);
    } else {

      if (proyecto.actualizarTarea(tarea.getIdTarea(), nombre.getText(), descripcion.getText(), Date.valueOf(fecha_inicio.getValue()), Date.valueOf(fecha_final.getValue()), prioridad.getValue())) {
        tarea.setNombre(nombre.getText());
        tarea.setDescripcion(descripcion.getText());
        tarea.setFecha_inicio(Date.valueOf(fecha_inicio.getValue()));
        tarea.setFecha_final(Date.valueOf(fecha_final.getValue()));
        tarea.setPrioridad(prioridad.getValue());

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Tarea actualizada correctamente");
        alert.setHeaderText(null);
        alert.setContentText("La tarea se ha actualizado correctamente");

        alert.showAndWait();

        Tareas.setUsuario(usuario);
        Tareas.setProyecto(proyecto);
        CodeFlow.setRoot("tareas");
      } else {
        errorMsg.setText("No se ha podido actualizar La tarea");
        errorMsg.setVisible(true);
      }

    }

  }

  public static Usuario getUsuario() {
    return usuario;
  }

  public static void setUsuario(Usuario usuario) {
    EditarTarea.usuario = usuario;
  }

  public static Proyecto getProyecto() {
    return proyecto;
  }

  public static void setProyecto(Proyecto proyecto) {
    EditarTarea.proyecto = proyecto;
  }

  public static Tarea getTarea() {
    return tarea;
  }

  public static void setTarea(Tarea tarea) {
    EditarTarea.tarea = tarea;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    fecha_inicio.setDayCellFactory(new Callback<DatePicker, DateCell>() {
      @Override
      public DateCell call(DatePicker param) {
        return new DateCell() {
          @Override
          public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);

            // Deshabilita las fechas anteriores al día actual
            setDisable(empty || item.compareTo(LocalDate.now()) < 0);
          }
        };
      }
    });

    fecha_final.setDayCellFactory(new Callback<DatePicker, DateCell>() {
      @Override
      public DateCell call(DatePicker param) {
        return new DateCell() {
          @Override
          public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);

            // Deshabilita las fechas anteriores al día actual mas uno
            setDisable(empty || item.compareTo(LocalDate.now()) < 0);
          }
        };
      }
    });

    nombre.setText(tarea.getNombre());
    descripcion.setText(tarea.getDescripcion());
    fecha_inicio.setValue(tarea.getFecha_inicio().toLocalDate());
    fecha_final.setValue(tarea.getFecha_final().toLocalDate());
    prioridad.getItems().addAll(Prioridad.values());
    prioridad.setValue(tarea.getPrioridad());

  }

}
