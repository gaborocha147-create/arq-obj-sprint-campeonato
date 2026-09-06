package br.edu.insper.campeonato.view;

import br.edu.insper.campeonato.model.Jogador;

public record JogadorView(
        Long id,
        String nome,
        int numero,
        String posicao,
        TimeResumoView time,
        int idade
) {
    public static JogadorView fromModel(Jogador jogador) {
        return new JogadorView(
                jogador.getId(),
                jogador.getNome(),
                jogador.getNumero(),
                jogador.getPosicao(),
                jogador.getTime() == null ? null : TimeResumoView.fromModel(jogador.getTime()),
                jogador.getIdade()
        );
    }
}
