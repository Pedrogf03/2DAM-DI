package Model;

import java.sql.Date;

import DAO.DB;

public class Tarea implements Comparable<Tarea> {

  private int idTarea;
  private int idProyecto;
  private String nombre;
  private String descripcion;
  private Date fecha_inicio;
  private Date fecha_final;
  private Prioridad prioridad;
  private DB database = new DB();

  public Tarea(int idTarea, int idProyecto, String nombre, String descripcion, Date fecha_inicio, Date fecha_final, Prioridad prioridad) {
    this.idTarea = idTarea;
    this.idProyecto = idProyecto;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.fecha_inicio = fecha_inicio;
    this.fecha_final = fecha_final;
    this.prioridad = prioridad;
  }

  public int getIdTarea() {
    return idTarea;
  }

  public void setIdTarea(int idTarea) {
    this.idTarea = idTarea;
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

  public Prioridad getPrioridad() {
    return prioridad;
  }

  public void setPrioridad(Prioridad prioridad) {
    this.prioridad = prioridad;
  }

  public boolean borrar() {
    return database.deleteTarea(this.idTarea);
  }

  @Override
  public int compareTo(Tarea t) {
    int resultado = this.getPrioridad().compareTo(t.getPrioridad());
    if (resultado == 0) {
      if (this.fecha_final.before(t.fecha_final)) {
        resultado = -1;
      } else if (this.fecha_final.after(t.fecha_final)) {
        resultado = 1;
      } else {
        resultado = this.getNombre().compareTo(t.getNombre());
        if (resultado == 0) {
          return Integer.compare(this.idTarea, t.idTarea);
        }
      }
    }
    return resultado;
  }

}
