package br.edu.insper.lojavirtual;

import java.util.ArrayList;

public class Categoria {
    private int codigo;
    private String nome;
    private ArrayList<Produto> produtos;

    public Categoria(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        this.produtos = new ArrayList<>();
    }

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

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }

    public ArrayList<Produto> listarProdutos() {
        return this.produtos;
    }

    public double calcularPrecoMedio() {
        if (this.produtos.isEmpty()) {
            return 0;
        }

        double soma = 0;
        for (Produto produto: this.produtos) {
            soma += produto.getPreco();
        }

        return soma / this.produtos.size();
    }

    public Produto buscarProdutoMaisCaro() {
        if (this.produtos.isEmpty()) {
            return null;
        }

        Produto maisCaro = this.produtos.get(0);
        for (Produto produto: this.produtos) {
            if (produto.getPreco() > maisCaro.getPreco()) {
                maisCaro = produto;
            }
        }

        return maisCaro;
    }

}