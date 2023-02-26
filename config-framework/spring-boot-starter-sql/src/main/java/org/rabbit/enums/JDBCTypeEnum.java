package org.rabbit.enums;

import cn.hutool.core.lang.Assert;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * java 类型 与 数据库类型枚举
 */
@Getter
public enum JDBCTypeEnum {

    VARCHAR(String.class, "varchar(%d)"),
    INT(Integer.class, "int"),
    BIGINT(Long.class, "bigint"),
    DATETIME(LocalDateTime.class, "datetime");

    private final Class<?> javaType;
    private final String jdbcType;

    private static final Map<Class<?>, String> cache;

    JDBCTypeEnum(Class<?> javaType, String jdbcType) {
        this.javaType = javaType;
        this.jdbcType = jdbcType;
    }

    /*
      初始化缓存
     */
    static {
        cache = new HashMap<>();
        for (JDBCTypeEnum typeEnum : JDBCTypeEnum.values()) {
            cache.put(typeEnum.javaType, typeEnum.jdbcType);
        }
    }

    /**
     * 根据 java 类型取 jdbc 类型
     */
    public static String getJdbcType(Class<?> javaType, Integer len){
        String jdbcType = cache.get(javaType);
        Assert.notNull(jdbcType, "不支持生成该类型的 sql, javaType: {}", javaType.getName());
        return String.format(jdbcType, len);
    }
}
