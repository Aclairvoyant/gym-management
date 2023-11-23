package com.sjdddd.config;

import com.sjdddd.interceptor.JwtTokenAdminInterceptor;
import com.sjdddd.json.JacksonObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .maxAge(3600);
//    }

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register");
    }

    /**
     * 通过knife4j生成接口文档
     *
     * @return
     */
//    @Bean
//    public Docket docket() {
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title("健身房会员管理系统项目接口文档")
//                .version("1.0")
//                .description("健身房会员管理项目接口文档")
//                .build();
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .groupName("管理端接口")
//                .apiInfo(apiInfo)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.sjdddd.controller"))
//                .paths(PathSelectors.any())
//                .build();
//        return docket;
//    }
//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("管理端接口")
//                .pathsToMatch("/**")
//                .packagesToScan("com.sjdddd.controller")
//                .build();
//    }
//
//    @Bean
//    public OpenAPI springShopOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("健身房会员管理系统项目接口文档")
//                        .description("健身房会员管理项目接口文档")
//                        .version("v1.0"));
//    }


    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        //registry.addResourceHandler("/swagger-ui.html/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/3.0.0/");
        //registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/3.0.0/");
        //registry.addResourceHandler("/swagger-resources/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/3.0.0/");
        //registry.addResourceHandler("/v3/api-docs/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/3.0.0/");
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

    }

    /**
     * 扩展消息转换器
     * 后端为前端提供数据，对其进行集中处理
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器");
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 设置一个对象转换器，将对象转换为json字符串
        converter.setObjectMapper(new JacksonObjectMapper());
        // 将自定义的消息转换器添加到集合中, 0表示添加到集合的第一个位置
        converters.add(0, converter);
    }
}
