module com.sorteoalumnos {
  requires transitive javafx.controls;
  requires transitive javafx.fxml;

  opens com.sorteoalumnos to javafx.fxml;

  exports com.sorteoalumnos;
}
