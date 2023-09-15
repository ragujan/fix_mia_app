package com.fixmia.rag.util.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class InitialSessionFactory {
    private static SessionFactory sessionFactory  = getSessionFactory();

    public static SessionFactory buildSessionFactory(){
        try {
            return new Configuration().configure().buildSessionFactory();
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ExceptionInInitializerError();
        }
    }
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }
}
