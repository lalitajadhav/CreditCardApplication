package com.example.CreditCardApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan({"com.example.CreditCardApplication",
        "com.example.CreditCardApplication.service",
        "com.example.CreditCardApplication.configuration"})
@EnableJpaRepositories("com.example.CreditCardApplication.persistence")
@EnableTransactionManagement
public class CreditCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditCardApplication.class, args);
    }

}
