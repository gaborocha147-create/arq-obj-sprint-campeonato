package br.edu.insper.lojavirtual.view;

import br.edu.insper.lojavirtual.models.ItemPedido;
import br.edu.insper.lojavirtual.models.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoView {
    private int numero;
    private String cpfCliente;
    private String status;
    private List<ItemPedidoView> itens;
    private double valorTotal;

    public PedidoView(Pedido pedido) {
        this.numero = pedido.getNumero();
        this.cpfCliente = pedido.getCliente().getCpf();
        this.status = pedido.getStatus();

        this.itens = new ArrayList<>();
        for (ItemPedido item : pedido.getItens()) {
            this.itens.add(new ItemPedidoView(item));
        }

        this.valorTotal = pedido.calcularTotal();
    }

    public int getNumero() {
        return numero;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getStatus() {
        return status;
    }

    public List<ItemPedidoView> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
