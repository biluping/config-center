package org.rabbit;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Knife4jProperties.class)
public class Knife4jAutoConfiguration {

    /**
     * 根据 @Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
//    @Bean
//    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
//        return openApi -> {
//            if (openApi.getTags()!=null){
//                openApi.getTags().forEach(tag -> {
//                    Map<String,Object> map=new HashMap<>();
//                    map.put("x-order", RandomUtil.randomInt(0,100));
//                    tag.setExtensions(map);
//                });
//            }
//            if(openApi.getPaths()!=null){
//                openApi.addExtension("x-test123","333");
//                openApi.getPaths().addExtension("x-abb",RandomUtil.randomInt(1,100));
//            }
//
//        };
//    }

//    @Bean
//    public GroupedOpenApi userApi(){
//        String[] paths = { "/**" };
//        String[] packagedToMatch = { "org.rabbit.controller" };
//        return GroupedOpenApi.builder().group("核心模块")
//                .pathsToMatch(paths)
//                .addOperationCustomizer((operation, handlerMethod) -> {
//                    return operation.addParametersItem(new HeaderParameter().name("groupCode").example("测试").description("集团code").schema(new StringSchema()._default("BR").name("groupCode").description("集团code")));
//                })
//                .packagesToScan(packagedToMatch).build();
//    }
    @Bean
    public OpenAPI customOpenAPI(Knife4jProperties knife4jProperties) {
        Contact contact = new Contact();
        contact.setName(knife4jProperties.getAuthor());

        return new OpenAPI()
                .info(new Info()
                        .title(knife4jProperties.getTitle())
                        .version(knife4jProperties.getVersion())
                        .description(knife4jProperties.getDescription())
                        .contact(contact)
                        .termsOfService("http://doc.xiaominfo.com")
                        .license(new License().name("Apache 2.0")
                                .url("http://doc.xiaominfo.com")));
    }


}
