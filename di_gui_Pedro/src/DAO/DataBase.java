package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

import Model.Prioridad;
import Model.Proyecto;
import Model.Tarea;

public class DataBase {

  // ---- SQL
  private static String driver = "com.mysql.cj.jdbc.Driver";
  private static String url = "jdbc:mysql://localhost/codeflow";
  private static String user = "root";
  private static String passwd = "123456";

  // Conexión
  private static Connection conn;

  public DataBase() {
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, passwd);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Insertar proyecto
  public boolean insertarProyecto(Proyecto p) {

    try {

      PreparedStatement ps = conn.prepareStatement("INSERT INTO proyecto (nombre, descripcion, imagen, fecha_inicio, fecha_final) VALUES (?,?,?,?,?)");

      ps.setString(1, p.getNombre());
      ps.setString(2, p.getDescripcion());
      ps.setString(3, p.getImagen());
      ps.setDate(4, p.getFecha_inicio());
      ps.setDate(5, p.getFecha_final());

      int rows = ps.executeUpdate();

      if (rows > 0) {
        return true;
      } else {
        return false;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }

  }

  public Set<Proyecto> getProyectos() {

    try {

      Set<Proyecto> proyectos = new TreeSet<>();
      PreparedStatement ps = conn.prepareStatement("SELECT * FROM proyecto");
      ResultSet result = ps.executeQuery();

      while (result.next()) {
        Proyecto p = new Proyecto(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getDate(5), result.getDate(6));
        proyectos.add(p);
      }

      return proyectos;

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

  }

  public Set<Tarea> getTareas(int idProyecto) {

    try {

      Set<Tarea> tareas = new TreeSet<>();
      PreparedStatement ps = conn.prepareStatement("SELECT * FROM tarea WHERE idProyecto = ?");
      ps.setInt(1, idProyecto);
      ResultSet result = ps.executeQuery();

      while (result.next()) {

        Prioridad p = Prioridad.MEDIA;
        if (result.getString(7).equals("ALTA")) {
          p = Prioridad.ALTA;
        } else if (result.getString(7).equals("MEDIA")) {
          p = Prioridad.MEDIA;
        } else if (result.getString(7).equals("BAJA")) {
          p = Prioridad.BAJA;
        }

        Tarea t = new Tarea(result.getInt(1), result.getString(3), result.getString(4), result.getDate(5), result.getDate(6), p);
        tareas.add(t);
      }

      return tareas;

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

  }

}
