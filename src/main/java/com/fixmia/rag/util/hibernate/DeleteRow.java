package com.fixmia.rag.util.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DeleteRow {
    public static void delete(Object object) {
        SessionFactory sessionFactory = null;
        Session session = null;
        try {
            sessionFactory = InitialSessionFactory.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }
}
