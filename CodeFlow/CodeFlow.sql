-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema codeflow
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema codeflow
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `codeflow` DEFAULT CHARACTER SET utf8mb3 ;
USE `codeflow` ;

-- -----------------------------------------------------
-- Table `codeflow`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codeflow`.`usuario` (
  `idUsuario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `codeflow`.`proyecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codeflow`.`proyecto` (
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
    REFERENCES `codeflow`.`usuario` (`idUsuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `codeflow`.`tarea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `codeflow`.`tarea` (
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
    REFERENCES `codeflow`.`proyecto` (`idProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
