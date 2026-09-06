package br.edu.insper.campeonato.view;

import br.edu.insper.campeonato.model.Partida;

import java.util.List;

public record PartidaView(
        Long id,
        String data,
        TimeResumoView timeMandante,
        TimeResumoView timeVisitante,
        int golMandante,
        int golVisitante,
        boolean placarDefinido,
        String placar,
        Long idCampeonato,
        List<CartaoView> cartoes
) {
    public static PartidaView fromModel(Partida partida) {
        return new PartidaView(
                partida.getId(),
                partida.getData(),
                TimeResumoView.fromModel(partida.getTimeMandante()),
                TimeResumoView.fromModel(partida.getTimeVisitante()),
                partida.getGolMandante(),
                partida.getGolVisitante(),
                partida.isPlacarDefinido(),
                partida.exibirPlacar(),
                partida.getCampeonato().getId(),
                partida.getCartoes().stream().map(CartaoView::fromModel).toList()
        );
    }
}
