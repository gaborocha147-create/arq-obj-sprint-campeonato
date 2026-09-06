package br.edu.insper.campeonato.view;

import br.edu.insper.campeonato.model.Time;

public record ClassificacaoView(
        int posicao,
        Long idTime,
        String time,
        int pontos
) {
    public static ClassificacaoView fromModel(int posicao, Time time, int pontos) {
        return new ClassificacaoView(posicao, time.getId(), time.getNome(), pontos);
    }
}
