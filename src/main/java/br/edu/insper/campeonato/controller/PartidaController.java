package br.edu.insper.campeonato.controller;

import br.edu.insper.campeonato.model.Cartao;
import br.edu.insper.campeonato.model.Partida;
import br.edu.insper.campeonato.model.TipoCartao;
import br.edu.insper.campeonato.service.PartidaService;
import br.edu.insper.campeonato.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @PostMapping
    public ResponseEntity<PartidaView> cadastrar(@RequestBody NovaPartidaView novaPartida) {
        if (novaPartida == null || textoVazio(novaPartida.data())
                || novaPartida.idTimeMandante() == null
                || novaPartida.idTimeVisitante() == null
                || novaPartida.idCampeonato() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da partida inválidos");
        }
        if (novaPartida.idTimeMandante().equals(novaPartida.idTimeVisitante())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O time mandante e o visitante não podem ser o mesmo time");
        }

        Partida partida = partidaService.cadastrar(
                novaPartida.data(),
                novaPartida.idTimeMandante(),
                novaPartida.idTimeVisitante(),
                novaPartida.idCampeonato()
        );
        return ResponseEntity
                .created(URI.create("/partidas/" + partida.getId()))
                .body(PartidaView.fromModel(partida));
    }

    @PatchMapping("/{id}/placar")
    public PartidaView atualizarPlacar(@PathVariable Long id, @RequestBody PlacarView placar) {
        if (placar == null || placar.golMandante() < 0 || placar.golVisitante() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O placar não pode ter gols negativos");
        }
        Partida partida = partidaService.atualizarPlacar(id, placar.golMandante(), placar.golVisitante());
        return PartidaView.fromModel(partida);
    }

    @PostMapping("/{id}/cartoes")
    public ResponseEntity<CartaoView> registrarCartao(@PathVariable Long id,
                                                      @RequestBody NovoCartaoView novoCartao) {
        if (novoCartao == null || novoCartao.idJogador() == null || novoCartao.minuto() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do cartão inválidos");
        }

        TipoCartao tipo;
        try {
            tipo = TipoCartao.valueOf(novoCartao.tipo().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O tipo do cartão deve ser AMARELO ou VERMELHO");
        }

        Cartao cartao = partidaService.registrarCartao(id, tipo, novoCartao.idJogador(), novoCartao.minuto());
        return ResponseEntity
                .created(URI.create("/partidas/" + id + "/cartoes/" + cartao.getId()))
                .body(CartaoView.fromModel(cartao));
    }

    private boolean textoVazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
