package br.edu.insper.lojavirtual;

public class Main {

    private static Categoria eletronicos = new Categoria(1, "Eletrônicos");
    private static Categoria alimentos = new Categoria(2, "Alimentos");
    private static Categoria vestuario = new Categoria(3, "Vestuário");

    private static Produto p1 = new Produto(101, "Notebook", 3500.00, eletronicos);
    private static Produto p2 = new Produto(102, "Mouse", 80.00, eletronicos);
    private static Produto p3 = new Produto(103, "Teclado", 150.00, eletronicos);
    private static Produto p4 = new Produto(104, "Fone de Ouvido", 200.00, eletronicos);
    private static Produto p5 = new Produto(105, "Arroz 5kg", 25.00, alimentos);
    private static Produto p6 = new Produto(106, "Feijão 1kg", 8.00, alimentos);
    private static Produto p7 = new Produto(107, "Camiseta", 45.00, vestuario);
    private static Produto p8 = new Produto(108, "Calça Jeans", 120.00, vestuario);

    private static Cliente c1 = new Cliente("João Silva", "111.111.111-11", "joao@email.com");
    private static Cliente c2 = new Cliente("Maria Souza", "222.222.222-22", "maria@email.com");
    private static Cliente c3 = new Cliente("Pedro Santos", "333.333.333-33", "pedro@email.com");

    private static Pedido pedido1 = new Pedido(1, c1);
    private static Pedido pedido2 = new Pedido(2, c2);
    private static Pedido pedido3 = new Pedido(3, c3);
    private static Pedido pedido4 = new Pedido(4, c1);

    private static Loja loja = new Loja("Loja do Fulano");

    public static void main(String[] args) {
        loja.adicionarProduto(p1);
        loja.adicionarProduto(p2);
        loja.adicionarProduto(p3);
        loja.adicionarProduto(p4);
        loja.adicionarProduto(p5);
        loja.adicionarProduto(p6);
        loja.adicionarProduto(p7);
        loja.adicionarProduto(p8);

        eletronicos.adicionarProduto(p1);
        eletronicos.adicionarProduto(p2);
        eletronicos.adicionarProduto(p3);
        eletronicos.adicionarProduto(p4);
        alimentos.adicionarProduto(p5);
        alimentos.adicionarProduto(p6);
        vestuario.adicionarProduto(p7);
        vestuario.adicionarProduto(p8);

        loja.adicionarCliente(c1);
        loja.adicionarCliente(c2);
        loja.adicionarCliente(c3);

        pedido1.adicionarProduto(p1, 1); // 1 notebook
        pedido1.adicionarProduto(p2, 2); // 2 mouses
        c1.adicionarPedido(pedido1);

        pedido2.adicionarProduto(p5, 3); // 3 arroz
        pedido2.adicionarProduto(p6, 5); // 5 feijão
        pedido2.alterarStatus("FINALIZADO");
        c2.adicionarPedido(pedido2);

        pedido3.adicionarProduto(p7, 4); // 4 camisetas
        pedido3.adicionarProduto(p8, 1); // 1 calça
        pedido3.adicionarProduto(p3, 1); // 1 teclado
        c3.adicionarPedido(pedido3);

        pedido4.adicionarProduto(p4, 2); // 2 fones de ouvido
        c1.adicionarPedido(pedido4);

        System.out.println("\n=== Valor de cada pedido ===");
        for (Cliente cliente : loja.getClientes()) {
            for (Pedido pedido : cliente.listarPedidos()) {
                System.out.printf("Pedido #%d (%s) - Cliente: %s - Total: R$ %.2f%n",
                        pedido.getNumero(), pedido.getStatus(), cliente.getNome(), pedido.calcularTotal());
            }
        }

        System.out.println("\n=== Total gasto por cliente ===");
        for (Cliente cliente : loja.getClientes()) {
            System.out.printf("%s - Total gasto: R$ %.2f%n", cliente.getNome(), cliente.calcularTotalGasto());
        }

        System.out.println("\n=== Produto mais caro por categoria ===");
        Categoria[] categorias = { eletronicos, alimentos, vestuario };
        for (Categoria categoria : categorias) {
            Produto maisCaro = categoria.buscarProdutoMaisCaro();
            if (maisCaro != null) {
                System.out.printf("%s - Produto mais caro: %s (R$ %.2f)%n",
                        categoria.getNome(), maisCaro.getNome(), maisCaro.getPreco());
            } else {
                System.out.printf("%s - Nenhum produto cadastrado%n", categoria.getNome());
            }
        }
    }
}