package br.edu.insper.lojavirtual.view;

import br.edu.insper.lojavirtual.models.ItemPedido;

public class ItemPedidoView {
    private String produtoNome;
    private int quantidade;
    private double precoUnitario;
    private double subtotal;

    public ItemPedidoView(ItemPedido item) {
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
