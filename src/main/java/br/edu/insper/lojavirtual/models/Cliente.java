package br.edu.insper.lojavirtual.models;

import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;
    private ArrayList<Pedido> pedidos;

    public Cliente(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.pedidos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void adicionarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    public ArrayList<Pedido> listarPedidos() {
        return this.pedidos;
    }

    public double calcularTotalGasto() {
        double total = 0;

        for (Pedido pedido: this.pedidos) {
            total += pedido.calcularTotal();
        }
        return total;
    }

    public Pedido buscarPedidoMaisCaro() {
        if (this.pedidos.isEmpty()) {
            return null;
        }

        Pedido maisCaro = this.pedidos.get(0);
        for (Pedido pedido: this.pedidos) {
            if (pedido.calcularTotal() > maisCaro.calcularTotal()) {
                maisCaro = pedido;
            }
        }

        return maisCaro;
    }

}