package br.edu.insper.campeonato.controller;

import br.edu.insper.campeonato.model.Campeonato;
import br.edu.insper.campeonato.model.DadosCampeonato;
import br.edu.insper.campeonato.view.CampeonatoView;
import br.edu.insper.campeonato.view.ClassificacaoView;
import br.edu.insper.campeonato.view.NovoCampeonatoView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/campeonatos")
public class CampeonatoController {

    private final DadosCampeonato dados;

    public CampeonatoController(DadosCampeonato dados) {
        this.dados = dados;
    }

    @PostMapping
    public ResponseEntity<CampeonatoView> cadastrar(@RequestBody NovoCampeonatoView novoCampeonato) {
        if (novoCampeonato == null || textoVazio(novoCampeonato.nome()) || novoCampeonato.ano() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do campeonato inválidos");
        }

        Campeonato campeonato = dados.cadastrarCampeonato(novoCampeonato.nome(), novoCampeonato.ano());
        return ResponseEntity
                .created(URI.create("/campeonatos/" + campeonato.getId()))
                .body(CampeonatoView.fromModel(campeonato));
    }

    @GetMapping("/{id}/classificacao")
    public List<ClassificacaoView> classificacao(@PathVariable Long id) {
        Campeonato campeonato = dados.buscarCampeonato(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campeonato não encontrado"));

        List<ClassificacaoView> resultado = new ArrayList<>();
        int posicao = 1;
        for (var entrada : campeonato.exibirClassificacao().entrySet()) {
            resultado.add(ClassificacaoView.fromModel(posicao++, entrada.getKey(), entrada.getValue()));
        }
        return resultado;
    }

    private boolean textoVazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
