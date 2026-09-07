package br.edu.insper.lojavirtual.dto;

import br.edu.insper.lojavirtual.models.Pedido;

public class PedidoResumoResponse {
    private int numero;
    private String status;
    private int quantidadeItens;
    private double valorTotal;

    public PedidoResumoResponse(Pedido pedido) {
        this.numero = pedido.getNumero();
        this.status = pedido.getStatus();
        this.quantidadeItens = pedido.calcularQuantidadeItens();
        this.valorTotal = pedido.calcularTotal();
    }

    public int getNumero() {
        return numero;
    }

    public String getStatus() {
        return status;
    }

    public int getQuantidadeItens() {
        return quantidadeItens;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
