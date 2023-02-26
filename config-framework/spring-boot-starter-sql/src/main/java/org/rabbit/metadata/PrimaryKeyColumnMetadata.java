package org.rabbit.metadata;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 列信息，排除主键
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PrimaryKeyColumnMetadata extends ColumnMetadata {

    /**
     * 是否自增
     */
    private Boolean isAutoIncrement;
}
