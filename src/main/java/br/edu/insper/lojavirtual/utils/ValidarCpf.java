package br.edu.insper.lojavirtual.utils;

public class ValidarCpf {
    public static boolean formatoValido(String cpf) {
        if (cpf == null) {
            return false;
        }
        return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") || cpf.matches("\\d{11}");
    }
}
