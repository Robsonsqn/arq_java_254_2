package br.edu.infnet.robsongallinaapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CardGame extends Game {

    private int cardCount;
    private String gameType;

    public CardGame(Long id, String title, String genre, int releaseYear, boolean owned, boolean played,
                    int cardCount, String gameType) {
        super(id, title, genre, releaseYear, owned, played);
        this.cardCount = cardCount;
        this.gameType = gameType;
    }
}