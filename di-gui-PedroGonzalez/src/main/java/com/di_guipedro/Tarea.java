package com.di_guipedro;

import java.sql.Date;

public class Tarea implements Comparable<Tarea> {

  private DataBase db = new DataBase();
  private int idTarea;
  private Proyecto p;
  private String nombre;
  private String descripcion;
  private Date fecha_inicio;
  private Date fecha_fin;
  private Prioridad prioridad;

  // ---- Constructor por defecto
  public Tarea() {
  }

  // ---- Constructor con todos los datos.
  public Tarea(int idTarea, Proyecto p, String nombre, String descripcion, Date fecha_inicio, Date fecha_fin, Prioridad prioridad) {
    this.idTarea = idTarea;
    this.p = p;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.fecha_inicio = fecha_inicio;
    this.fecha_fin = fecha_fin;
    this.prioridad = prioridad;
  }

  // ---- Constructor sin id (insertar nueva tarea en base de datos).
  public Tarea(Proyecto p, String nombre, String descripcion, Date fecha_inicio, Date fecha_fin, Prioridad prioridad) {
    this.p = p;
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

  public Proyecto getProyecto() {
    return p;
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

  public String getPrioridad() {
    return prioridad.toString();
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

  public boolean crearTarea() {
    return db.insertarTarea(this);
  }

  public boolean eliminar() {
    return db.borrarTarea(this);
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

  public boolean update() {
    return db.updateTarea(this);
  }

}
