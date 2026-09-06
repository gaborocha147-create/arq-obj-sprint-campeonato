package br.edu.insper.campeonato.view;

public record NovoCartaoView(
        String tipo,
        Long idJogador,
        int minuto
) {}
