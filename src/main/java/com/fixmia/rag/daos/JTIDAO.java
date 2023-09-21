package com.fixmia.rag.daos;

import com.fixmia.rag.entities.JTI;
import com.fixmia.rag.util.hibernate.AddRow;
import com.fixmia.rag.util.hibernate.DeleteRow;
import com.fixmia.rag.util.hibernate.LoadData;

import java.util.LinkedList;
import java.util.List;

public class JTIDAO {
    public List<JTI> getAll() {
        List<Object[]> list = LoadData.loadAll("JTI");
        List<JTI> jtiList = new LinkedList<>();
        if (list != null) {

            for (Object obj[] : list
            ) {
                JTI jti = (JTI) obj[0];
                jtiList.add(jti);
            }
        }
        return jtiList;
    }
    public void save(JTI jti){
        AddRow.addRow(jti);
    }
    public void deleteJTI(JTI jti) {
        DeleteRow.delete(jti);
    }

}
