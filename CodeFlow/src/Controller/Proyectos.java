package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import DAO.DB;
import Model.Proyecto;
import Model.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Proyectos implements Initializable {

  private static Usuario usuario;
  private DB database = new DB();
  private List<Proyecto> proyectos;

  @FXML
  private Button botonCrearProyecto;

  @FXML
  private Button botonFiltrar;

  @FXML
  private ChoiceBox<String> filtro;

  @FXML
  private FlowPane flowPane;
  @FXML
  private ScrollPane scrollPane;

  public static Usuario getUsuario() {
    return usuario;
  }

  public static void setUsuario(Usuario usuario) {
    Proyectos.usuario = usuario;
  }

  @FXML
  void crearProyecto(ActionEvent event) throws IOException {
    NuevoProyecto.setUsuario(usuario);
    CodeFlow.setRoot("nuevoProyecto");
  }

  @FXML
  void filtrar() {
    if (filtro.getValue() != null) {
      if (filtro.getValue().equals("Por defecto")) {
        proyectos = database.getProyectos(usuario.getIdUsuario());
      } else if (filtro.getValue().equals("Orden Alfabético")) {
        proyectos = database.getProyectosAlfabetico(usuario.getIdUsuario());
      } else if (filtro.getValue().equals("Finalizan antes")) {
        proyectos = database.getProyectosFin(usuario.getIdUsuario());
      } else if (filtro.getValue().equals("Nº Tareas")) {
        proyectos = database.getProyectosTareas(usuario.getIdUsuario());
      }
      mostrarProyectos();
    }
  }

  public void mostrarProyectos() {

    flowPane.getChildren().clear();

    if (!proyectos.isEmpty()) {
      for (Proyecto p : proyectos) {
        try {
          Image image = new Image(getClass().getResource("/img/" + p.getImagen()).toExternalForm());
          ImageView imageView = new ImageView(image);

          imageView.setFitWidth(236);
          imageView.setFitHeight(133);

          Label label = new Label(p.getNombre());
          label.setFont(Font.font("Franklin Gothic Demi"));
          label.setTextFill(Color.web("#FFC107"));

          VBox vbox = new VBox();
          vbox.setPadding(new Insets(10, 10, 10, 10));
          vbox.getChildren().addAll(imageView, label);

          EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              // TODO: mostrarTareas(p);
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

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);

    filtro.getItems().addAll("Por defecto", "Orden Alfabético", "Finalizan antes", "Nº Tareas");
    filtro.setValue("Por defecto");

    filtrar();
  }

}
