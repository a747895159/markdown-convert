package com.person.zb.util.mdconvert.html2md;


public enum EleTagEnum {
    /**
     *
     */
    ID("id"),
    CSS("css"),
    TAG("tag"),

    ;

    private final String code;

    EleTagEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
