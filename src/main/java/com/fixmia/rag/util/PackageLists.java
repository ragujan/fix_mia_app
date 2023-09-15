package com.fixmia.rag.util;

public enum PackageLists {
    CONTROLLERS("com.fixmia.rag.controllers"),
    LISTERNER("com.fixmia.rag.listener"),
    MIDDLEWARE("com.fixmia.rag.middleware"),
    FILTER("com.fixmia.rag.filter"),
    ;

    public String getPackageName() {
        return packageName;
    }

    private final String packageName;

    PackageLists(String packageName) {
      this.packageName = packageName;
    }


}
