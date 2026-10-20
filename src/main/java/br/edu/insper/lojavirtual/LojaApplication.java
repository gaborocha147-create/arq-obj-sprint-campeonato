package br.edu.insper.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LojaApplication {

    public static void main(String[] args) {
        SpringApplication app =
                new SpringApplication(LojaApplication.class);
        app.setAdditionalProfiles("lojavirtual");
        app.run(args);
    }
}
