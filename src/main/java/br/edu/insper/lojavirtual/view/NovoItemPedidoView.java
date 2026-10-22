package br.edu.insper.lojavirtual.view;

import jakarta.validation.constraints.Positive;

public class NovoItemPedidoView {
    @Positive(message = "Informe o código de um produto")
    private int produtoCodigo;

    @Positive(message = "Quantidade deve ser maior que zero")
    private int quantidade;

    public int getProdutoCodigo() {
        return produtoCodigo;
    }

    public void setProdutoCodigo(int produtoCodigo) {
        this.produtoCodigo = produtoCodigo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
