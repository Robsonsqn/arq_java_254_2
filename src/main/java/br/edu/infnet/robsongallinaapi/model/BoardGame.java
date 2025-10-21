package br.edu.infnet.robsongallinaapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BoardGame extends Game {

    private int minPlayers;
    private int maxPlayers;
    private String publisher;

    public BoardGame(Long id, String title, String genre, int releaseYear, boolean owned, boolean played,
                     int minPlayers, int maxPlayers, String publisher) {
        super(id, title, genre, releaseYear, owned, played);
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.publisher = publisher;
    }
}