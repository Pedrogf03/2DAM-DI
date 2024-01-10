import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class ArrowController implements Initializable {

  @FXML
  private Line line;

  @FXML
  private Polygon arrow;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

  }

  public Line getLine() {
    return line;
  }

  public Polygon getArrow() {
    return arrow;
  }

}
