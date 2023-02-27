package org.rabbit.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sql")
@Data
public class SqlProperties {

    private String basePackage;
}
