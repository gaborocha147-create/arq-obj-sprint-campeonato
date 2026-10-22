package br.edu.insper.lojavirtual.view;

import jakarta.validation.constraints.NotBlank;

public class NovoPedidoView {
    @NotBlank(message = "CPF do cliente é obrigatório")
    private String cpfCliente;

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }
}
