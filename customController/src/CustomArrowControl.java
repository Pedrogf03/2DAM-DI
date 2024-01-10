import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class CustomArrowControl extends AnchorPane {

  ArrowController controller;
  ObjectProperty<Color> arrowColor = new SimpleObjectProperty<>(Color.BLACK);

  public CustomArrowControl() {

    super();

    try {

      FXMLLoader loader = new FXMLLoader(getClass().getResource("Arrow.fxml"));
      controller = new ArrowController();
      loader.setController(controller);
      Node n = loader.load();

      this.getChildren().add(n);

      controller.getLine().fillProperty().bind(arrowColor);
      controller.getArrow().fillProperty().bind(arrowColor);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public ArrowController getController() {
    return controller;
  }

  public void setController(ArrowController controller) {
    this.controller = controller;
  }

  public ObjectProperty<Color> getArrowColor() {
    return arrowColor;
  }

  public void setArrowColor(ObjectProperty<Color> arrowColor) {
    this.arrowColor = arrowColor;
  }

}
