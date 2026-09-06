package br.edu.insper.campeonato.view;

import br.edu.insper.campeonato.model.Cartao;

public record CartaoView(
        Long id,
        String tipo,
        Long idJogador,
        String nomeJogador,
        int minuto
) {
    public static CartaoView fromModel(Cartao cartao) {
        return new CartaoView(
                cartao.getId(),
                cartao.getTipo().name(),
                cartao.getJogador().getId(),
                cartao.getJogador().getNome(),
                cartao.getMinuto()
        );
    }
}
