package org.rabbit.enums;

import lombok.Getter;

@Getter
public enum SqlKeywordEnum {
    NOT_NULL("not null"),
    DEFAULT("default"),
    PRIMARY_KEY("PRIMARY KEY (`{}`)"),
    NORMAL_KEY("INDEX `{}`(`{}`) USING BTREE"),
    UNIQUE_KEY("UNIQUE INDEX `{}`(`{}`) USING BTREE"),
    COMMENT("comment"),
    AUTO_INCREMENT("auto_increment"),
    ADD_COLUMN("alter table {} add column"),
    MODIFY_COLUMN("alter table {} modify column"),
    DROP_COLUMN("alter table {} drop column"),
    CREATE_TABLE_TEMPLATE("create table {} (\n{}\n) comment '{}' ;"),
    ALTER_TABLE_COMMENT("alter table {} COMMENT = '{}';");


    private final String keyword;

    SqlKeywordEnum(String keyword){
        this.keyword = keyword;
    }
}
