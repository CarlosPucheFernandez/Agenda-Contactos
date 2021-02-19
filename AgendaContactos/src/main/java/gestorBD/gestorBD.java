/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorBD;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import modelos.Contacto;

/**
 *
 * @author carlo
 */
public class gestorBD {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("agendaContactos.odb");
    EntityManager em = emf.createEntityManager();

    public void guardar(Contacto c) {
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }

    public void editar(Contacto c, String nombre, String apellidos, String empresa, ArrayList<String> emails, ArrayList<String> telefonos) {
        em.getTransaction().begin();
        c.setNombre(nombre);
        c.setApellidos(apellidos);
        c.setEmpresa(empresa);
        c.setEmails(emails);
        c.setTelefonos(telefonos);
        em.getTransaction().commit();
    }

    public void eliminar(Contacto c) {
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
    }
    
    public ObservableList<Contacto> mostrarDatos(){
        ObservableList<Contacto> ob = FXCollections.observableArrayList();
        Query query = em.createQuery("SELECT c FROM Contacto c");
        ob.addAll(query.getResultList()); 
        return ob;
    }
}
