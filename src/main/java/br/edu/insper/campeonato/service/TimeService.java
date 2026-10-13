package br.edu.insper.campeonato.service;

import br.edu.insper.campeonato.model.Jogador;
import br.edu.insper.campeonato.model.Time;
import br.edu.insper.campeonato.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TimeService {

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private JogadorService jogadorService;

    public Time salvar(Time time) {
        return timeRepository.save(time);
    }

    public List<Time> listar() {
        return timeRepository.findAll();
    }

    public Time buscarPorId(Long id) {
        return timeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Time não encontrado"));
    }

    public Time adicionarJogador(Long idTime, Long idJogador) {
        Time time = buscarPorId(idTime);
        Jogador jogador = jogadorService.buscarPorId(idJogador);

        if (jogador.getTime() != null && !jogador.getTime().getId().equals(time.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O jogador já pertence a outro time");
        }

        time.adicionarJogador(jogador);
        jogadorService.salvar(jogador);
        return time;
    }
}
