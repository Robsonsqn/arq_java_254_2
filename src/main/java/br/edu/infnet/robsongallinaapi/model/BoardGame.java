package br.edu.infnet.robsongallinaapi.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BoardGame extends Game {

    private int minPlayers;
    private int maxPlayers;

    public BoardGame(Long id, String title, String genre, int releaseYear, boolean owned, boolean played,
                     int minPlayers, int maxPlayers, Publisher publisher) {
        super(id, title, genre, releaseYear, owned, played, publisher);
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }
}