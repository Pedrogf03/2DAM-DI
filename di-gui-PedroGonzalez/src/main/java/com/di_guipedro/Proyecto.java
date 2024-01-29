package com.di_guipedro;

import java.sql.Date;
import java.util.Set;

public class Proyecto implements Comparable<Proyecto> {

  private DataBase db = new DataBase();
  private int idProyecto;
  private String nombre;
  private String descripcion;
  private String imagen = "default.png";
  private Date fecha_inicio;
  private Date fecha_final;
  private Set<Tarea> tareas;

  // ---- Constructor por defecto.
  public Proyecto() {
  }

  // ---- Constructor con todos los datos.
  public Proyecto(int idProyecto, String nombre, String descripcion, String imagen, Date fecha_inicio, Date fecha_final) {
    this.idProyecto = idProyecto;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.imagen = imagen;
    this.fecha_inicio = fecha_inicio;
    this.fecha_final = fecha_final;
  }

  // ---- Constructor sin id (insertar nuevo proyecto en base de datos).
  public Proyecto(String nombre, String descripcion, String imagen, Date fecha_inicio, Date fecha_final) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    if (imagen != null) {
      this.imagen = imagen;
    }
    this.fecha_inicio = fecha_inicio;
    this.fecha_final = fecha_final;
  }

  // ---- Getters
  public int getIdProyecto() {
    return idProyecto;
  }

  public String getNombre() {
    return nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getImagen() {
    return imagen;
  }

  public Date getFecha_inicio() {
    return fecha_inicio;
  }

  public Date getFecha_final() {
    return fecha_final;
  }

  public Set<Tarea> getTareas() {
    this.tareas = db.getTareas(this);
    return tareas;
  }

  // ---- Setters
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setImagen(String imagen) {
    this.imagen = imagen;
  }

  public void setFecha_inicio(Date fecha_inicio) {
    this.fecha_inicio = fecha_inicio;
  }

  public void setFecha_final(Date fecha_final) {
    this.fecha_final = fecha_final;
  }

  // ---- Metodos
  public boolean crearProyecto() {
    return db.insertarProyecto(this);
  }

  public boolean eliminar() {
    return db.borrarProyecto(this);
  }

  @Override
  public int compareTo(Proyecto p) {
    if (this.fecha_final.before(p.fecha_final)) {
      return -1;
    } else if (this.fecha_final.after(p.fecha_final)) {
      return 1;
    } else {
      int resultado = this.getNombre().compareTo(p.getNombre());
      if (resultado == 0) {
        return Integer.compare(this.idProyecto, p.idProyecto);
      } else {
        return resultado;
      }
    }
  }

  public boolean update() {
    return db.updateProyecto(this);
  }

}
