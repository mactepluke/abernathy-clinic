package com.mediscreen.massessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceAssessmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAssessmentApplication.class, args);
    }

}
