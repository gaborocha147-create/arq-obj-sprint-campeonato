package br.edu.insper.lojavirtual.controller;

import br.edu.insper.lojavirtual.models.Cliente;
import br.edu.insper.lojavirtual.models.Pedido;
import br.edu.insper.lojavirtual.service.ClienteService;
import br.edu.insper.lojavirtual.view.PedidoResumoView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Cliente cliente) {

        if (clienteService.buscarPorCpf(cliente.getCpf()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um cliente cadastrado com esse CPF.");
        }

        Cliente salvo = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }

    @GetMapping("/{cpf}/pedidos")
    public ResponseEntity<?> listarPedidos(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente com CPF " + cpf + " não encontrado.");
        }

        List<PedidoResumoView> resposta = new ArrayList<>();
        for (Pedido pedido : cliente.getPedidos()) {
            resposta.add(new PedidoResumoView(pedido));
        }
        return ResponseEntity.ok(resposta);
    }
}
