package org.example.spring_security_jwt;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityJwtApplication {
    public static void main(String[] args) {

        SLF4JBridgeHandler.install();


        SpringApplication.run(SpringSecurityJwtApplication.class, args);
    }
}


