package org.rabbit;

import org.rabbit.entity.ProjectEntity;
import org.rabbit.sql.SqlUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ConfigApplication.class, args);
        String tableSql = SqlUtils.createTableSql(ProjectEntity.class);
        System.out.println(tableSql);
    }
}
