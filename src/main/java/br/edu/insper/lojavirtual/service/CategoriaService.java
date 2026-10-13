package br.edu.insper.lojavirtual.service;

import br.edu.insper.lojavirtual.models.Categoria;
import br.edu.insper.lojavirtual.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria buscarPorCodigo(int codigo) {
        return categoriaRepository.findById(codigo).orElse(null);
    }
}
