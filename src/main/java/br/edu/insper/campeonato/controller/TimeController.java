package br.edu.insper.campeonato.controller;

import br.edu.insper.campeonato.model.Time;
import br.edu.insper.campeonato.service.TimeService;
import br.edu.insper.campeonato.view.NovoTimeView;
import br.edu.insper.campeonato.view.TimeResumoView;
import br.edu.insper.campeonato.view.TimeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    @Autowired
    private TimeService timeService;

    @PostMapping
    public ResponseEntity<TimeView> cadastrar(@RequestBody NovoTimeView novoTime) {
        if (novoTime == null || textoVazio(novoTime.nome()) || textoVazio(novoTime.cidade())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do time inválidos");
        }

        Time time = new Time();
        time.setNome(novoTime.nome());
        time.setCidade(novoTime.cidade());
        time = timeService.salvar(time);

        return ResponseEntity
                .created(URI.create("/times/" + time.getId()))
                .body(TimeView.fromModel(time));
    }

    @GetMapping
    public List<TimeResumoView> listar() {
        return timeService.listar().stream().map(TimeResumoView::fromModel).toList();
    }

    @GetMapping("/{id}")
    public TimeView buscar(@PathVariable Long id) {
        return TimeView.fromModel(timeService.buscarPorId(id));
    }

    @PostMapping("/{idTime}/jogadores/{idJogador}")
    public TimeView adicionarJogador(@PathVariable Long idTime, @PathVariable Long idJogador) {
        return TimeView.fromModel(timeService.adicionarJogador(idTime, idJogador));
    }

    private boolean textoVazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
