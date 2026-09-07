package br.edu.insper.lojavirtual.dto;

import br.edu.insper.lojavirtual.models.ItemPedido;

public class ItemPedidoResponse {
    private String produtoNome;
    private int quantidade;
    private double precoUnitario;
    private double subtotal;

    public ItemPedidoResponse(ItemPedido item) {
        this.produtoNome = item.getProduto().getNome();
        this.quantidade = item.getQuantidade();
        this.precoUnitario = item.getPrecoUnitario();
        this.subtotal = item.calcularSubtotal();
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }
}
