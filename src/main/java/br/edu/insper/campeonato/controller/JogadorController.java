package br.edu.insper.campeonato.controller;

import br.edu.insper.campeonato.model.DadosCampeonato;
import br.edu.insper.campeonato.model.Jogador;
import br.edu.insper.campeonato.view.JogadorView;
import br.edu.insper.campeonato.view.NovoJogadorView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    private final DadosCampeonato dados;

    public JogadorController(DadosCampeonato dados) {
        this.dados = dados;
    }

    @PostMapping
    public ResponseEntity<JogadorView> cadastrar(
            @RequestBody NovoJogadorView novoJogador
    ) {
        if (novoJogador == null
                || textoVazio(novoJogador.nome())
                || textoVazio(novoJogador.posicao())
                || novoJogador.numero() <= 0
                || novoJogador.idade() < 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do jogador inválidos"
            );
        }

        Jogador jogador = dados.cadastrarJogador(
                novoJogador.nome(),
                novoJogador.numero(),
                novoJogador.posicao(),
                novoJogador.idade()
        );

        return ResponseEntity
                .created(URI.create("/jogadores/" + jogador.getId()))
                .body(JogadorView.fromModel(jogador));
    }

    @GetMapping
    public List<JogadorView> listar() {
        return dados.listarJogadores()
                .stream()
                .map(JogadorView::fromModel)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogadorView> buscar(@PathVariable Long id) {
        return dados.buscarJogador(id)
                .map(JogadorView::fromModel)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private boolean textoVazio(String texto) {
        return texto == null || texto.isBlank();
    }
}