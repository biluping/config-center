package org.rabbit;

import org.rabbit.conf.SqlProperties;
import org.rabbit.parser.DbTableParse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SqlProperties.class)
public class SqlBuildAutoConfiguration {

    /**
     * 用于 springboot 启动阶段，获取数据库表信息
     */
    @Bean
    public GenerateSqlRunner getDbMetadataRunner(){
        return new GenerateSqlRunner();
    }

    /**
     * 数据库表解析器
     */
    @Bean
    public DbTableParse dbTableParse(){
        return new DbTableParse();
    }
}
