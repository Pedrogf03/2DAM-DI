package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import javafx.event.EventHandler;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.Set;

import DAO.DataBase;
import DAO.FileFun;
import Model.Prioridad;
import Model.Proyecto;
import Model.Tarea;

public class CodeFlowController implements Initializable {

  // ---- FileFun

  private FileFun file = new FileFun();

  // ---- PROYECTOS

  private Set<Proyecto> proyectos;

  // ---- Botón que muestra el panel de creación de un nuevo proyecto.
  @FXML
  private Button newProjectButton;

  // ---- Panel que contiene todos los proyectos.
  @FXML
  private ScrollPane scrollPane;
  @FXML
  private FlowPane flowPane;

  // ---- Panel de creación de nuevo proyecto.
  @FXML
  private AnchorPane newProjectTab;
  @FXML
  private TextField nombre;
  @FXML
  private TextArea descripcion;
  @FXML
  private Button fileChooser;
  @FXML
  private DatePicker fecha_inicio;
  @FXML
  private DatePicker fecha_final;
  @FXML
  private Text errorMsg;
  @FXML
  private Button createProjectButton;
  @FXML
  private Button closeProjectTab;

  // ---- Panel de confirmación
  @FXML
  private AnchorPane confirmAlert;
  @FXML
  private Text confirmMsg;
  @FXML
  private Button confirmButton;
  @FXML
  private Button closeAlert;

  // ---- MÉTODOS 

  // ---- Función que se ejecuta al iniciar la aplicación.
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // Resgringir fechas pasadas en las fechas de inicio y fin
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

    // Resgringir fechas pasadas en las fechas de inicio y fin
    fecha_inicioNuevaTarea.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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

    fecha_finalNuevaTarea.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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

    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);

    scrollTareasEnProceso.setFitToHeight(true);
    scrollTareasPendientes.setFitToHeight(true);
    scrollTareasFinalizadas.setFitToHeight(true);

    prioridad.getItems().addAll(Prioridad.values());
    prioridad.setValue(Prioridad.MEDIA);

    mostrarProyectos();

  }

  // ---- Función para mostrar el panel de creación de un nuevo proyecto.
  @FXML
  void showNewProjectTab(ActionEvent event) {
    newProjectTab.setVisible(true);
    newProjectTab.setDisable(false);
  }

  // ---- Función genérica de cerrar paneles
  @FXML
  void closePanel(ActionEvent event) {

    Button b = (Button) event.getTarget();

    Parent p = b.getParent();
    if (p.getId().equals("newProjectTab")) {
      if (!nombre.getText().equals("") || !descripcion.getText().equals("") || fecha_inicio.getValue() != null || fecha_final.getValue() != null || fileChooser.getText() != "📁") {
        confirmMsg.setText("Va a perder todos los datos introducidos");
        confirmAlert.setVisible(true);
        confirmAlert.setDisable(false);
      } else {
        p.setVisible(false);
        p.setDisable(true);
      }
    } else if (p.getId().equals("newTaskTab")) {
      if (!nombreNuevaTarea.getText().equals("") || !descripcionNuevaTarea.getText().equals("") || fecha_inicioNuevaTarea.getValue() != null || fecha_finalNuevaTarea.getValue() != null || !prioridad.getValue().toString().equals("MEDIA")) {
        alertTaskText.setText("Va a perder todos los datos introducidos");
        alertTask.setVisible(true);
        alertTask.setDisable(false);

        alertTaskButton.setOnAction(e -> {
          alertTask.setVisible(false);
          alertTask.setDisable(true);

          resetForm(1);

          newTaskTab.setVisible(false);
          newTaskTab.setDisable(true);

        });

      } else {
        p.setVisible(false);
        p.setDisable(true);
      }
    } else {
      p.setVisible(false);
      p.setDisable(true);
    }

  }

  // ---- Función que se ejecuta al pulsar el botón aceptar del panel de confirmación.
  @FXML
  void confirmCloseProject(ActionEvent event) {

    confirmAlert.setVisible(false);
    confirmAlert.setDisable(true);

    newProjectTab.setVisible(false);
    newProjectTab.setDisable(true);

    resetForm(0);

  }

  // ---- Reiniciar el formulario.
  public void resetForm(int option) {
    if (option == 0) {
      nombre.setText("");
      descripcion.setText("");
      fileChooser.setText("📁");
      fecha_inicio.setValue(null);
      fecha_final.setValue(null);
      errorMsg.setText("");
    } else if (option == 1) {
      nombreNuevaTarea.setText("");
      descripcionNuevaTarea.setText("");
      fecha_inicioNuevaTarea.setValue(null);
      fecha_finalNuevaTarea.setValue(null);
      errorMsgNuevaTarea.setText("");
      prioridad.setValue(Prioridad.MEDIA);
    }
  }

  // ---- Seleccionar la imagen del proyecto.
  @FXML
  void fileSelector(ActionEvent event) {
    file.seleccionarImagen(fileChooser, event);
  }

  // ---- Comprobar y crear un nuevo proyecto en la base de datos.
  @FXML
  void createProject(ActionEvent event) {

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
      // Creación e inserción en base de datos del proyecto.
      Proyecto p = new Proyecto(nombre.getText(), descripcion.getText(), file.imagenProyecto, Date.valueOf(fecha_inicio.getValue()), Date.valueOf(fecha_final.getValue()));
      if (p.crearProyecto()) {
        newProjectTab.setVisible(false);
        newProjectTab.setDisable(true);

        // Mensaje de confirmación.
        confirmMsg.setText("Proyecto creado correctamente");
        confirmAlert.setVisible(true);
        confirmAlert.setDisable(false);
        mostrarProyectos();
      } else {
        // Mensaje de confirmación.
        confirmMsg.setText("Ha ocurrido un error inesperado");
        confirmAlert.setVisible(true);
        confirmAlert.setDisable(false);
      }

      resetForm(0);

    }

  }

  // ---- Mostrar todos los proyectos de la base de datos
  public void mostrarProyectos() {
    DataBase db = new DataBase();
    proyectos = db.getProyectos();

    flowPane.getChildren().clear();

    if (!proyectos.isEmpty()) {
      for (Proyecto p : proyectos) {
        try {
          // Cargar la imagen desde la carpeta de recursos
          Image image = new Image(getClass().getResource("/img/" + p.getImagen()).toExternalForm());
          ImageView imageView = new ImageView(image);

          // Establecer el ancho y alto fijos
          // imageView.setFitWidth(216.78);
          // imageView.setFitHeight(122.5);
          imageView.setFitWidth(236);
          imageView.setFitHeight(133);

          // Crear un label con el nombre del proyecto
          Label label = new Label(p.getNombre());
          // Cambiar la fuente a Franklin Gothic Demi
          label.setFont(Font.font("Franklin Gothic Demi"));
          // Cambiar el color del texto a blanco
          label.setTextFill(Color.web("#FFC107"));

          // Crear un VBox y añadir la imagen y el label
          VBox vbox = new VBox();
          vbox.setPadding(new Insets(10, 10, 10, 10));
          vbox.getChildren().addAll(imageView, label);

          // Añadir un evento al hacer click en el vbox
          EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              mostrarTareas(p);
            }
          };

          vbox.setOnMouseClicked(eventHandler);
          vbox.setCursor(Cursor.HAND);

          // Añadir el VBox al FlowPane
          flowPane.getChildren().add(vbox);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  // -------------------------------------------------------------------------------------------- //

  // ---- TAREAS

  // ---- Panel donde se van a mostrar las tareas
  @FXML
  private AnchorPane tareas;

  // ---- Proyecto con el que se está trabajando
  private Proyecto proyecto;

  // ---- Botones del panel lateral
  @FXML
  private Button volverToProyectos;
  @FXML
  private Button borrarProyecto;

  // ---- Alerta de borrar el proyecto
  @FXML
  private AnchorPane alertTask;
  @FXML
  private Button alertTaskButton;
  @FXML
  private Text alertTaskText;

  // ---- Paneles con las tareas
  @FXML
  private ScrollPane scrollTareasPendientes;
  @FXML
  private VBox tareasPendientes;

  @FXML
  private Button newTaskButton;

  @FXML
  private ScrollPane scrollTareasEnProceso;
  @FXML
  private VBox tareasEnProceso;
  @FXML
  private ScrollPane scrollTareasFinalizadas;
  @FXML
  private VBox tareasFinalizadas;

  // ---- Información relativa al proyecto
  @FXML
  private Text nombreProyecto;
  @FXML
  private Text fechasProyecto;
  @FXML
  private Text descripcionProyecto;

  // ---- Panel de creacion de nueva tarea
  @FXML
  private AnchorPane newTaskTab;
  @FXML
  private TextField nombreNuevaTarea;
  @FXML
  private TextArea descripcionNuevaTarea;
  @FXML
  private DatePicker fecha_inicioNuevaTarea;
  @FXML
  private DatePicker fecha_finalNuevaTarea;
  @FXML
  private ChoiceBox<Prioridad> prioridad;
  @FXML
  private Text errorMsgNuevaTarea;
  @FXML
  private Button createTaskButton;
  @FXML
  private Button closeTaskTab;

  void mostrarTareas(Proyecto p) {
    tareas.setVisible(true);
    tareas.setDisable(false);

    proyecto = p;

    tareasPendientes.getChildren().clear();
    tareasEnProceso.getChildren().clear();
    tareasFinalizadas.getChildren().clear();

    nombreProyecto.setText(p.getNombre());
    fechasProyecto.setText(p.getFecha_inicio() + " - " + p.getFecha_final());
    descripcionProyecto.setText(p.getDescripcion());

    for (Tarea t : p.getTareas()) {

      Text nombreTarea = new Text(t.getNombre());
      Text fechasTarea = new Text(t.getFecha_inicio() + " - " + t.getFecha_fin());

      long days = ChronoUnit.DAYS.between(t.getFecha_inicio().toLocalDate(), t.getFecha_fin().toLocalDate());

      Text duracion = new Text("" + days);

      nombreTarea.setFont(new Font("Consolas", 14));
      fechasTarea.setFont(new Font("Consolas", 14));
      duracion.setFont(new Font("Consolas", 12));
      nombreTarea.setFill(Color.WHITE);
      fechasTarea.setFill(Color.WHITE);
      duracion.setFill(Color.WHITE);
      fechasTarea.setWrappingWidth(200);
      fechasTarea.setTextAlignment(TextAlignment.CENTER);
      duracion.setWrappingWidth(200);
      duracion.setTextAlignment(TextAlignment.CENTER);

      VBox tarea = new VBox(5);

      tarea.getChildren().addAll(nombreTarea, fechasTarea, duracion);

      VBox.setMargin(tarea, new Insets(5, 10, 5, 10));
      tarea.setPadding(new Insets(10, 10, 10, 10));

      if (t.getFecha_inicio().after(Date.valueOf(LocalDate.now()))) { // La fecha de inicio es despues de hoy

        tarea.setStyle("-fx-background-color: #D15F5F;");
        tareasPendientes.getChildren().addAll(tarea);

      } else if ((t.getFecha_inicio().before(Date.valueOf(LocalDate.now())) || t.getFecha_inicio().equals(Date.valueOf(LocalDate.now()))) && (t.getFecha_fin().after(Date.valueOf(LocalDate.now())) || t.getFecha_fin().equals(Date.valueOf(LocalDate.now())))) {

        tarea.setStyle("-fx-background-color: #FFC107;");
        tareasEnProceso.getChildren().addAll(tarea);

      } else if (t.getFecha_fin().before(Date.valueOf(LocalDate.now()))) {

        tarea.setStyle("-fx-background-color: #81C784;");
        tareasFinalizadas.getChildren().addAll(tarea);

      }

    }

  }

  @FXML
  void confirmBorrarProyecto() {

    alertTaskText.setText("¿Seguro que quiere eliminar este proyecto?");

    alertTask.setVisible(true);
    alertTask.setDisable(false);

    alertTaskButton.setOnAction(e -> {
      borrarProyecto(proyecto);
      alertTask.setVisible(false);
      alertTask.setDisable(true);
    });

  }

  public void borrarProyecto(Proyecto p) {
    if (p.eliminar()) {
      goBackToProjects(null);
    } else {
      System.out.println("No se ha podido eliminar el proyecto.");
    }
  }

  @FXML
  void goBackToProjects(ActionEvent event) {
    mostrarProyectos();

    tareas.setVisible(false);
    tareas.setDisable(true);
  }

  @FXML
  void showNewTaskTab(ActionEvent event) {
    newTaskTab.setVisible(true);
    newTaskTab.setDisable(false);
  }

  @FXML
  void createTask(ActionEvent event) {

    // Comprobación de que no haya campos obligatorios en blanco.
    if (nombreNuevaTarea.getText().equals("")) {
      errorMsgNuevaTarea.setText("Introduzca el nombre de la tarea");
      errorMsgNuevaTarea.setVisible(true);
    } else if (descripcionNuevaTarea.getText().equals("")) {
      errorMsgNuevaTarea.setText("Introduzca la descripción de la tarea");
      errorMsgNuevaTarea.setVisible(true);
    } else if (fecha_inicioNuevaTarea.getValue() == null) {
      errorMsgNuevaTarea.setText("Introduzca la fecha de inicio del proyecto.");
      errorMsgNuevaTarea.setVisible(true);
    } else if (fecha_finalNuevaTarea.getValue() == null) {
      errorMsgNuevaTarea.setText("Introduzca la fecha de final del proyecto.");
      errorMsgNuevaTarea.setVisible(true);
    } else if (fecha_finalNuevaTarea.getValue().compareTo(fecha_inicioNuevaTarea.getValue()) < 0) {
      errorMsgNuevaTarea.setText("La fecha de final no puede ser anterior a la fecha de inicio.");
      errorMsgNuevaTarea.setVisible(true);
    } else {

      Prioridad pr = Prioridad.MEDIA;
      if (prioridad.getValue().toString().equals("ALTA")) {
        pr = Prioridad.ALTA;
      } else if (prioridad.getValue().toString().equals("MEDIA")) {
        pr = Prioridad.MEDIA;
      } else if (prioridad.getValue().toString().equals("BAJA")) {
        pr = Prioridad.BAJA;
      }

      // Creación e inserción en base de datos de la tarea.
      Tarea t = new Tarea(proyecto, nombreNuevaTarea.getText(), descripcionNuevaTarea.getText(), Date.valueOf(fecha_inicioNuevaTarea.getValue()), Date.valueOf(fecha_finalNuevaTarea.getValue()), pr);
      if (t.crearTarea()) {
        newTaskTab.setVisible(false);
        newTaskTab.setDisable(true);

        // Mensaje de confirmación.
        confirmMsg.setText("Tarea creada correctamente");
        confirmAlert.setVisible(true);
        confirmAlert.setDisable(false);
        mostrarTareas(proyecto);
      } else {
        // Mensaje de confirmación.
        confirmMsg.setText("Ha ocurrido un error inesperado");
        confirmAlert.setVisible(true);
        confirmAlert.setDisable(false);
      }

      resetForm(1);

    }

  }

  void confirmCloseTask(ActionEvent event) {

    alertTaskText.setText("Va a perder los datos introducidos");

    alertTask.setVisible(false);
    alertTask.setDisable(true);

    newTaskTab.setVisible(false);
    newTaskTab.setDisable(true);

  }

}
