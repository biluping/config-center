package org.rabbit.parser;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.annotation.TableName;
import org.rabbit.flow.FlowExecutor;
import org.rabbit.flow.component.column.ColumnMetadataBuildCmp;
import org.rabbit.metadata.ColumnMetadata;
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
     * 根据实体类进行表信息解析
     */
    public static TableMetadata parse(Class<?> entity){

        // 参数校验
        Assert.notNull(entity, "传入的实体类不能为空");
        TableName tableNameAnno = entity.getAnnotation(TableName.class);
        Assert.notNull(tableNameAnno, "实体类 [{}] 未标注 @TableName 注解", entity.getName());
        Assert.notBlank(tableNameAnno.value(), "实体类 [{}] 标注的 @TableName 中表名不能为空", entity.getName());

        TableMetadata tableMetadata = new TableMetadata();
        tableMetadata.setTableName(tableNameAnno.value());

        // 字段解析, 调用流程引擎
        List<ColumnMetadata> columnMetadataList = ColumnParser.parse(entity);
        tableMetadata.setColumnMetadataList(columnMetadataList);

        return tableMetadata;
    }

    /**
     * 根据表名从数据库查询表信息并进行表信息解析
     */
    public static TableMetadata parse(String tableName){
        return null;
    }
}
