import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(Stage stage) throws Exception {

    VBox root = new VBox(); // Escena principa.

    // Label y cuadro de texto del nombre.
    Label nombre = new Label("Nombre");
    nombre.setFont(new Font(15));

    TextField nombreField = new TextField();
    VBox.setMargin(nombreField, new Insets(10, 0, 10, 0));

    // Label y cuadro de texto de los apellidos.
    Label apellidos = new Label("Apellidos");
    apellidos.setFont(new Font(15));

    TextField apellidosField = new TextField();
    VBox.setMargin(apellidosField, new Insets(10, 0, 10, 0));

    // Label y cuadro de texto de la edad.
    Label edad = new Label("Edad");
    edad.setFont(new Font(15));

    TextField edadField = new TextField();
    VBox.setMargin(edadField, new Insets(10, 0, 10, 0));

    // Botones de la aplicacion.
    Button save = new Button("Guardar");
    save.setMaxWidth(Double.MAX_VALUE);
    VBox.setMargin(save, new Insets(20, 0, 0, 0));

    Button exit = new Button("Salir");
    exit.setMaxWidth(Double.MAX_VALUE);
    VBox.setMargin(exit, new Insets(10, 0, 10, 0));

    exit.setOnAction(e -> {
      System.exit(0);
    });

    // Añadir los nodos y configurar el root.
    root.getChildren().addAll(nombre, nombreField, apellidos, apellidosField, edad, edadField, save, exit);
    root.setAlignment(Pos.CENTER_LEFT);
    root.setPadding(new Insets(15, 15, 15, 15));

    // Crear escena y mostrarla por pantalla.
    Scene scene = new Scene(root, 250, 350);
    stage.setTitle("Formulario");
    stage.setScene(scene);
    stage.show();

  }

  public static void main(String[] args) throws Exception {
    launch(args);
  }

}
