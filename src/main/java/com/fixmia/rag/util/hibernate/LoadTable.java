package com.fixmia.rag.util.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class LoadTable {
    public static List<Object[]> loadAll(String tableName){
        SessionFactory factory = InitialSessionFactory.getSessionFactory();
        Session session = factory.openSession();
        String hql = "from "+tableName;
        Query query =  session.createQuery(hql,Object[].class);
        List<Object[]> resultList = query.getResultList();
        if(resultList.size()>=1){

            return resultList;
        }else{
            String empty =  "empty";
            Object[] objects = {empty};
            resultList = new ArrayList<>();
            resultList.add(objects);
            return resultList;
        }
    }
    public static List<Object[]> loadMultiple(String tableName){
        SessionFactory factory = InitialSessionFactory.getSessionFactory();
        Session session = factory.openSession();
        String hql = "from "+tableName;
        Query query =  session.createQuery(hql,Object[].class);
        List<Object[]> resultList = query.getResultList();
        return resultList;
    }
}
