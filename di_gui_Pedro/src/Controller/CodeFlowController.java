package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.Node;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CodeFlowController implements Initializable {

  // ---- SQL
  private static String driver = "com.mysql.cj.jdbc.Driver";
  private static String url = "jdbc:mysql://localhost/codeflow";
  private static String user = "root";
  private static String passwd = "123456";

  // ---- File

  // Método para obtener la extensión del archivo
  private String getFileExtension(File file) {
    String fileName = file.getName();
    int dotIndex = fileName.lastIndexOf('.');
    return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
  }

  private String imagePath;

  // ---- JavaFX

  @FXML
  private Button createProjectButton;

  @FXML
  private TextArea descripcion;

  @FXML
  private Text errorMsg;

  @FXML
  private DatePicker fecha_final;

  @FXML
  private DatePicker fecha_inicio;

  @FXML
  private Button fileChooser;

  @FXML
  private Button newProjectButton;

  @FXML
  private AnchorPane newProjectTab;

  @FXML
  private TextField nombre;

  @FXML
  void showNewProjectTab(ActionEvent event) {

    newProjectTab.setVisible(true);
    newProjectTab.setDisable(false);

  }

  @FXML
  void fileSelector(ActionEvent event) {

    try {

      Class.forName(driver);

      Connection conn = DriverManager.getConnection(url, user, passwd);

      PreparedStatement ps = conn.prepareStatement("SELECT MAX(idProyecto) FROM Proyecto");

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        // Crear la carpeta de destino.

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
          // Define la ruta de destino
          Path dest = Paths.get("img/", "proyecto" + rs.getInt(1) + "Img" + getFileExtension(file));

          // Copia el archivo
          Files.copy(file.toPath(), dest);

          imagePath = dest.toString();

          this.fileChooser.setText(file.getAbsolutePath());

        }

      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @FXML
  void createProject(ActionEvent event) {

    try {

      Class.forName(driver);

      Connection conn = DriverManager.getConnection(url, user, passwd);

      PreparedStatement ps = conn.prepareStatement("INSERT INTO Proyecto (nombre, descripcion, imagen, fecha_inicio, fecha_final) VALUES (?, ?, ?, ?, ?)");

      if (nombre.getText().equals("") || descripcion.getText().equals("") || fecha_inicio.getValue() == null || fecha_final.getValue() == null) {
        errorMsg.setVisible(true);
      } else {

        ps.setString(1, nombre.getText());
        ps.setString(2, descripcion.getText());

        // Comprobar si se ha subido una imagen.
        if (imagePath != "") {
          ps.setString(3, imagePath);
        } else {
          ps.setString(3, null);
        }

        ps.setDate(4, Date.valueOf(fecha_inicio.getValue()));
        ps.setDate(5, Date.valueOf(fecha_final.getValue()));

        int rows = ps.executeUpdate();

        if (rows > 0) {
          System.out.println("Registro insertado con éxito.");
        }

      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

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
            setDisable(empty || item.compareTo(LocalDate.now().plusDays(1)) < 0);
          }
        };
      }
    });

  }

}
