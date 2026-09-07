package br.edu.insper.lojavirtual.dto;

import br.edu.insper.lojavirtual.models.ItemPedido;
import br.edu.insper.lojavirtual.models.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoResponse {
    private int numero;
    private String cpfCliente;
    private String status;
    private List<ItemPedidoResponse> itens;
    private double valorTotal;

    public PedidoResponse(Pedido pedido) {
        this.numero = pedido.getNumero();
        this.cpfCliente = pedido.getCliente().getCpf();
        this.status = pedido.getStatus();

        this.itens = new ArrayList<>();
        for (ItemPedido item : pedido.getItens()) {
            this.itens.add(new ItemPedidoResponse(item));
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

    public List<ItemPedidoResponse> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
