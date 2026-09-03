package br.edu.insper.campeonato;

import java.util.ArrayList;

public class Jogador {
    private String nome;
    private int numero;
    private String posicao;
    private Time time;
    private int idade;

    public Jogador(String nome, int numero, String posicao, Time time, int idade) {
        this.nome = nome;
        this.numero = numero;
        this.posicao = posicao;
        this.time = time;
        this.idade = idade;
    }

    public int getIdade() {
        return this.idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Time getTime() {
        return this.time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }
}