package br.edu.insper.lojavirtual.repository;

import br.edu.insper.lojavirtual.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByCategoriaCodigo(int codigo);
}
