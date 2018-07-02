package com.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Base (main) class for Spring Boot application.
 *
 * Created by Mikhail Kholodkov
 *         on July 2, 2018
 */
@SpringBootApplication
public class TestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }
}
