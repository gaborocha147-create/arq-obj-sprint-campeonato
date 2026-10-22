package br.edu.insper.lojavirtual.controller;

import br.edu.insper.lojavirtual.models.Categoria;
import br.edu.insper.lojavirtual.models.Produto;
import br.edu.insper.lojavirtual.service.CategoriaService;
import br.edu.insper.lojavirtual.service.ProdutoService;
import br.edu.insper.lojavirtual.view.NovoProdutoView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody NovoProdutoView request) {
        Categoria categoria = categoriaService.buscarPorCodigo(request.getCategoriaId());

        if (categoria == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Categoria com código " + request.getCategoriaId() + " não encontrada.");
        }

        Produto produto = new Produto();
        produto.setNome(request.getNome());
        produto.setPreco(request.getPreco());
        produto.setCategoria(categoria);
        produto = produtoService.salvar(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar(@RequestParam(required = false) Integer categoriaId) {
        if (categoriaId == null) {
            return ResponseEntity.ok(produtoService.listar());
        }
        return ResponseEntity.ok(produtoService.listarPorCategoria(categoriaId));
    }
}
