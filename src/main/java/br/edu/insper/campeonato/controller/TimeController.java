package br.edu.insper.campeonato.controller;

import br.edu.insper.campeonato.model.DadosCampeonato;
import br.edu.insper.campeonato.model.Jogador;
import br.edu.insper.campeonato.model.Time;
import br.edu.insper.campeonato.view.NovoTimeView;
import br.edu.insper.campeonato.view.TimeResumoView;
import br.edu.insper.campeonato.view.TimeView;
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
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final DadosCampeonato dados;

    public TimeController(DadosCampeonato dados) {
        this.dados = dados;
    }

    @PostMapping
    public ResponseEntity<TimeView> cadastrar(@RequestBody NovoTimeView novoTime) {
        if (novoTime == null || textoVazio(novoTime.nome()) || textoVazio(novoTime.cidade())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do time inválidos");
        }

        Time time = dados.cadastrarTime(novoTime.nome(), novoTime.cidade());
        return ResponseEntity
                .created(URI.create("/times/" + time.getId()))
                .body(TimeView.fromModel(time));
    }

    @GetMapping
    public List<TimeResumoView> listar() {
        return dados.listarTimes().stream().map(TimeResumoView::fromModel).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeView> buscar(@PathVariable Long id) {
        return dados.buscarTime(id)
                .map(TimeView::fromModel)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{idTime}/jogadores/{idJogador}")
    public ResponseEntity<TimeView> adicionarJogador(
            @PathVariable Long idTime,
            @PathVariable Long idJogador
    ) {
        Time time = dados.buscarTime(idTime)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Time não encontrado"));
        Jogador jogador = dados.buscarJogador(idJogador)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador não encontrado"));

        if (jogador.getTime() != null && jogador.getTime() != time) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O jogador já pertence a outro time");
        }

        time.adicionarJogador(jogador);
        return ResponseEntity.ok(TimeView.fromModel(time));
    }

    private boolean textoVazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
