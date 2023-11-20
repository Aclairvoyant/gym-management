package com.sjdddd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/10/24 22:00
 **/

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
//@EnableOpenApi
public class GymManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymManagementApplication.class, args);
        log.info("server started");
    }
}
