module com.exaula.prueba1 {
  requires transitive javafx.controls;
  requires javafx.fxml;

  opens com.exaula.prueba1 to javafx.fxml;

  exports com.exaula.prueba1;
}
