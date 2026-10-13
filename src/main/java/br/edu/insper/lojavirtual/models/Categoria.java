package br.edu.insper.lojavirtual.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(nullable = false)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos = new ArrayList<>();

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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }

    public List<Produto> listarProdutos() {
        return this.produtos;
    }

    public double calcularPrecoMedio() {
        if (this.produtos.isEmpty()) {
            return 0;
        }

        double soma = 0;
        for (Produto produto : this.produtos) {
            soma += produto.getPreco();
        }
        return soma / this.produtos.size();
    }

    public Produto buscarProdutoMaisCaro() {
        if (this.produtos.isEmpty()) {
            return null;
        }

        Produto maisCaro = this.produtos.get(0);
        for (Produto produto : this.produtos) {
            if (produto.getPreco() > maisCaro.getPreco()) {
                maisCaro = produto;
            }
        }
        return maisCaro;
    }
}
