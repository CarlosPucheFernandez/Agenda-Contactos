/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.agendacontactos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Administrador
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("p.odb");
        EntityManager em = emf.createEntityManager();
        
        
        persona p = new persona(0,"Francisco","Romero","2DAM");
        
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        
        em.close();
        emf.close();
    }
    
}
