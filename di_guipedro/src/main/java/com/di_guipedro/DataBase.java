package com.di_guipedro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

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

      PreparedStatement ps = conn.prepareStatement(
          "INSERT INTO proyecto (nombre, descripcion, imagen, fecha_inicio, fecha_final) VALUES (?,?,?,?,?)");

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

  // Insertar tarea
  public boolean insertarTarea(Tarea t) {

    try {

      PreparedStatement ps = conn.prepareStatement(
          "INSERT INTO tarea (idProyecto, nombre, descripcion, fecha_inicio, fecha_fin, prioridad) VALUES (?,?,?,?,?,?)");

      ps.setInt(1, t.getProyecto().getIdProyecto());
      ps.setString(2, t.getNombre());
      ps.setString(3, t.getDescripcion());
      ps.setDate(4, t.getFecha_inicio());
      ps.setDate(5, t.getFecha_fin());
      ps.setString(6, t.getPrioridad());

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
        Proyecto p = new Proyecto(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
            result.getDate(5), result.getDate(6));
        proyectos.add(p);
      }

      return proyectos;

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

  }

  public Set<Tarea> getTareas(Proyecto p) {

    try {

      Set<Tarea> tareas = new TreeSet<>();
      PreparedStatement ps = conn.prepareStatement("SELECT * FROM tarea WHERE idProyecto = ?");
      ps.setInt(1, p.getIdProyecto());
      ResultSet result = ps.executeQuery();

      while (result.next()) {

        Prioridad pr = Prioridad.MEDIA;
        if (result.getString(7).equals("ALTA")) {
          pr = Prioridad.ALTA;
        } else if (result.getString(7).equals("MEDIA")) {
          pr = Prioridad.MEDIA;
        } else if (result.getString(7).equals("BAJA")) {
          pr = Prioridad.BAJA;
        }

        Tarea t = new Tarea(result.getInt(1), p, result.getString(3), result.getString(4), result.getDate(5),
            result.getDate(6), pr);
        tareas.add(t);
      }

      return tareas;

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

  }

  public boolean borrarProyecto(Proyecto p) {
    try {

      PreparedStatement ps = conn.prepareStatement("DELETE FROM proyecto WHERE idProyecto = ?");
      ps.setInt(1, p.getIdProyecto());

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

  public boolean borrarTarea(Tarea t) {
    try {

      PreparedStatement ps = conn.prepareStatement("DELETE FROM tarea WHERE idTarea = ?");
      ps.setInt(1, t.getIdTarea());

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

  public boolean updateProyecto(Proyecto p) {

    try {

      PreparedStatement ps = conn.prepareStatement(
          "UPDATE proyecto SET nombre = ?, descripcion = ?, imagen = ?, fecha_inicio = ?, fecha_final = ? WHERE idProyecto = ?");

      ps.setString(1, p.getNombre());
      ps.setString(2, p.getDescripcion());
      ps.setString(3, p.getImagen());
      ps.setDate(4, p.getFecha_inicio());
      ps.setDate(5, p.getFecha_final());

      ps.setInt(6, p.getIdProyecto());

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

  public boolean updateTarea(Tarea t) {

    try {

      PreparedStatement ps = conn.prepareStatement(
          "UPDATE tarea SET nombre = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ?, prioridad = ? WHERE idTarea = ?");

      ps.setString(1, t.getNombre());
      ps.setString(2, t.getDescripcion());
      ps.setDate(3, t.getFecha_inicio());
      ps.setDate(4, t.getFecha_fin());
      ps.setString(5, t.getPrioridad());

      ps.setInt(6, t.getIdTarea());

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

}
