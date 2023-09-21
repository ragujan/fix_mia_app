package com.fixmia.rag.util.hibernate;

import com.fixmia.rag.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class LoadData {
    public static List<Object[]> loadAll(String tableName) {
        SessionFactory factory = InitialSessionFactory.getSessionFactory();
        Session session = factory.openSession();
        String hql = "from " + tableName;
        Query query = session.createQuery(hql, Object[].class);
        List<Object[]> resultList = query.getResultList();
        session.close();
        return resultList;

    }

    public static List<Object[]> loadMultiple(String tableName) {
        SessionFactory factory = InitialSessionFactory.getSessionFactory();
        Session session = factory.openSession();
        String hql = "from " + tableName;
        Query query = session.createQuery(hql, Object[].class);
        List<Object[]> resultList = query.getResultList();
        session.close();
        return resultList;
    }

    public static <T>T loadSingleData(String tableName, String columnName,Object value) {
        SessionFactory factory = InitialSessionFactory.getSessionFactory();
        Session session = factory.openSession();
        String hql = "from " + tableName+" as tab where tab."+columnName+" =:value";
        Query<T> query = session.createQuery(hql,(Class<T>) Object.class);
        query.setParameter("value",value);
        T result = query.uniqueResult();
        session.close();
        return result;

    }

}
