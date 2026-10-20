package br.edu.insper.campeonato;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:contexto", "spring.jpa.hibernate.ddl-auto=create-drop"})
class CampeonatoApplicationTests {

	@Test
	void contextLoads() {
	}

}
