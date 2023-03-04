package org.rabbit.metadata;

import lombok.Data;
import org.rabbit.enums.IndexEnum;

import java.util.Arrays;
import java.util.Objects;

/**
 * 列索引元数据
 */
@Data
public class ColumnIndexMetadata {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 索引类型
     */
    private IndexEnum indexEnum;

    /**
     * 索引所有字段
     */
    private String[] fields;

    /**
     * 索引长度
     */
    private Integer len;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnIndexMetadata that)) return false;
        return Objects.equals(getIndexName(), that.getIndexName()) &&
                getIndexEnum() == that.getIndexEnum() &&
                Arrays.equals(getFields(), that.getFields());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getIndexName(), getIndexEnum());
        result = 31 * result + Arrays.hashCode(getFields());
        return result;
    }
}
