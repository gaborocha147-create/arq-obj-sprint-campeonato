package br.edu.insper.lojavirtual;

import java.util.ArrayList;

public class Pedido {
    private int numero;
    private Cliente cliente;
    private ArrayList<ItemPedido> itens;
    private String status;

    public Pedido(int numero, Cliente cliente) {
        this.numero = numero;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.status = "ABERTO";
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItemPedido> itens) {
        this.itens = itens;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        ItemPedido novoItem = new ItemPedido(produto, quantidade, produto.getPreco());
        this.itens.add(novoItem);
    }

    public void removerProduto(Produto produto) {
        ItemPedido itemParaRemover = null;
        for (ItemPedido item: this.itens) {
            if (item.getProduto().equals(produto)) {
                itemParaRemover = item;
                break;
            }
        }
        if (itemParaRemover != null) {
            this.itens.remove(itemParaRemover);
        }
    }

    public double calcularTotal() {
        double total = 0;

        for (ItemPedido item: this.itens) {
            total += item.calcularSubtotal();
        }

        return total;
    }

    public int calcularQuantidadeItens() {
        int quantidade = 0;

        for (ItemPedido item: this.itens) {
            quantidade += item.getQuantidade();
        }

        return quantidade;
    }

    public ItemPedido buscarItemMaisCaro() {
        if (this.itens.isEmpty()) {
            return null;
        }

        ItemPedido maisCaro = this.itens.get(0);
        for (ItemPedido item: this.itens) {
            if (item.getPrecoUnitario() > maisCaro.getPrecoUnitario()) {
                maisCaro = item;
            }
        }

        return maisCaro;
    }

    public void alterarStatus(String status) {
        this.status = status;
    }

}