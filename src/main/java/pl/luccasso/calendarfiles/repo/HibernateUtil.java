/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.repo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author piko
 */
public class HibernateUtil {
    
    private static SessionFactory sessionF;
    
    static{        
        sessionF = new Configuration()
                .configure()
                .buildSessionFactory();
    }
    
    public static SessionFactory getFactory(){
        return sessionF;
    }

    private  HibernateUtil() {
    }
    
}
