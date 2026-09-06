package br.edu.insper.campeonato.model;

import java.util.ArrayList;

public class Partida {

    private final Long id;
    private String data;
    private Time timeMandante;
    private Time timeVisitante;
    private int golMandante;
    private int golVisitante;
    private boolean placarDefinido;
    private ArrayList<Cartao> cartoes;
    private Campeonato campeonato;

    public Partida(Long id, String data, Time timeMandante, Time timeVisitante, Campeonato campeonato) {
        this.id = id;
        this.data = data;
        this.timeMandante = timeMandante;
        this.timeVisitante = timeVisitante;
        this.golMandante = 0;
        this.golVisitante = 0;
        this.placarDefinido = false;
        this.cartoes = new ArrayList<>();
        this.campeonato = campeonato;
    }

    public void registrarGols(int golMandante, int golVisitante) {
        if (golMandante < 0 || golVisitante < 0) {
            throw new IllegalArgumentException("A quantidade de gols não pode ser negativa");
        }
        this.golMandante = golMandante;
        this.golVisitante = golVisitante;
        this.placarDefinido = true;
    }

    public void registrarCartao(Cartao cartao) {
        cartoes.add(cartao);
    }

    public Time buscarVencedor() {
        if (!placarDefinido || golMandante == golVisitante) {
            return null;
        }
        return golMandante > golVisitante ? timeMandante : timeVisitante;
    }

    public String exibirPlacar() {
        return golMandante + "-" + golVisitante;
    }

    public boolean jogadorParticipou(Jogador jogador) {
        return jogador.getTime() == timeMandante || jogador.getTime() == timeVisitante;
    }

    public Long getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Time getTimeMandante() {
        return timeMandante;
    }

    public void setTimeMandante(Time timeMandante) {
        this.timeMandante = timeMandante;
    }

    public Time getTimeVisitante() {
        return timeVisitante;
    }

    public void setTimeVisitante(Time timeVisitante) {
        this.timeVisitante = timeVisitante;
    }

    public int getGolMandante() {
        return golMandante;
    }

    public int getGolVisitante() {
        return golVisitante;
    }

    public boolean isPlacarDefinido() {
        return placarDefinido;
    }

    public ArrayList<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(ArrayList<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }
}
