package br.edu.insper.lojavirtual.service;

import br.edu.insper.lojavirtual.models.Cliente;
import br.edu.insper.lojavirtual.models.ItemPedido;
import br.edu.insper.lojavirtual.models.Pedido;
import br.edu.insper.lojavirtual.models.Produto;
import br.edu.insper.lojavirtual.repository.ItemPedidoRepository;
import br.edu.insper.lojavirtual.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido criar(Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido = pedidoRepository.save(pedido);
        cliente.adicionarPedido(pedido);
        return pedido;
    }

    public Pedido buscarPorNumero(int numero) {
        return pedidoRepository.findById(numero).orElse(null);
    }

    public Pedido adicionarItem(Pedido pedido, Produto produto, int quantidade) {
        ItemPedido item = pedido.adicionarProduto(produto, quantidade);
        itemPedidoRepository.save(item); // o item é quem tem a coluna id_pedido
        return pedido;
    }

    public Pedido alterarStatus(Pedido pedido, String status) {
        pedido.alterarStatus(status);
        return pedidoRepository.save(pedido);
    }
}
