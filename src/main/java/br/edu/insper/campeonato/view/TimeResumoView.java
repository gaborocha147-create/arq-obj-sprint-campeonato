package br.edu.insper.campeonato.view;

import br.edu.insper.campeonato.model.Time;

public record TimeResumoView(
        Long id,
        String nome,
        String cidade
) {
    public static TimeResumoView fromModel(Time time) {
        return new TimeResumoView(time.getId(), time.getNome(), time.getCidade());
    }
}
