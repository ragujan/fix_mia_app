package com.fixmia.rag.services;

import com.fixmia.rag.util.hibernate.RowChecker;

public class ServiceProviderService {

    private static String generateRandStr(){
        String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            double random = Math.random();
            int ch = (int)(AlphaNumericStr.length()*random);
            builder.append(AlphaNumericStr.charAt(ch));

        }
        return builder.toString();
    }
    public static String generateServiceProviderRandomId(){
        String genreatedId = generateRandStr();
        while(isGeneratedIdInServiceProvider(genreatedId)){
            genreatedId = generateRandStr();
        }
        return genreatedId;
    }
    public static boolean isGeneratedIdInServiceProvider(String generatedId){
        return RowChecker.rowExists("ServiceProvider","generatedId",generatedId);
    }
    public static boolean isPFPPathExists(String pfpurl){
        return RowChecker.rowExists("ServiceProviderPFP","pfpUrl",pfpurl);
    }
    public static String generatePFPPath(String pfpName){
        String genreatedJTI = generateRandStr();
        String pfpUrl = genreatedJTI+pfpName;
        while(isPFPPathExists(pfpUrl)){
            genreatedJTI = generateRandStr();
            pfpUrl = pfpName+genreatedJTI;
        }
        return pfpUrl;
    }

}
