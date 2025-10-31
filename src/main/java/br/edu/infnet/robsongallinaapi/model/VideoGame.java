package br.edu.infnet.robsongallinaapi.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class VideoGame extends Game {

    private String platform;
    private String developer;

    public VideoGame(Long id, String title, String genre, int releaseYear, boolean owned, boolean played,
                     String platform, String developer, Publisher publisher) {
        super(id, title, genre, releaseYear, owned, played, publisher, Collections.emptyList());
        this.platform = platform;
        this.developer = developer;
    }
}