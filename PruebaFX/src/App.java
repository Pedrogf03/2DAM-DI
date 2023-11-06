import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
  public static void main(String[] args) throws Exception {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {

    VBox root = new VBox();
    Label label = new Label("Hola mundo");
    Label label2 = new Label("Hello World");

    root.setAlignment(Pos.CENTER);

    label.setStyle("-fx-font-size:30px; -fx-text-fill:red;");

    root.getChildren().addAll(label);
    root.getChildren().addAll(label2);

    Scene scene = new Scene(root, 400, 300);
    stage.setScene(scene);
    stage.setTitle("Hello JFX!");

    stage.show();

  }
}
