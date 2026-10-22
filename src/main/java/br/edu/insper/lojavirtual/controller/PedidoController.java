package br.edu.insper.lojavirtual.controller;

import br.edu.insper.lojavirtual.models.Cliente;
import br.edu.insper.lojavirtual.models.Pedido;
import br.edu.insper.lojavirtual.models.Produto;
import br.edu.insper.lojavirtual.service.ClienteService;
import br.edu.insper.lojavirtual.service.PedidoService;
import br.edu.insper.lojavirtual.service.ProdutoService;
import br.edu.insper.lojavirtual.view.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody NovoPedidoView request) {
        Cliente cliente = clienteService.buscarPorCpf(request.getCpfCliente());

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cliente com CPF " + request.getCpfCliente() + " não encontrado.");
        }

        Pedido pedido = pedidoService.criar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PedidoView(pedido));
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<?> adicionarItem(@PathVariable int id, @Valid @RequestBody NovoItemPedidoView request) {
        Pedido pedido = pedidoService.buscarPorNumero(id);

        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Pedido " + id + " não encontrado.");
        }

        if (!"ABERTO".equals(pedido.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não é possível adicionar itens a um pedido com status " + pedido.getStatus() + ".");
        }

        Produto produto = produtoService.buscarPorCodigo(request.getProdutoCodigo());

        if (produto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Produto " + request.getProdutoCodigo() + " não encontrado.");
        }

        // o preço atual do produto é copiado para o ItemPedido no momento da adição
        pedidoService.adicionarItem(pedido, produto, request.getQuantidade());

        return ResponseEntity.status(HttpStatus.CREATED).body(new PedidoView(pedido));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(@PathVariable int id, @Valid @RequestBody StatusView request) {
        Pedido pedido = pedidoService.buscarPorNumero(id);

        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Pedido " + id + " não encontrado.");
        }

        pedido = pedidoService.alterarStatus(pedido, request.getStatus());
        return ResponseEntity.ok(new PedidoView(pedido));
    }
}
