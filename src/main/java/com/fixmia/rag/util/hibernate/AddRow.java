package com.fixmia.rag.util.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AddRow {
    public static boolean addRow(Object tableName) {
        SessionFactory sessionFactory = InitialSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        boolean transactionStatus = false;
        try {
            transaction.begin();
            session.persist(tableName);
            transaction.commit();
            transactionStatus = true;

        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (transaction.isActive()) {
                    System.out.println("Transaction is being rolled back");
                    transaction.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Transaction rollback is failed ");
                ex.printStackTrace(System.err);
            }
        }
        return transactionStatus;
    }
}
