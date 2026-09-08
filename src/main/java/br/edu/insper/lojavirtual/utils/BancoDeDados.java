package br.edu.insper.lojavirtual.utils;

import br.edu.insper.lojavirtual.models.Categoria;
import br.edu.insper.lojavirtual.models.Cliente;
import br.edu.insper.lojavirtual.models.Pedido;
import br.edu.insper.lojavirtual.models.Produto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BancoDeDados {
    private List<Categoria> categorias = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();

    private int proximoCodigoCategoria = 1;
    private int proximoCodigoProduto = 1;
    private int proximoNumeroPedido = 1;

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public int gerarCodigoCategoria() {
        return proximoCodigoCategoria++;
    }

    public int gerarCodigoProduto() {
        return proximoCodigoProduto++;
    }

    public Categoria buscarCategoriaPorCodigo(int codigo) {
        for (Categoria categoria : categorias) {
            if (categoria.getCodigo() == codigo) {
                return categoria;
            }
        }
        return null;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public int gerarNumeroPedido() {
        return proximoNumeroPedido++;
    }

    public Pedido buscarPedidoPorNumero(int numero) {
        for (Pedido pedido : pedidos) {
            if (pedido.getNumero() == numero) {
                return pedido;
            }
        }
        return null;
    }

    public Produto buscarProdutoPorCodigo(int codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }
}
