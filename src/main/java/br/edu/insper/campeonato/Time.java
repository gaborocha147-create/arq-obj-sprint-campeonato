package br.edu.insper.campeonato;

import java.util.ArrayList;

public class Time {
    private String nome;
    private String cidade;
    private ArrayList<Jogador> jogadores;

    public Time(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
        this.jogadores = new ArrayList<>();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void adicionarJogador(Jogador jogador) {
        this.jogadores.add(jogador);
    }

    public void removerJogador(Jogador jogador) {
        if (jogadores.contains(jogador)) {
            this.jogadores.remove(jogador);
        }
    }

    public ArrayList<Jogador> listarJogadores() {
        return this.jogadores;
    }

    public double mediaIdadeJogadores() {
        int n = this.jogadores.size();
        if (n == 0) {
            return 0;
        }
        double soma = 0;
        for (Jogador jogador : this.jogadores) {
            soma += jogador.getIdade();
        }
        return soma / n;
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

}