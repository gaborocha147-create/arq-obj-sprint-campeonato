package br.edu.insper.lojavirtual.controller;

import br.edu.insper.lojavirtual.utils.BancoDeDados;
import br.edu.insper.lojavirtual.models.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private BancoDeDados bancoDeDados;

    @PostMapping
    public ResponseEntity<Categoria> cadastrar(@RequestBody Categoria categoria) {
        categoria.setCodigo(bancoDeDados.gerarCodigoCategoria());
        bancoDeDados.getCategorias().add(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }
}
