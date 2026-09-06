package br.edu.insper.campeonato.view;

public record NovaPartidaView(
        String data,
        Long idTimeMandante,
        Long idTimeVisitante,
        Long idCampeonato
) {}
