package br.edu.insper.campeonato.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Campeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    private int ano;

    @ManyToMany
    @JoinTable(
            name = "campeonato_time",
            joinColumns = @JoinColumn(name = "id_campeonato"),
            inverseJoinColumns = @JoinColumn(name = "id_time")
    )
    private List<Time> times = new ArrayList<>();

    @OneToMany(mappedBy = "campeonato")
    private List<Partida> partidas = new ArrayList<>();

    public void adicionarTime(Time time) {
        boolean jaExiste = times.stream().anyMatch(t -> t.getId().equals(time.getId()));
        if (!jaExiste) {
            times.add(time);
        }
    }

    public void adicionarPartida(Partida partida) {
        partida.setCampeonato(this);
        adicionarTime(partida.getTimeMandante());
        adicionarTime(partida.getTimeVisitante());
        if (!partidas.contains(partida)) {
            partidas.add(partida);


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

            boolean mandante = partida.getTimeMandante().getId().equals(time.getId());
            boolean visitante = partida.getTimeVisitante().getId().equals(time.getId());
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

    public void setId(Long id) {
        this.id = id;
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

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }
}
