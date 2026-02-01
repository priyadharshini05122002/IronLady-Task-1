package com.ironlady.internal_system;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IronLadyInternalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(IronLadyInternalSystemApplication.class, args);

        // Auto-open browser (Windows only – for demo/learning)
        try {
            Runtime.getRuntime().exec(
                "rundll32 url.dll,FileProtocolHandler http://localhost:8080/learners"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
