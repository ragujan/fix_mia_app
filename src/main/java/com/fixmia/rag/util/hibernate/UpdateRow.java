package com.fixmia.rag.util.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UpdateRow {
    public static void update(String tableName, String columnName, String value, Object object) {
        int rowCount = getObject(tableName, columnName, value).size();
        if (rowCount == 1) {
            Session session = InitialSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                session.update(object);
                transaction.commit();
                session.close();
            } finally {
                if (transaction.isActive()) transaction.rollback();
            }
        }
    }

    public static void update(Object object) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = InitialSessionFactory.getSessionFactory().openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.update(object);
            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

    }


    private static List getObject(String tableName, String columnName, String value) {

        Session session = InitialSessionFactory.getSessionFactory().openSession();
        String hql = "from " + tableName + " as tabName where tabName." + columnName + " =:value";
        Query<Object> query = session.createQuery(hql);
        query.setParameter("value", value);
        List<Object> object = query.list();
        return object;

    }
}
