-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema CodeFlow
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema CodeFlow
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CodeFlow` DEFAULT CHARACTER SET utf8 ;
USE `CodeFlow` ;

-- -----------------------------------------------------
-- Table `CodeFlow`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CodeFlow`.`Usuario` (
  `idUsuario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CodeFlow`.`Proyecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CodeFlow`.`Proyecto` (
  `idProyecto` INT NOT NULL AUTO_INCREMENT,
  `idUsuario` INT UNSIGNED NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(200) NOT NULL,
  `imagen` VARCHAR(200) NOT NULL,
  `fecha_inicio` DATE NOT NULL,
  `fecha_final` DATE NOT NULL,
  PRIMARY KEY (`idProyecto`),
  INDEX `fk_Proyecto_Usuario_idx` (`idUsuario` ASC) VISIBLE,
  CONSTRAINT `fk_Proyecto_Usuario`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `CodeFlow`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CodeFlow`.`Tarea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CodeFlow`.`Tarea` (
  `idTarea` INT NOT NULL AUTO_INCREMENT,
  `idProyecto` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(200) NOT NULL,
  `fecha_inicio` DATE NOT NULL,
  `fecha_final` DATE NOT NULL,
  `prioridad` ENUM('BAJA', 'MEDIA', 'ALTA') NOT NULL,
  PRIMARY KEY (`idTarea`),
  INDEX `fk_Tareas_Proyecto1_idx` (`idProyecto` ASC) VISIBLE,
  CONSTRAINT `fk_Tareas_Proyecto1`
    FOREIGN KEY (`idProyecto`)
    REFERENCES `CodeFlow`.`Proyecto` (`idProyecto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
