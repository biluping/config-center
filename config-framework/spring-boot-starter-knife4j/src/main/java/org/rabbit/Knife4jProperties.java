package org.rabbit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "knife")
public class Knife4jProperties {

    private String title;
    private String version = "1.0";
    private String description;
    private String author;

}
