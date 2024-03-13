package Model;

import java.sql.Date;
import java.util.List;

import DAO.DB;

public class Usuario {

  private int idUsuario;
  private String nombre;
  private String password;
  private List<Proyecto> proyectos;
  private DB database = new DB();

  public Usuario(int idUsuario, String nombre, String password) {
    this.idUsuario = idUsuario;
    this.nombre = nombre;
    this.password = password;

    this.proyectos = database.getProyectos(this.idUsuario);

  }

  public int getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Proyecto> getProyectos() {
    return database.getProyectos(idUsuario);
  }

  public List<Proyecto> getProyectosAlfabetico() {
    return database.getProyectosAlfabetico(idUsuario);
  }

  public List<Proyecto> getProyectosFin() {
    return database.getProyectosFin(idUsuario);
  }

  public List<Proyecto> getProyectosTareas() {
    return database.getProyectosTareas(idUsuario);
  }

  public void setProyectos(List<Proyecto> proyectos) {
    this.proyectos = proyectos;
  }

  public boolean crearProyecto(int idUsuario, String nombre, String descripcion, String imagen, Date fecha_inicio, Date fecha_final) {
    return database.insertarProyecto(idUsuario, nombre, descripcion, imagen, fecha_inicio, fecha_final);
  }

}
