package Model;

import java.sql.Date;

import DAO.DataBase;

public class Proyecto implements Comparable<Proyecto> {

  private static DataBase db = new DataBase();

  private int idProyecto;
  private String nombre;
  private String descripcion;
  private String imagen = "defaultProjectImg.png";
  private Date fecha_inicio;
  private Date fecha_final;

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

  // ---- Getterss
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

  // ---- Setters
  public void setIdProyecto(int idProyecto) {
    this.idProyecto = idProyecto;
  }

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

  @Override
  public int compareTo(Proyecto o) {

    if (this.idProyecto > o.getIdProyecto()) {
      return 1;
    } else if (this.idProyecto < o.getIdProyecto()) {
      return -1;
    } else {
      return 0;
    }

  }

}
