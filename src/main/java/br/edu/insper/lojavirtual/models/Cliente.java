package br.edu.insper.lojavirtual.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente {

    @Id
    private String cpf;

    @Column(nullable = false)
    private String nome;

    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void adicionarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    public List<Pedido> listarPedidos() {
        return this.pedidos;
    }

    public double calcularTotalGasto() {
        double total = 0;
        for (Pedido pedido : this.pedidos) {
            total += pedido.calcularTotal();
        }
        return total;
    }

    public Pedido buscarPedidoMaisCaro() {
        if (this.pedidos.isEmpty()) {
            return null;
        }

        Pedido maisCaro = this.pedidos.get(0);
        for (Pedido pedido : this.pedidos) {
            if (pedido.calcularTotal() > maisCaro.calcularTotal()) {
                maisCaro = pedido;
            }
        }
        return maisCaro;
    }
}
