package br.edu.insper.campeonato;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Campeonato {
    private String nome;
    private int ano;
    private ArrayList<Time> times;
    private ArrayList<Partida> partidas;

    // Recebe nome, ano, times e partidas no campeonato
    public Campeonato(String nome, int ano) {
        this.nome = nome;
        this.ano = ano;
        this.times = new ArrayList<>();
        this.partidas = new ArrayList<>();
    }

    public void adicionarTime(Time time) {
        this.times.add(time);
    }

    public void adicionarPartida(Partida partida) {
        this.partidas.add(partida);
    }

    public ArrayList<Time> listarTimes() {
        return this.times;
    }

    public ArrayList<Partida> listarPartidas() {
        return this.partidas;
    }

    public Time buscarTime(String nome) {
        for (Time time: this.times) {
            if (time.getNome().equals(nome)) {
                return time;
            }
        }
        return null;
    }

    public ArrayList<Time> exibirClassificacao() {
        HashMap<Time, Integer> timePontos = new HashMap<>();
        for (Time time : this.times) {
            int ptsTime = 0;
            for (Partida partida : this.partidas) {
                if (partida.getTimeMandante().equals(time.getNome())) {
                    if (partida.getGolMandante() > partida.getGolVisitante()) {
                        ptsTime += 3;
                    }
                    else if (partida.getGolMandante() == partida.getGolVisitante()) {
                        ptsTime += 1;
                    }
                }
                else if  (partida.getTimeVisitante().equals(time.getNome())) {
                    if (partida.getGolVisitante() > partida.getGolMandante()) {
                        ptsTime += 3;
                    }
                    else if (partida.getGolVisitante() == partida.getGolMandante()) {
                        ptsTime += 1;
                    }
                }
            }
            timePontos.put(time, ptsTime);
        }
        ArrayList<Map.Entry<Time, Integer>> lista =
                new ArrayList<>(timePontos.entrySet());

        Collections.sort(lista, (a, b) ->
                Integer.compare(b.getValue(), a.getValue())
        );

        ArrayList<Time> classificacao = new ArrayList<>();

        for (Map.Entry<Time, Integer> entrada : lista) {
            classificacao.add(entrada.getKey());
        }

        return classificacao;
    }

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(ArrayList<Partida> partidas) {
        this.partidas = partidas;
    }

    public ArrayList<Time> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Time> times) {
        this.times = times;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
