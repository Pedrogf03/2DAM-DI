module com.di_guipedro {
  requires transitive javafx.controls;
  requires transitive javafx.fxml;
  requires transitive java.sql;
  requires transitive jasperreports;
  requires transitive javahelp;

  opens com.di_guipedro to javafx.fxml;

  exports com.di_guipedro;
}
