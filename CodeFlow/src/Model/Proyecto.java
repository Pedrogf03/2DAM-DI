package Model;

import java.sql.Date;
import java.util.List;

import DAO.DB;

public class Proyecto {

  private int idProyecto;
  private int idUsuario;
  private String nombre;
  private String descripcion;
  private String imagen = "default.png";
  private Date fecha_inicio;
  private Date fecha_final;
  private List<Tarea> tareas;
  private DB database = new DB();

  public Proyecto(int idProyecto, int idUsuario, String nombre, String descripcion, String imagen, Date fecha_inicio, Date fecha_final) {
    this.idProyecto = idProyecto;
    this.idUsuario = idUsuario;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.imagen = imagen;
    this.fecha_inicio = fecha_inicio;
    this.fecha_final = fecha_final;

    this.tareas = database.getTareas(this.idProyecto);

  }

  public Proyecto(int idProyecto, int idUsuario, String nombre, String descripcion, Date fecha_inicio, Date fecha_final) {
    this.idProyecto = idProyecto;
    this.idUsuario = idUsuario;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.fecha_inicio = fecha_inicio;
    this.fecha_final = fecha_final;

    this.tareas = database.getTareas(this.idProyecto);

  }

  public int getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(int idProyecto) {
    this.idProyecto = idProyecto;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getImagen() {
    return imagen;
  }

  public void setImagen(String imagen) {
    this.imagen = imagen;
  }

  public Date getFecha_inicio() {
    return fecha_inicio;
  }

  public void setFecha_inicio(Date fecha_inicio) {
    this.fecha_inicio = fecha_inicio;
  }

  public Date getFecha_final() {
    return fecha_final;
  }

  public void setFecha_final(Date fecha_final) {
    this.fecha_final = fecha_final;
  }

  public int getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public List<Tarea> getTareas() {
    return tareas;
  }

  public void setTareas(List<Tarea> tareas) {
    this.tareas = tareas;
  }

  public boolean borrar() {
    return database.deleteProyecto(this.idProyecto);
  }

}
