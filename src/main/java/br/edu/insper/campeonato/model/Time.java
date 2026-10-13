package br.edu.insper.campeonato.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "times")
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cidade;

    @JsonIgnore
    @OneToMany(mappedBy = "time")
    private List<Jogador> jogadores = new ArrayList<>();

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

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
