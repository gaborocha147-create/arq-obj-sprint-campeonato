package br.edu.insper.campeonato.model;

public class Jogador {

    private final Long id;
    private String nome;
    private int numero;
    private String posicao;
    private Time time;
    private int idade;

    public Jogador(Long id, String nome, int numero, String posicao, int idade) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.posicao = posicao;
        this.time = null;
        this.idade = idade;
    }

    public Long getId() {
        return id;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Time getTime() {
        return time;
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
