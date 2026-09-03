package br.edu.insper.campeonato;

import java.util.ArrayList;

public class Partida {
    private String data;
    private Time timeMandante;
    private Time timeVisitante;
    private int golMandante;
    private int golVisitante;
    private ArrayList<Cartao> cartoes;
    private Campeonato campeonato;


    public Partida(String data, Time timeMandante, Time timeVisitante, int golMandante, int golVisitante, Campeonato campeonato) {
        this.data = data;
        this.timeMandante = timeMandante;
        this.timeVisitante = timeVisitante;
        this.golMandante = golMandante;
        this.golVisitante = golVisitante;
        this.cartoes = new ArrayList<>();
        this.campeonato = campeonato;
    }

    public Time getTimeMandante() {
        return this.timeMandante;
    }

    public Time getTimeVisitante() {
        return this.timeVisitante;
    }

    public int getGolMandante() {
        return this.golMandante;
    }

    public int getGolVisitante() {
        return this.golVisitante;
    }

    public void setTimeMandante(Time timeMandante) {
        this.timeMandante = timeMandante;
    }

    public void setTimeVisitante(Time timeVisitante) {
        this.timeVisitante = timeVisitante;
    }

    public void setGolMandante(int golMandante) {
        this.golMandante = golMandante;
    }

    public void setGolVisitante(int golVisitante) {
        this.golVisitante = golVisitante;
    }

    public void registrarTimes(Time timeMandante, Time timeVisitante) {
        setTimeMandante(timeMandante);
        setTimeVisitante(timeVisitante);
    }

    public void registrarGols(int golMandante, int golVisitante) {
        setGolMandante(golMandante);
        setGolVisitante(golVisitante);
    }

    public void registrarCartao(Cartao cartao) {
        this.cartoes.add(cartao);
    }

    public Time buscarVencedor(int golMandante, int golVisitante) {
        if (golMandante == golVisitante) {
            return null;
        }
        else if (golMandante > golVisitante) {
            return this.timeMandante;
        }
        else {
            return this.timeVisitante;
        }
    }

    public String exibirPlacar(int golMandante, int golVisitante) {
        final String placar = golMandante + "-" + golVisitante;
        return placar;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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