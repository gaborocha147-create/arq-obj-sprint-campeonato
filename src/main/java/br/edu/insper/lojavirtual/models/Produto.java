package br.edu.insper.lojavirtual.models;

import jakarta.persistence.*;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(nullable = false)
    private String nome;

    private double preco;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double aplicarDesconto(double percentual) {
        return (1 - percentual) * getPreco();
    }

    public double aumentarPreco(double percentual) {
        return (1 + percentual) * getPreco();
    }
}
