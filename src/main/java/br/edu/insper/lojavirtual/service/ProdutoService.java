package br.edu.insper.lojavirtual.service;

import br.edu.insper.lojavirtual.models.Produto;
import br.edu.insper.lojavirtual.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarPorCategoria(int codigoCategoria) {
        return produtoRepository.findByCategoriaCodigo(codigoCategoria);
    }

    public Produto buscarPorCodigo(int codigo) {
        return produtoRepository.findById(codigo).orElse(null);
    }
}
