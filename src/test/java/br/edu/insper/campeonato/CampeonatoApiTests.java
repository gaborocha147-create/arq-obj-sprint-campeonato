package br.edu.insper.campeonato;

import br.edu.insper.campeonato.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:api;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop", "spring.jpa.show-sql=false"})
@AutoConfigureMockMvc
class CampeonatoApiTests {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @Autowired UsuarioRepository usuarios;

    private String token() throws Exception {
        String email = UUID.randomUUID() + "@teste.com";
        String dados = "{\"email\":\"" + email + "\",\"senha\":\"senha123\"}";
        mvc.perform(post("/usuarios").contentType("application/json").content(dados))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.senha").doesNotExist());
        assertNotEquals("senha123", usuarios.findByEmail(email).orElseThrow().getSenha());
        String resposta = mvc.perform(post("/login").contentType("application/json").content(dados))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        return mapper.readTree(resposta).get("token").asString();
    }

    private long criar(String url, String dados, String token) throws Exception {
        String resposta = mvc.perform(post(url).header("Authorization", "Bearer " + token)
                .contentType("application/json").content(dados))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return mapper.readTree(resposta).get("id").asLong();
    }

    @Test void autenticaERejeitaTokenInvalido() throws Exception {
        mvc.perform(get("/times")).andExpect(status().isUnauthorized());
        mvc.perform(get("/times").header("Authorization", "Bearer invalido"))
                .andExpect(status().isUnauthorized());
        mvc.perform(get("/times").header("Authorization", "Bearer " + token()))
                .andExpect(status().isOk());
        mvc.perform(post("/login").contentType("application/json")
                .content("{\"email\":\"naoexiste@teste.com\",\"senha\":\"errada\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test void validaCadastroEDuplicidade() throws Exception {
        mvc.perform(post("/usuarios").contentType("application/json")
                .content("{\"email\":\"invalido\",\"senha\":\"\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value(400));
        String dados = "{\"email\":\"duplicado@teste.com\",\"senha\":\"senha123\"}";
        mvc.perform(post("/usuarios").contentType("application/json").content(dados))
                .andExpect(status().isCreated());
        mvc.perform(post("/usuarios").contentType("application/json").content(dados))
                .andExpect(status().isConflict());
        mvc.perform(post("/login").contentType("application/json")
                .content("{\"email\":\"duplicado@teste.com\",\"senha\":\"errada\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test void executaCampeonatoComPlacarEClassificacaoECartoes() throws Exception {
        String token = token();
        String auth = "Bearer " + token;
        long campeonato = criar("/campeonatos", "{\"nome\":\"Copa\",\"ano\":2026}", token);
        mvc.perform(get("/campeonatos/" + campeonato).header("Authorization", auth))
                .andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Copa"));
        mvc.perform(get("/campeonatos").header("Authorization", auth)).andExpect(status().isOk());
        long mandante = criar("/times", "{\"nome\":\"A\",\"cidade\":\"SP\"}", token);
        long visitante = criar("/times", "{\"nome\":\"B\",\"cidade\":\"RJ\"}", token);
        long jogador = criar("/jogadores", "{\"nome\":\"Joao\",\"numero\":10,\"posicao\":\"Atacante\",\"idade\":22}", token);
        mvc.perform(post("/times/" + mandante + "/jogadores/" + jogador).header("Authorization", auth))
                .andExpect(status().isOk()).andExpect(jsonPath("$.jogadores.length()").value(1));
        mvc.perform(post("/times/" + mandante + "/jogadores/" + jogador).header("Authorization", auth))
                .andExpect(status().isOk()).andExpect(jsonPath("$.jogadores.length()").value(1));
        mvc.perform(post("/times/" + visitante + "/jogadores/" + jogador).header("Authorization", auth))
                .andExpect(status().isBadRequest());
        long partida = criar("/partidas", "{\"data\":\"2026-10-20\",\"idTimeMandante\":" + mandante
                + ",\"idTimeVisitante\":" + visitante + ",\"idCampeonato\":" + campeonato + "}", token);
        mvc.perform(get("/campeonatos/" + campeonato + "/classificacao").header("Authorization", auth))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].pontos").value(0));
        mvc.perform(patch("/partidas/" + partida + "/placar").header("Authorization", auth)
                .contentType("application/json").content("{\"golMandante\":2,\"golVisitante\":1}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.placar").value("2-1"));
        mvc.perform(get("/campeonatos/" + campeonato + "/classificacao").header("Authorization", auth))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].idTime").value(mandante))
                .andExpect(jsonPath("$[0].pontos").value(3)).andExpect(jsonPath("$[1].pontos").value(0));
        mvc.perform(get("/partidas/" + partida).header("Authorization", auth))
                .andExpect(status().isOk()).andExpect(jsonPath("$.placar").value("2-1"));
        mvc.perform(get("/partidas").header("Authorization", auth)).andExpect(status().isOk());
        criar("/partidas/" + partida + "/cartoes",
                "{\"tipo\":\"AMARELO\",\"idJogador\":" + jogador + ",\"minuto\":10}", token);
        mvc.perform(patch("/partidas/" + partida + "/placar").header("Authorization", auth)
                .contentType("application/json").content("{\"golMandante\":1,\"golVisitante\":1}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.cartoes.length()").value(1));
        mvc.perform(get("/campeonatos/" + campeonato + "/classificacao").header("Authorization", auth))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].pontos").value(1))
                .andExpect(jsonPath("$[1].pontos").value(1));
        mvc.perform(patch("/partidas/" + partida + "/placar").header("Authorization", auth)
                .contentType("application/json").content("{\"golMandante\":-1,\"golVisitante\":1}"))
                .andExpect(status().isBadRequest());
    }

    @Test void documentacaoPublicaEErrosHttp() throws Exception {
        mvc.perform(get("/v3/api-docs")).andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("API Campeonato"));
        mvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
        String auth = "Bearer " + token();
        mvc.perform(get("/times/9999999").header("Authorization", auth))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.status").value(404));
        mvc.perform(get("/times/abc").header("Authorization", auth)).andExpect(status().isBadRequest());
        mvc.perform(post("/times").header("Authorization", auth).contentType("application/json").content("{"))
                .andExpect(status().isBadRequest());
    }
}

