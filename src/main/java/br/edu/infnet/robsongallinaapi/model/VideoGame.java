package br.edu.infnet.robsongallinaapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VideoGame extends Game {

    private String platform;
    private String developer;

    public VideoGame(Long id, String title, String genre, int releaseYear, boolean owned, boolean played,
                     String platform, String developer) {
        super(id, title, genre, releaseYear, owned, played);
        this.platform = platform;
        this.developer = developer;
    }
}