package br.edu.insper.lojavirtual.controller;

import br.edu.insper.lojavirtual.utils.BancoDeDados;
import br.edu.insper.lojavirtual.view.*;
import br.edu.insper.lojavirtual.models.Cliente;
import br.edu.insper.lojavirtual.models.Pedido;
import br.edu.insper.lojavirtual.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private static final List<String> STATUS_VALIDOS = Arrays.asList("ABERTO", "PAGO", "CANCELADO");

    @Autowired
    private BancoDeDados bancoDeDados;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody NovoPedidoView request) {
        Cliente cliente = bancoDeDados.buscarClientePorCpf(request.getCpfCliente());

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cliente com CPF " + request.getCpfCliente() + " não encontrado.");
        }

        Pedido pedido = new Pedido(bancoDeDados.gerarNumeroPedido(), cliente);
        bancoDeDados.getPedidos().add(pedido);
        cliente.adicionarPedido(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PedidoView(pedido));
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<?> adicionarItem(@PathVariable int id, @RequestBody NovoItemPedidoView request) {
        Pedido pedido = bancoDeDados.buscarPedidoPorNumero(id);

        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Pedido " + id + " não encontrado.");
        }

        if (!"ABERTO".equals(pedido.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não é possível adicionar itens a um pedido com status " + pedido.getStatus() + ".");
        }

        Produto produto = bancoDeDados.buscarProdutoPorCodigo(request.getProdutoCodigo());

        if (produto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Produto " + request.getProdutoCodigo() + " não encontrado.");
        }

        if (request.getQuantidade() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Quantidade deve ser maior que zero.");
        }

        // pedido.adicionarProduto já captura produto.getPreco() no momento da chamada
        // e guarda esse valor dentro do ItemPedido -- mudanças futuras no preço do
        // produto não afetam este item.
        pedido.adicionarProduto(produto, request.getQuantidade());

        return ResponseEntity.status(HttpStatus.CREATED).body(new PedidoView(pedido));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(@PathVariable int id, @RequestBody StatusView request) {
        Pedido pedido = bancoDeDados.buscarPedidoPorNumero(id);

        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Pedido " + id + " não encontrado.");
        }

        if (!STATUS_VALIDOS.contains(request.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Status inválido. Use um de: " + STATUS_VALIDOS);
        }

        pedido.alterarStatus(request.getStatus());

        return ResponseEntity.ok(new PedidoView(pedido));
    }
}
