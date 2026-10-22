package br.edu.insper.lojavirtual.view;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class NovoProdutoView {
    @NotBlank(message = "Nome do produto é obrigatório")
    private String nome;

    @Positive(message = "Preço deve ser maior que zero")
    private double preco;

    @Positive(message = "Informe o código de uma categoria")
    private int categoriaId;

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

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
}
