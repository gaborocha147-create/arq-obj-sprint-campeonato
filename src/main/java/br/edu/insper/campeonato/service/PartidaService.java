package br.edu.insper.campeonato.service;

import br.edu.insper.campeonato.model.Campeonato;
import br.edu.insper.campeonato.model.Cartao;
import br.edu.insper.campeonato.model.Jogador;
import br.edu.insper.campeonato.model.Partida;
import br.edu.insper.campeonato.model.TipoCartao;
import br.edu.insper.campeonato.repository.CartaoRepository;
import br.edu.insper.campeonato.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private TimeService timeService;

    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private CampeonatoService campeonatoService;

    public Partida cadastrar(String data, Long idMandante, Long idVisitante, Long idCampeonato) {
        Partida partida = new Partida();
        partida.setData(data);
        partida.setTimeMandante(timeService.buscarPorId(idMandante));
        partida.setTimeVisitante(timeService.buscarPorId(idVisitante));

        Campeonato campeonato = campeonatoService.buscarPorId(idCampeonato);
        partida.setCampeonato(campeonato);
        partida = partidaRepository.save(partida);

        campeonato.adicionarPartida(partida);
        campeonatoService.salvar(campeonato);
        return partida;
    }

    public Partida buscarPorId(Long id) {
        return partidaRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada"));
    }

    public Partida atualizarPlacar(Long id, int golMandante, int golVisitante) {
        Partida partida = buscarPorId(id);
        partida.registrarGols(golMandante, golVisitante);
        return partidaRepository.save(partida);
    }

    public Cartao registrarCartao(Long idPartida, TipoCartao tipo, Long idJogador, int minuto) {
        Partida partida = buscarPorId(idPartida);
        Jogador jogador = jogadorService.buscarPorId(idJogador);

        if (!partida.jogadorParticipou(jogador)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O jogador não pertence aos times desta partida");
        }

        Cartao cartao = new Cartao();
        cartao.setTipo(tipo);
        cartao.setJogador(jogador);
        cartao.setMinuto(minuto);
        partida.registrarCartao(cartao);

        return cartaoRepository.save(cartao);
    }
}
