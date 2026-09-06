package br.edu.insper.campeonato.model;

public class Cartao {

    private final Long id;
    private final TipoCartao tipo;
    private final Jogador jogador;
    private final int minuto;

    public Cartao(Long id, TipoCartao tipo, Jogador jogador, int minuto) {
        this.id = id;
        this.tipo = tipo;
        this.jogador = jogador;
        this.minuto = minuto;
    }

    public Long getId() {
        return id;
    }

    public TipoCartao getTipo() {
        return tipo;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public int getMinuto() {
        return minuto;
    }
}
