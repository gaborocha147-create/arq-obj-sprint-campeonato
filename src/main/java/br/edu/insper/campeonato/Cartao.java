package br.edu.insper.campeonato;

public class Cartao {
    private Jogador jogador;
    private int minuto;
    private String tipo;
    private Partida partida;

    public Cartao(Jogador jogador, int minuto, String tipo, Partida partida) {
        this.jogador = jogador;
        this.minuto = minuto;
        this.tipo = tipo;
        this.partida = partida;
    }

    public void setTipo(String tipo) {
        if (tipo.equals("AMARELO") || tipo.equals("VERMELHO")) {
            this.tipo = tipo;
        }
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public void setPartida (Partida partida) {
        this.partida = partida;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public Jogador getJogador() {
        return this.jogador;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Partida getPartida() {
        return this.partida;
    }

    public int getMinuto() {
        return this.minuto;
    }

}