package br.edu.insper.campeonato.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DadosCampeonato {

    private final List<Jogador> jogadores = new ArrayList<>();
    private final List<Time> times = new ArrayList<>();
    private final List<Partida> partidas = new ArrayList<>();
    private final List<Campeonato> campeonatos = new ArrayList<>();

    private final AtomicLong proximoJogadorId = new AtomicLong(1);
    private final AtomicLong proximoTimeId = new AtomicLong(1);
    private final AtomicLong proximaPartidaId = new AtomicLong(1);
    private final AtomicLong proximoCartaoId = new AtomicLong(1);
    private final AtomicLong proximoCampeonatoId = new AtomicLong(1);

    public synchronized Jogador cadastrarJogador(String nome, int numero, String posicao, int idade) {
        Jogador jogador = new Jogador(proximoJogadorId.getAndIncrement(), nome, numero, posicao, idade);
        jogadores.add(jogador);
        return jogador;
    }

    public synchronized Time cadastrarTime(String nome, String cidade) {
        Time time = new Time(proximoTimeId.getAndIncrement(), nome, cidade);
        times.add(time);
        return time;
    }

    public synchronized Campeonato cadastrarCampeonato(String nome, int ano) {
        Campeonato campeonato = new Campeonato(proximoCampeonatoId.getAndIncrement(), nome, ano);
        campeonatos.add(campeonato);
        return campeonato;
    }

    public synchronized Partida cadastrarPartida(
            String data,
            Time mandante,
            Time visitante,
            Campeonato campeonato
    ) {
        Partida partida = new Partida(
                proximaPartidaId.getAndIncrement(), data, mandante, visitante, campeonato
        );
        partidas.add(partida);
        campeonato.adicionarPartida(partida);
        return partida;
    }

    public Cartao novoCartao(TipoCartao tipo, Jogador jogador, int minuto) {
        return new Cartao(proximoCartaoId.getAndIncrement(), tipo, jogador, minuto);
    }

    public synchronized List<Jogador> listarJogadores() {
        return List.copyOf(jogadores);
    }

    public synchronized List<Time> listarTimes() {
        return List.copyOf(times);
    }

    public synchronized Optional<Jogador> buscarJogador(Long id) {
        return jogadores.stream().filter(jogador -> jogador.getId().equals(id)).findFirst();
    }

    public synchronized Optional<Time> buscarTime(Long id) {
        return times.stream().filter(time -> time.getId().equals(id)).findFirst();
    }

    public synchronized Optional<Partida> buscarPartida(Long id) {
        return partidas.stream().filter(partida -> partida.getId().equals(id)).findFirst();
    }

    public synchronized Optional<Campeonato> buscarCampeonato(Long id) {
        return campeonatos.stream().filter(campeonato -> campeonato.getId().equals(id)).findFirst();
    }
}
