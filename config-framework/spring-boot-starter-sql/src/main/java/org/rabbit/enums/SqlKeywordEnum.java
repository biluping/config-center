package org.rabbit.enums;

import lombok.Getter;

@Getter
public enum SqlKeywordEnum {
    NOT_NULL("not null"),
    DEFAULT("default"),
    PRIMARY_KEY("primary key"),
    COMMENT("comment"),
    AUTO_INCREMENT("auto_increment");

    private final String keyword;

    SqlKeywordEnum(String keyword){
        this.keyword = keyword;
    }
}