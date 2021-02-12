/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.agendacontactos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Administrador
 */

@Entity
public class persona implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue
    private Integer id;
    
    private String nombre;
    private String apellidos;
    private String curso;

    public persona() {
    }

    public persona(Integer id, String nombre, String apellidos, String curso) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.curso = curso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "persona{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", curso=" + curso + '}';
    }
 
    
}
