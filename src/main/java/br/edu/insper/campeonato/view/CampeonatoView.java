package br.edu.insper.campeonato.view;

import br.edu.insper.campeonato.model.Campeonato;

public record CampeonatoView(
        Long id,
        String nome,
        int ano
) {
    public static CampeonatoView fromModel(Campeonato campeonato) {
        return new CampeonatoView(campeonato.getId(), campeonato.getNome(), campeonato.getAno());
    }
}
