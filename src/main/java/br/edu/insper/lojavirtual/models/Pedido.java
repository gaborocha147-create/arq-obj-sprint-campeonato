package br.edu.insper.lojavirtual.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero;

    @ManyToOne
    @JoinColumn(name = "cpf_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    @Column(nullable = false)
    private String status = "ABERTO";

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

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ItemPedido adicionarProduto(Produto produto, int quantidade) {
        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco()); // preço "congelado" no momento da compra
        item.setPedido(this);
        this.itens.add(item);
        return item;
    }

    public void removerProduto(Produto produto) {
        ItemPedido itemParaRemover = null;
        for (ItemPedido item : this.itens) {
            if (item.getProduto().getCodigo() == produto.getCodigo()) {
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
        for (ItemPedido item : this.itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public int calcularQuantidadeItens() {
        int quantidade = 0;
        for (ItemPedido item : this.itens) {
            quantidade += item.getQuantidade();
        }
        return quantidade;
    }

    public ItemPedido buscarItemMaisCaro() {
        if (this.itens.isEmpty()) {
            return null;
        }

        ItemPedido maisCaro = this.itens.get(0);
        for (ItemPedido item : this.itens) {
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
