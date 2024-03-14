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
    this.proyectos = database.getProyectos(idUsuario);
    return this.proyectos;
  }

  public List<Proyecto> getProyectosAlfabetico() {
    this.proyectos = database.getProyectosAlfabetico(idUsuario);
    return this.proyectos;
  }

  public List<Proyecto> getProyectosFin() {
    this.proyectos = database.getProyectosFin(idUsuario);
    return this.proyectos;
  }

  public List<Proyecto> getProyectosTareas() {
    this.proyectos = database.getProyectosTareas(idUsuario);
    return this.proyectos;
  }

  public void setProyectos(List<Proyecto> proyectos) {
    this.proyectos = proyectos;
  }

  public boolean crearProyecto(String nombre, String descripcion, String imagen, Date fecha_inicio, Date fecha_final) {
    return database.insertarProyecto(this.idUsuario, nombre, descripcion, imagen, fecha_inicio, fecha_final);
  }

  public boolean actualizarProyecto(int idProyecto, String nombre, String descripcion, String imagen, Date fecha_inicio, Date fecha_final) {
    return database.updateProyecto(idProyecto, nombre, descripcion, imagen, fecha_inicio, fecha_final);
  }

}
