package com.fixmia.rag.util;

public class ReturnMessage {
    public static String nonException(String msg){
        return "Non-Exception:"+msg;
    }
    public static String successMessage(String msg){
        return "Success:"+msg;
    }
}
