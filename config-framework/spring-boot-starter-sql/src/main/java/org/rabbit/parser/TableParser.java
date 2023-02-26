package org.rabbit.parser;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import org.rabbit.flow.FlowExecutor;
import org.rabbit.flow.component.column.ColumnMetadataBuildCmp;
import org.rabbit.metadata.ColumnMetadata;
import org.rabbit.metadata.PrimaryKeyColumnMetadata;
import org.rabbit.metadata.TableMetadata;

import java.util.List;

/**
 * 表信息解析器
 */
public class TableParser {

    public static final String FLOW_NAME = "column-metadata-parse-flow";

    // 将解析组件添加到流程中
    static {
        FlowExecutor.addFlow(FLOW_NAME, ColumnMetadataBuildCmp.class);
    }

    /**
     * 表信息解析
     */
    public static TableMetadata parse(Class<?> entity){

        // 参数校验
        TableName tableNameAnno = entity.getAnnotation(TableName.class);
        if (ObjectUtil.isNull(tableNameAnno) || StrUtil.isBlank(tableNameAnno.value())) {
            throw new IllegalArgumentException("实体类注解不正确， 请检查 @TableName 注解: " + entity);
        }

        TableMetadata tableMetadata = new TableMetadata();
        tableMetadata.setTableName(tableNameAnno.value());

        // 主键解析
        PrimaryKeyColumnMetadata primaryKeyMetadata = PrimaryKeyParser.parse(entity);
        tableMetadata.setPrimaryKeyMetadata(primaryKeyMetadata);

        // 普通字段解析, 调用流程引擎
        List<ColumnMetadata> columnMetadataList = ColumnParser.parse(entity);
        tableMetadata.setColumnMetadataList(columnMetadataList);

        return tableMetadata;
    }
}
