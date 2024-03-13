package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Proyecto;
import Model.Usuario;

public class DB {

  // ---- SQL
  private static String driver = "com.mysql.cj.jdbc.Driver";
  private static String url = "jdbc:mysql://localhost/codeflow";
  private static String user = "root";
  private static String passwd = "123456";

  // Conexión
  private Connection conn;

  public DB() {
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, passwd);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Usuario getUsuario(String nombre, String password) {

    try {

      PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuario WHERE nombre = ? AND password = ?");

      ps.setString(1, nombre);
      ps.setString(2, password);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3));
      } else {
        return null;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

  }

  public boolean registrarUsuario(String nombre, String password) {

    try {

      PreparedStatement ps = conn.prepareStatement("INSERT INTO usuario (nombre, password) VALUES (?,?)");

      ps.setString(1, nombre);
      ps.setString(2, password);

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

  public List<Proyecto> getProyectos(int idUsuario) {

    List<Proyecto> proyectos = new ArrayList<>();

    try {

      PreparedStatement ps = conn.prepareStatement("SELECT * FROM proyecto WHERE idUsuario = ?");
      ps.setInt(1, idUsuario);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Proyecto p = new Proyecto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7));
        proyectos.add(p);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return proyectos;

  }

  public List<Proyecto> getProyectosAlfabetico(int idUsuario) {

    List<Proyecto> proyectos = new ArrayList<>();

    try {

      PreparedStatement ps = conn.prepareStatement("SELECT * FROM proyecto WHERE idUsuario = ? ORDER BY nombre ASC");
      ps.setInt(1, idUsuario);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Proyecto p = new Proyecto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7));
        proyectos.add(p);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return proyectos;

  }

  public List<Proyecto> getProyectosFin(int idUsuario) {

    List<Proyecto> proyectos = new ArrayList<>();

    try {

      PreparedStatement ps = conn.prepareStatement("SELECT * FROM proyecto WHERE idUsuario = ? ORDER BY fecha_final ASC");
      ps.setInt(1, idUsuario);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Proyecto p = new Proyecto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7));
        proyectos.add(p);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return proyectos;

  }

  public List<Proyecto> getProyectosTareas(int idUsuario) {

    List<Proyecto> proyectos = new ArrayList<>();

    try {

      PreparedStatement ps = conn.prepareStatement("SELECT p.* FROM proyecto p WHERE idUsuario = ? ORDER BY (SELECT count(idTarea) FROM tarea t WHERE t.idProyecto = p.idProyecto) ASC");
      ps.setInt(1, idUsuario);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Proyecto p = new Proyecto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7));
        proyectos.add(p);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return proyectos;

  }

  public boolean crearProyecto(int idUsuario, String nombre, String descripcion, String imagen, Date fecha_inicio, Date fecha_final) {

    try {

      PreparedStatement ps = conn.prepareStatement("INSERT INTO proyecto (idUsuario, nombre, descripcion, imagen, fecha_inicio, fecha_final) VALUES (?,?,?,?,?,?)");

      ps.setInt(1, idUsuario);
      ps.setString(2, nombre);
      ps.setString(3, descripcion);
      ps.setString(4, imagen);
      ps.setDate(5, fecha_inicio);
      ps.setDate(6, fecha_final);

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
