package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import javafx.util.Callback;

import javafx.event.EventHandler;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Set;

import DAO.DataBase;
import DAO.FileFun;
import Model.Proyecto;

public class CodeFlowController implements Initializable {

  private Set<Proyecto> proyectos;

  // ---- FileFun

  private FileFun file = new FileFun();

  // ---- JavaFX

  // ---- Alertas de confirmación
  @FXML
  private AnchorPane confirmAlert;
  @FXML
  private Button confirmButton;
  @FXML
  private Text confirmMsg;

  // ---- Panel de creación de proyecto
  @FXML
  private Button createProjectButton;
  @FXML
  private TextArea descripcion;
  @FXML
  private DatePicker fecha_final;
  @FXML
  private DatePicker fecha_inicio;
  @FXML
  private Button fileChooser;
  @FXML
  private TextField nombre;
  @FXML
  private Text errorMsg;
  @FXML
  private Button close;
  @FXML
  private Button newProjectButton;
  @FXML
  private AnchorPane newProjectTab;

  // ---- Panel con todos los proyectos
  @FXML
  private FlowPane flowPane;

  // Pantalla de ver proyecto

  // ---- Botón de volver
  @FXML
  private Button backButton;

  // ---- Panel con la información del proyecto
  @FXML
  private AnchorPane proyectoTab;
  @FXML
  private Text projectName;
  @FXML
  private Text projectDesc;
  @FXML
  private Text projectStartDate;
  @FXML
  private Text projectEndDate;

  // ---- Función generica de cerrar ventana.
  @FXML
  void closeWindow(ActionEvent event) {

    Button b = (Button) event.getTarget();

    Parent p = b.getParent();
    if (p.getId().equals("newProjectTab")) {
      if (!nombre.getText().equals("") || !descripcion.getText().equals("") || fecha_inicio.getValue() != null || fecha_final.getValue() != null) {
        confirmMsg.setText("Va a perder todos los datos introducidos");
        confirmAlert.setVisible(true);
        confirmAlert.setDisable(false);
      } else {
        p.setVisible(false);
        p.setDisable(true);
      }
    } else {
      p.setVisible(false);
      p.setDisable(true);
    }

  }

  // ---- Función para mostrar la ventana de creación de proyecto
  @FXML
  void showNewProjectTab(ActionEvent event) {
    newProjectTab.setVisible(true);
    newProjectTab.setDisable(false);
  }

  // ---- Mensaje de confirmación 
  @FXML
  void confirmProject(ActionEvent event) {
    confirmAlert.setVisible(false);
    confirmAlert.setDisable(true);

    newProjectTab.setVisible(false);
    newProjectTab.setDisable(true);

    resetForm();
  }

  // ---- Selector de la imagen del proyecto
  @FXML
  void fileSelector(ActionEvent event) {
    file.seleccionarImagen(fileChooser, event);
  }

  // ---- Comprobar y crear un nuevo proyecto en la base de datos
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

      resetForm();

    }

  }

  // ---- Reiniciar el formulario
  public void resetForm() {
    nombre.setText(null);
    descripcion.setText(null);
    fileChooser.setText("📁");
    fecha_inicio.setValue(null);
    fecha_final.setValue(null);
    errorMsg.setText(null);
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
          imageView.setFitWidth(216.78);
          imageView.setFitHeight(122.5);

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

          EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              // Escribir el código que quieres que se ejecute cuando se haga clic en el vbox
              showProject(p);
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

  // ---- Cambiar de ventana a la que muestra la información del proyecto
  void showProject(Proyecto p) {

    proyectoTab.setDisable(false);
    proyectoTab.setVisible(true);

    projectName.setText(p.getNombre());
    projectDesc.setText(p.getDescripcion());
    projectStartDate.setText("" + p.getFecha_inicio());
    projectEndDate.setText("" + p.getFecha_final());

  }

  // ---- Volver a la ventana que muestra todos los proyectos
  @FXML
  void goBackToProjects(ActionEvent event) {

    proyectoTab.setDisable(true);
    proyectoTab.setVisible(false);

  }

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

    mostrarProyectos();

  }

}
