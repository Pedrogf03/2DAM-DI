package com.di_guipedro;

import org.junit.Test;
import static org.junit.Assert.*;
import com.di_guipedro.Tarea;

class TareaTest {
  @Test
  void testAddTareaValidoMinimo() {
    Tarea tarea = new Tarea();
    assertTrue(tarea.addTarea("", "", "0/0/0", "0/0/0", "BAJA"));
  }

  @Test
  void testAddTareaValidoRango() {
    Tarea tarea = new Tarea();
    assertTrue(tarea.addTarea("Nombre de tarea", "Descripción de tarea", "fecha_inicio_proyecto", "fecha_inicio_tarea", "MEDIA"));
  }

  @Test
  void testAddTareaValidoMaximo() {
    Tarea tarea = new Tarea();
    assertTrue(tarea.addTarea("N".repeat(45), "D".repeat(200), "fecha_final_proyecto", "fecha_final_proyecto", "ALTA"));
  }

  @Test
  void testAddTareaInvalidoNombre() {
    Tarea tarea = new Tarea();
    assertFalse(tarea.addTarea("N".repeat(46), "D".repeat(200), "fecha_final_proyecto", "fecha_final_proyecto", "ALTA"));
  }

  @Test
  void testAddTareaInvalidoDescripcion() {
    Tarea tarea = new Tarea();
    assertFalse(tarea.addTarea("N".repeat(45), "D".repeat(201), "fecha_final_proyecto", "fecha_final_proyecto", "ALTA"));
  }

  @Test
  void testAddTareaInvalidoFecha() {
    Tarea tarea = new Tarea();
    assertFalse(tarea.addTarea("N".repeat(45), "D".repeat(200), "∞/∞/∞", "∞/∞/∞", "ALTA"));
  }
}
