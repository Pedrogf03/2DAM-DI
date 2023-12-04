package Model;

import java.sql.Date;

public class Tarea implements Comparable<Tarea> {

  private int idTarea;
  private String nombre;
  private String descripcion;
  private Date fecha_inicio;
  private Date fecha_fin;
  private Prioridad prioridad;

  // ---- Constructor por defecto
  public Tarea() {
  }

  // ---- Constructor con todos los datos.
  public Tarea(int idTarea, String nombre, String descripcion, Date fecha_inicio, Date fecha_fin, Prioridad prioridad) {
    this.idTarea = idTarea;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.fecha_inicio = fecha_inicio;
    this.fecha_fin = fecha_fin;
    this.prioridad = prioridad;
  }

  // ---- Constructor sin id (insertar nuevo proyecto en base de datos).
  public Tarea(String nombre, String descripcion, Date fecha_inicio, Date fecha_fin, Prioridad prioridad) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.fecha_inicio = fecha_inicio;
    this.fecha_fin = fecha_fin;
    this.prioridad = prioridad;
  }

  // ---- Getters
  public int getIdTarea() {
    return idTarea;
  }

  public String getNombre() {
    return nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public Date getFecha_inicio() {
    return fecha_inicio;
  }

  public Date getFecha_fin() {
    return fecha_fin;
  }

  public Prioridad getPrioridad() {
    return prioridad;
  }

  // ---- Setters
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setFecha_inicio(Date fecha_inicio) {
    this.fecha_inicio = fecha_inicio;
  }

  public void setFecha_fin(Date fecha_fin) {
    this.fecha_fin = fecha_fin;
  }

  public void setPrioridad(Prioridad prioridad) {
    this.prioridad = prioridad;
  }

  @Override
  public int compareTo(Tarea t) {
    int resultado = this.getPrioridad().compareTo(t.getPrioridad());
    if (resultado == 0) {
      if (this.fecha_fin.before(t.fecha_fin)) {
        resultado = -1;
      } else if (this.fecha_fin.after(t.fecha_fin)) {
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
