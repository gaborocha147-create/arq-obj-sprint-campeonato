package br.edu.insper.campeonato.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Campeonato {

    private final Long id;
    private String nome;
    private int ano;
    private ArrayList<Time> times;
    private ArrayList<Partida> partidas;

    public Campeonato(Long id, String nome, int ano) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.times = new ArrayList<>();
        this.partidas = new ArrayList<>();
    }

    public void adicionarTime(Time time) {
        if (!times.contains(time)) {
            times.add(time);
        }
    }

    public void adicionarPartida(Partida partida) {
        if (!partidas.contains(partida)) {
            partidas.add(partida);
            adicionarTime(partida.getTimeMandante());
            adicionarTime(partida.getTimeVisitante());
        }
    }

    public Map<Time, Integer> exibirClassificacao() {
        Map<Time, Integer> pontos = new LinkedHashMap<>();
        for (Time time : times) {
            pontos.put(time, calcularPontos(time));
        }

        Map<Time, Integer> classificacao = new LinkedHashMap<>();
        pontos.entrySet().stream()
                .sorted(Map.Entry.<Time, Integer>comparingByValue().reversed()
                        .thenComparing(entrada -> entrada.getKey().getNome()))
                .forEachOrdered(entrada -> classificacao.put(entrada.getKey(), entrada.getValue()));
        return classificacao;
    }

    private int calcularPontos(Time time) {
        int pontos = 0;
        for (Partida partida : partidas) {
            if (!partida.isPlacarDefinido()) {
                continue;
            }

            boolean mandante = partida.getTimeMandante() == time;
            boolean visitante = partida.getTimeVisitante() == time;
            if (!mandante && !visitante) {
                continue;
            }

            if (partida.getGolMandante() == partida.getGolVisitante()) {
                pontos += 1;
            } else if ((mandante && partida.getGolMandante() > partida.getGolVisitante())
                    || (visitante && partida.getGolVisitante() > partida.getGolMandante())) {
                pontos += 3;
            }
        }
        return pontos;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public ArrayList<Time> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Time> times) {
        this.times = times;
    }

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(ArrayList<Partida> partidas) {
        this.partidas = partidas;
    }
}
