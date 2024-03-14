package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import Model.Proyecto;
import Model.Tarea;
import Model.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Tareas implements Initializable {

  private static Usuario usuario;
  private static Proyecto proyecto;
  private Tarea tareaActual;

  @FXML
  private Button botonVolverProyectos;
  @FXML
  private Button botonEditarProyecto;
  @FXML
  private Button botonBorrarProyecto;

  @FXML
  private Text nombreProyecto;
  @FXML
  private Text descripcionProyecto;
  @FXML
  private Text fechasProyecto;

  @FXML
  private AnchorPane botonInfoTarea;
  @FXML
  private Button botonEditarTarea;
  @FXML
  private Button botonBorrarTarea;
  @FXML
  private Button botonCerrarInfo;

  @FXML
  private AnchorPane infoTarea;
  @FXML
  private Text nombre;
  @FXML
  private Text descripcion;
  @FXML
  private Text fechas;
  @FXML
  private Text duracion;

  @FXML
  private ScrollPane scrollTareasEnProceso;
  @FXML
  private Button botonCrearTarea;
  @FXML
  private VBox tareasEnProceso;

  @FXML
  private ScrollPane scrollTareasFinalizadas;
  @FXML
  private VBox tareasFinalizadas;

  @FXML
  private ScrollPane scrollTareasPendientes;
  @FXML
  private VBox tareasPendientes;

  @FXML
  void volverProyectos(ActionEvent event) throws IOException {
    Tareas.setProyecto(null);
    CodeFlow.setRoot("proyectos");
  }

  private void mostrarTareas() {

    tareasPendientes.getChildren().clear();
    tareasEnProceso.getChildren().clear();
    tareasFinalizadas.getChildren().clear();

    nombreProyecto.setText(proyecto.getNombre());
    fechasProyecto.setText(proyecto.getFecha_inicio() + " - " + proyecto.getFecha_final());
    descripcionProyecto.setText(proyecto.getDescripcion());

    for (Tarea t : proyecto.getTareas()) {

      Text nombreTarea = new Text(t.getNombre());
      Text fechasTarea = new Text(t.getFecha_inicio() + " - " + t.getFecha_final());

      long days = ChronoUnit.DAYS.between(t.getFecha_inicio().toLocalDate(), t.getFecha_final().toLocalDate());

      Text duracionTarea = new Text("Días: " + days);

      nombreTarea.setFont(new Font("Consolas", 14));
      fechasTarea.setFont(new Font("Consolas", 14));
      duracionTarea.setFont(new Font("Consolas", 12));
      nombreTarea.setFill(Color.WHITE);
      fechasTarea.setFill(Color.WHITE);
      duracionTarea.setFill(Color.WHITE);
      fechasTarea.setWrappingWidth(200);
      fechasTarea.setTextAlignment(TextAlignment.CENTER);
      duracionTarea.setWrappingWidth(200);
      duracionTarea.setTextAlignment(TextAlignment.CENTER);

      VBox tarea = new VBox(5);

      tarea.getChildren().addAll(nombreTarea, fechasTarea, duracionTarea);

      VBox.setMargin(tarea, new Insets(5, 10, 5, 10));
      tarea.setPadding(new Insets(10, 10, 10, 10));

      if (t.getFecha_inicio().after(Date.valueOf(LocalDate.now()))) {

        tarea.setStyle("-fx-background-color: #D15F5F;");
        tareasPendientes.getChildren().addAll(tarea);

      } else if ((t.getFecha_inicio().before(Date.valueOf(LocalDate.now())) || t.getFecha_inicio().equals(Date.valueOf(LocalDate.now()))) && (t.getFecha_final().after(Date.valueOf(LocalDate.now())) || t.getFecha_final().equals(Date.valueOf(LocalDate.now())))) {

        tarea.setStyle("-fx-background-color: #FFC107;");
        tareasEnProceso.getChildren().addAll(tarea);

      } else if (t.getFecha_final().before(Date.valueOf(LocalDate.now()))) {

        tarea.setStyle("-fx-background-color: #81C784;");
        tareasFinalizadas.getChildren().addAll(tarea);

      }

      EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          tareaActual = t;

          nombre.setText(nombreTarea.getText());
          fechas.setText(fechasTarea.getText());
          duracion.setText(duracionTarea.getText());
          descripcion.setText(t.getDescripcion());

          infoTarea.setVisible(true);
          infoTarea.setDisable(false);

        }
      };

      tarea.setOnMouseClicked(eventHandler);
      tarea.setCursor(Cursor.HAND);

    }

  }

  @FXML
  void borrarProyecto(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmación");
    alert.setHeaderText("¿Seguro que quiere borrar este proyecto?");
    alert.setContentText("Todos los datos se perderán.");

    ButtonType buttonTypeAceptar = new ButtonType("Aceptar");
    ButtonType buttonTypeCancelar = new ButtonType("Cancelar");
    alert.getButtonTypes().setAll(buttonTypeAceptar, buttonTypeCancelar);

    alert.showAndWait().ifPresent(buttonType -> {
      if (buttonType == buttonTypeAceptar) {
        try {
          proyecto.borrar();
          tareaActual = null;
          proyecto = null;
          Proyectos.setUsuario(usuario);
          CodeFlow.setRoot("proyectos");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  @FXML
  void editarProyecto(ActionEvent event) throws IOException {
    EditarProyecto.setUsuario(usuario);
    EditarProyecto.setProyecto(proyecto);
    CodeFlow.setRoot("editarProyecto");
  }

  @FXML
  void crearTarea(ActionEvent event) throws IOException {
    NuevaTarea.setUsuario(usuario);
    NuevaTarea.setProyecto(proyecto);
    CodeFlow.setRoot("nuevaTarea");
  }

  @FXML
  void cerrarInfo(ActionEvent event) {
    infoTarea.setVisible(false);
    infoTarea.setDisable(true);
  }

  @FXML
  void borrarTarea(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmación");
    alert.setHeaderText("¿Seguro que quiere borrar esta tarea?");
    alert.setContentText("Todos los datos se perderán.");

    ButtonType buttonTypeAceptar = new ButtonType("Aceptar");
    ButtonType buttonTypeCancelar = new ButtonType("Cancelar");
    alert.getButtonTypes().setAll(buttonTypeAceptar, buttonTypeCancelar);

    alert.showAndWait().ifPresent(buttonType -> {
      if (buttonType == buttonTypeAceptar) {
        tareaActual.borrar();
        tareaActual = null;
        cerrarInfo(event);
        mostrarTareas();
      }
    });
  }

  @FXML
  void editarTarea(ActionEvent event) throws IOException {
    EditarTarea.setUsuario(usuario);
    EditarTarea.setProyecto(proyecto);
    EditarTarea.setTarea(tareaActual);
    CodeFlow.setRoot("editarTarea");
  }

  public static Proyecto getProyecto() {
    return proyecto;
  }

  public static void setProyecto(Proyecto proyecto) {
    Tareas.proyecto = proyecto;
  }

  public static Usuario getUsuario() {
    return usuario;
  }

  public static void setUsuario(Usuario usuario) {
    Tareas.usuario = usuario;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    scrollTareasEnProceso.setFitToHeight(true);
    scrollTareasPendientes.setFitToHeight(true);
    scrollTareasFinalizadas.setFitToHeight(true);

    mostrarTareas();

  }

}
