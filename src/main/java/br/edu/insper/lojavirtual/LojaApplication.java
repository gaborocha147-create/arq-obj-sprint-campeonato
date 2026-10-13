package br.edu.insper.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LojaApplication {

    public static void main(String[] args) {
        // banco separado do campeonato (data/loja)
        SpringApplication.run(LojaApplication.class,
                "--spring.datasource.url=jdbc:h2:file:./data/loja",
                "--spring.application.name=lojavirtual");
    }

}
