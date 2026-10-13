package br.edu.insper.campeonato.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String data;

    @ManyToOne
    @JoinColumn(name = "id_time_mandante")
    private Time timeMandante;

    @ManyToOne
    @JoinColumn(name = "id_time_visitante")
    private Time timeVisitante;

    private int golMandante;
    private int golVisitante;
    private boolean placarDefinido;

    @OneToMany(mappedBy = "partida")
    private List<Cartao> cartoes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_campeonato")
    private Campeonato campeonato;

    public void registrarGols(int golMandante, int golVisitante) {
        if (golMandante < 0 || golVisitante < 0) {
            throw new IllegalArgumentException("A quantidade de gols não pode ser negativa");
        }
        this.golMandante = golMandante;
        this.golVisitante = golVisitante;
        this.placarDefinido = true;
    }

    public void registrarCartao(Cartao cartao) {
        cartao.setPartida(this);
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
        if (jogador.getTime() == null) {
            return false;
        }
        Long idTime = jogador.getTime().getId();
        return idTime.equals(timeMandante.getId()) || idTime.equals(timeVisitante.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setGolMandante(int golMandante) {
        this.golMandante = golMandante;
    }

    public int getGolVisitante() {
        return golVisitante;
    }

    public void setGolVisitante(int golVisitante) {
        this.golVisitante = golVisitante;
    }

    public boolean isPlacarDefinido() {
        return placarDefinido;
    }

    public void setPlacarDefinido(boolean placarDefinido) {
        this.placarDefinido = placarDefinido;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }
}
