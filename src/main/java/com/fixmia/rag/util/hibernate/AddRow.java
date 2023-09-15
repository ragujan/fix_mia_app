package com.fixmia.rag.util.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AddRow {
    public static boolean addRow(Object tableName){
        SessionFactory sessionFactory = InitialSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(tableName);
            transaction.commit();
        }finally {
           if(transaction.isActive()){
               transaction.rollback();
           }
        }
        return false;
    }
}
