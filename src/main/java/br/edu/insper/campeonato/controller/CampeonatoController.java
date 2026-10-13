package br.edu.insper.campeonato.controller;

import br.edu.insper.campeonato.model.Campeonato;
import br.edu.insper.campeonato.service.CampeonatoService;
import br.edu.insper.campeonato.view.CampeonatoView;
import br.edu.insper.campeonato.view.ClassificacaoView;
import br.edu.insper.campeonato.view.NovoCampeonatoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/campeonatos")
public class CampeonatoController {

    @Autowired
    private CampeonatoService campeonatoService;

    @PostMapping
    public ResponseEntity<CampeonatoView> cadastrar(@RequestBody NovoCampeonatoView novoCampeonato) {
        if (novoCampeonato == null || textoVazio(novoCampeonato.nome()) || novoCampeonato.ano() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do campeonato inválidos");
        }

        Campeonato campeonato = new Campeonato();
        campeonato.setNome(novoCampeonato.nome());
        campeonato.setAno(novoCampeonato.ano());
        campeonato = campeonatoService.salvar(campeonato);

        return ResponseEntity
                .created(URI.create("/campeonatos/" + campeonato.getId()))
                .body(CampeonatoView.fromModel(campeonato));
    }

    @GetMapping("/{id}/classificacao")
    public List<ClassificacaoView> classificacao(@PathVariable Long id) {
        Campeonato campeonato = campeonatoService.buscarPorId(id);

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