package com.fixmia.rag.util.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class RowChecker {

    private static List<String> columnNames = new ArrayList<>();
    private static List<Object> columnValues = new ArrayList<>();

    public static void addColumnNames(String... names) {
        for (String name : names) {
            columnNames.add(name);
        }
    }

    public static void addColumnValues(Object... values) {
        for (Object value : values) {
            columnValues.add(value);
        }
    }
    public static boolean rowExists(String tableName) {
        if (columnNames.size() != columnValues.size() || columnNames.isEmpty() || columnValues.isEmpty())
            throw new IllegalStateException("Columns and columnValues lists must be same and cannot be empty");
        SessionFactory sessionFactory = InitialSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean isRowExists = false;
        int rows = 0;
        try {
            StringBuilder hqlBuilder = new StringBuilder("from ").append(tableName).append(" as tab where ");

            for (int i = 0; i < columnNames.size(); i++) {
                if (i > 0) {
                    hqlBuilder.append(" AND ");
                }
                hqlBuilder.append(" tab.").append(columnNames.get(i)).append(" =:value").append(i);
            }
            Query<Object> query = session.createQuery(hqlBuilder.toString());

            for (int i = 0; i < columnValues.size(); i++) {
                query.setParameter("value" + i, columnValues.get(i));
            }
            rows = query.list().size();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        isRowExists = rows == 1;
        columnValues.clear();
        columnNames.clear();
        return isRowExists;
    }

    public static boolean rowExists(String tableName, String columnName, Object columnValue) {
//        table name should be entity name eg: Employee not employee from database table
        SessionFactory sessionFactory = InitialSessionFactory.getSessionFactory();

        Session session = sessionFactory.openSession();
        boolean isRowExists = false;
        int rows = 0;
        try {

            String hql = "from " + tableName + " as tab where tab." + columnName + " =:value";
            Query<Object> query = session.createQuery(hql);
            query.setParameter("value", columnValue);
            rows = query.list().size();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        if (rows >= 1) {
            isRowExists = true;
        } else {
            isRowExists = false;
        }
        return isRowExists;
    }


    public static <T> T getEntityByColumn(Class<T> entity, String columnName, Object value) {
        SessionFactory sessionFactory = InitialSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from " + entity.getName() + " as tb where tb." + columnName + " =:value";
        Query<T> query = session.createQuery(hql);
        query.setParameter("value", value);
        T tb = query.uniqueResult();
        session.close();
        return tb;
    }

}
