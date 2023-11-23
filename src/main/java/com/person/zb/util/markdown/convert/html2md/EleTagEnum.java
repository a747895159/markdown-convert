package com.person.zb.util.markdown.convert.html2md;

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
    TAG("tag"),

    ;

    private final String code;
}
