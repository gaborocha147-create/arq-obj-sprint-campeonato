package br.edu.insper.campeonato.view;

import br.edu.insper.campeonato.model.Time;

public record TimeView(
        Long id,
        String nome,
        String cidade,
        double mediaIdade,
        java.util.List<JogadorView> jogadores
) {
    public static TimeView fromModel(Time time) {
        return new TimeView(
                time.getId(),
                time.getNome(),
                time.getCidade(),
                time.mediaIdadeJogadores(),
                time.listarJogadores().stream().map(JogadorView::fromModel).toList()
        );
    }
}
