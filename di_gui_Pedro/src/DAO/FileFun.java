package DAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class FileFun {

  public String imagenProyecto = null;

  public FileFun() {
  }

  public String seleccionarImagen(Button b, Event event) {

    try {
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
      File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
      if (file != null) {
        // Define la ruta de destino
        Path dest = Paths.get("img/", file.getName());

        // Copia el archivo
        Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);

        imagenProyecto = dest.toString();

        b.setText(file.getAbsolutePath());

        return imagenProyecto;
      } else {
        return null;
      }

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

  }

}
