package br.edu.insper.campeonato.model;

import java.util.ArrayList;

public class Time {

    private final Long id;
    private String nome;
    private String cidade;
    private ArrayList<Jogador> jogadores;

    public Time(Long id, String nome, String cidade) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.jogadores = new ArrayList<>();
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void adicionarJogador(Jogador jogador) {
        if (!jogadores.contains(jogador)) {
            jogadores.add(jogador);
            jogador.setTime(this);
        }
    }

    public void removerJogador(Jogador jogador) {
        if (jogadores.remove(jogador)) {
            jogador.setTime(null);
        }
    }

    public ArrayList<Jogador> listarJogadores() {
        return new ArrayList<>(jogadores);
    }

    public double mediaIdadeJogadores() {
        if (jogadores.isEmpty()) {
            return 0;
        }

        double soma = 0;
        for (Jogador jogador : jogadores) {
            soma += jogador.getIdade();
        }
        return soma / jogadores.size();
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
