package com.casino.msproovedores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsProovedoresApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProovedoresApplication.class, args);
    }

}
