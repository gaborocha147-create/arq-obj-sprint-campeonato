package br.edu.insper.campeonato.controller;

import br.edu.insper.campeonato.model.Campeonato;
import br.edu.insper.campeonato.model.Cartao;
import br.edu.insper.campeonato.model.DadosCampeonato;
import br.edu.insper.campeonato.model.Jogador;
import br.edu.insper.campeonato.model.Partida;
import br.edu.insper.campeonato.model.Time;
import br.edu.insper.campeonato.model.TipoCartao;
import br.edu.insper.campeonato.view.CartaoView;
import br.edu.insper.campeonato.view.NovaPartidaView;
import br.edu.insper.campeonato.view.NovoCartaoView;
import br.edu.insper.campeonato.view.PartidaView;
import br.edu.insper.campeonato.view.PlacarView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private final DadosCampeonato dados;

    public PartidaController(DadosCampeonato dados) {
        this.dados = dados;
    }

    @PostMapping
    public ResponseEntity<PartidaView> cadastrar(@RequestBody NovaPartidaView novaPartida) {
        if (novaPartida == null || textoVazio(novaPartida.data())
                || novaPartida.idTimeMandante() == null
                || novaPartida.idTimeVisitante() == null
                || novaPartida.idCampeonato() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da partida inválidos");
        }
        if (novaPartida.idTimeMandante().equals(novaPartida.idTimeVisitante())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O time mandante e o visitante não podem ser o mesmo time"
            );
        }

        Time mandante = buscarTime(novaPartida.idTimeMandante());
        Time visitante = buscarTime(novaPartida.idTimeVisitante());
        Campeonato campeonato = dados.buscarCampeonato(novaPartida.idCampeonato())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campeonato não encontrado"));

        Partida partida = dados.cadastrarPartida(novaPartida.data(), mandante, visitante, campeonato);
        return ResponseEntity
                .created(URI.create("/partidas/" + partida.getId()))
                .body(PartidaView.fromModel(partida));
    }

    @PatchMapping("/{id}/placar")
    public ResponseEntity<PartidaView> atualizarPlacar(
            @PathVariable Long id,
            @RequestBody PlacarView placar
    ) {
        Partida partida = buscarPartida(id);
        if (placar == null || placar.golMandante() < 0 || placar.golVisitante() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O placar não pode ter gols negativos");
        }

        partida.registrarGols(placar.golMandante(), placar.golVisitante());
        return ResponseEntity.ok(PartidaView.fromModel(partida));
    }

    @PostMapping("/{id}/cartoes")
    public ResponseEntity<CartaoView> registrarCartao(
            @PathVariable Long id,
            @RequestBody NovoCartaoView novoCartao
    ) {
        Partida partida = buscarPartida(id);
        if (novoCartao == null || novoCartao.idJogador() == null || novoCartao.minuto() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do cartão inválidos");
        }

        TipoCartao tipo;
        try {
            tipo = TipoCartao.valueOf(novoCartao.tipo().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O tipo do cartão deve ser AMARELO ou VERMELHO"
            );
        }

        Jogador jogador = dados.buscarJogador(novoCartao.idJogador())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador não encontrado"));
        if (!partida.jogadorParticipou(jogador)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O jogador não pertence aos times desta partida"
            );
        }

        Cartao cartao = dados.novoCartao(tipo, jogador, novoCartao.minuto());
        partida.registrarCartao(cartao);
        return ResponseEntity
                .created(URI.create("/partidas/" + id + "/cartoes/" + cartao.getId()))
                .body(CartaoView.fromModel(cartao));
    }

    private Time buscarTime(Long id) {
        return dados.buscarTime(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Time não encontrado"));
    }

    private Partida buscarPartida(Long id) {
        return dados.buscarPartida(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada"));
    }

    private boolean textoVazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
