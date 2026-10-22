package br.edu.insper.lojavirtual.controller;

import br.edu.insper.lojavirtual.models.Categoria;
import br.edu.insper.lojavirtual.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Categoria> cadastrar(@Valid @RequestBody Categoria categoria) {
        Categoria salva = categoriaService.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }
}
