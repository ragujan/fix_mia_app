package com.fixmia.rag.util;

public enum PackageLists {
    CONTROLLERS("com.fixmia.rag.controllers"),
    LISTERNER("com.fixmia.rag.controllers"),
    MIDDLEWARE("com.fixmia.rag.controllers"),
    ;

    public String getPackageName() {
        return packageName;
    }

    private final String packageName;

    PackageLists(String packageName) {
      this.packageName = packageName;
    }


}
