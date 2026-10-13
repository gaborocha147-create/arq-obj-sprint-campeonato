package br.edu.insper.campeonato.service;

import br.edu.insper.campeonato.model.Campeonato;
import br.edu.insper.campeonato.repository.CampeonatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CampeonatoService {
    @Autowired
    private CampeonatoRepository campeonatoRepository;

    public Campeonato salvar(Campeonato campeonato) {
        return campeonatoRepository.save(campeonato);
    }

    public Campeonato buscarPorId(Long id) {
        return campeonatoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campeonato não encontrado"));
    }
}
