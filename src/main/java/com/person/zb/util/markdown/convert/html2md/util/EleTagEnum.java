package com.person.zb.util.markdown.convert.html2md.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EleTagEnum {
    /**
     *
     */
    ID("id"),
    CSS("css"),

    ;

    private final String code;
}