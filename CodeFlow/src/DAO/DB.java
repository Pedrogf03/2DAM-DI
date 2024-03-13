package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
