package com.fixmia.rag.services;

import com.fixmia.rag.entities.JTI;
import com.fixmia.rag.util.hibernate.AddRow;
import com.fixmia.rag.util.hibernate.RowChecker;

public class JTIService {
    public static boolean isJTIExists(String jti){
        return RowChecker.rowExists("JTI","JTI",jti);
    }
    public static String generateJTI(){
        String genreatedJTI = generateRandStr();
        while(isJTIExists(genreatedJTI)){
            genreatedJTI = generateRandStr();
        }
        insertJTI(genreatedJTI);
        return genreatedJTI;
    }
    public static void insertJTI(String generatedJTI){
        JTI jti = new JTI();
        jti.setJTI(generatedJTI);
        AddRow.addRow(jti);
    }
    private static String generateRandStr(){
        String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            double random = Math.random();
            int ch = (int)(AlphaNumericStr.length()*random);
            builder.append(AlphaNumericStr.charAt(ch));

        }
        return builder.toString();
    }
}
