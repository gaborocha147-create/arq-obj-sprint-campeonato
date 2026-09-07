package br.edu.insper.lojavirtual.controller;


import br.edu.insper.lojavirtual.dto.BancoDeDados;
import br.edu.insper.lojavirtual.dto.ProdutoRequest;
import br.edu.insper.lojavirtual.models.Categoria;
import br.edu.insper.lojavirtual.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private BancoDeDados bancoDeDados;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody ProdutoRequest request) {
        Categoria categoria = bancoDeDados.buscarCategoriaPorCodigo(request.getCategoriaId());

        if (categoria == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Categoria com código " + request.getCategoriaId() + " não encontrada.");
        }

        Produto produto = new Produto(
                bancoDeDados.gerarCodigoProduto(),
                request.getNome(),
                request.getPreco(),
                categoria
        );

        bancoDeDados.getProdutos().add(produto);
        categoria.adicionarProduto(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar(
            @RequestParam(required = false) Integer categoriaId) {

        if (categoriaId == null) {
            return ResponseEntity.ok(bancoDeDados.getProdutos());
        }

        List<Produto> filtrados = new ArrayList<>();
        for (Produto produto : bancoDeDados.getProdutos()) {
            if (produto.getCategoria().getCodigo() == categoriaId) {
                filtrados.add(produto);
            }
        }
        return ResponseEntity.ok(filtrados);
    }
}
