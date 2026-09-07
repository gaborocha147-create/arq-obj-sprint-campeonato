package br.edu.insper.lojavirtual.models;

import java.util.ArrayList;

public class Loja {
    private String nome;
    private ArrayList<Produto> produtos;
    private ArrayList<Cliente> clientes;

    public Loja(String nome) {
        this.nome = nome;
        this.produtos = new ArrayList<>();
        this.clientes = new ArrayList<>();
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

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }

    public void adicionarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public Produto buscarProduto(int codigo) {
        for (Produto produto: this.produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }

    public Cliente buscarCliente(String cpf) {
        for (Cliente cliente: this.clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public ArrayList<Produto> listarProdutos() {
        return this.produtos;
    }

    public ArrayList<Produto> listarProdutosPorCategoria(Categoria categoria) {
        ArrayList<Produto> resultado = new ArrayList<>();
        for (Produto produto: this.produtos) {
            if (produto.getCategoria().equals(categoria)) {
                resultado.add(produto);
            }
        }
        return resultado;
    }

}