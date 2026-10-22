package br.edu.insper.lojavirtual.view;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class StatusView {
    @NotBlank(message = "Status é obrigatório")
    @Pattern(regexp = "ABERTO|PAGO|CANCELADO", message = "Status deve ser ABERTO, PAGO ou CANCELADO")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
