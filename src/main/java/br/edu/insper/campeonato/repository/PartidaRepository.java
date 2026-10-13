package br.edu.insper.campeonato.repository;

import br.edu.insper.campeonato.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PartidaRepository extends JpaRepository<Partida, Long>{
}
