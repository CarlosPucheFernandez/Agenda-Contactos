/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;

/**
 *
 * @author carlo
 */
@Entity
public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    private String nombre;
    private String apellidos;
    private String empresa;
    private ArrayList<String> telefonos;
    private ArrayList<String> emails;

    public Contacto() {
    }

    public Contacto(String nombre, String apellidos, String empresa, ArrayList<String> telefono, ArrayList<String> email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.empresa = empresa;
        this.telefonos = telefono;
        this.emails = email;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public ArrayList<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<String> telefonos) {
        this.telefonos = telefonos;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "Contacto{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", empresa=" + empresa + ", telefono=" + telefonos + ", email=" + emails + '}';
    }
    
    public String getEmailFormateado(){
        String str = emails.toString().replace("[","").replace("]","");
        return str;
    }
    
    public String getTelefonoFormateado(){
        String str = telefonos.toString().replace("[","").replace("]","");
        return str;
    }
}
