package br.edu.insper.lojavirtual.controller;

import br.edu.insper.lojavirtual.utils.BancoDeDados;
import br.edu.insper.lojavirtual.view.PedidoResumoView;
import br.edu.insper.lojavirtual.utils.ValidarCpf;
import br.edu.insper.lojavirtual.models.Cliente;
import br.edu.insper.lojavirtual.models.Pedido;
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
    private BancoDeDados bancoDeDados;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Cliente cliente) {

        if (!ValidarCpf.formatoValido(cliente.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("CPF inválido. Use o formato 123.456.789-00 ou 12345678900.");
        }

        if (bancoDeDados.buscarClientePorCpf(cliente.getCpf()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um cliente cadastrado com esse CPF.");
        }

        bancoDeDados.getClientes().add(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(bancoDeDados.getClientes());
    }

    @GetMapping("/{cpf}/pedidos")
    public ResponseEntity<?> listarPedidos(@PathVariable String cpf) {
        Cliente cliente = bancoDeDados.buscarClientePorCpf(cpf);

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
