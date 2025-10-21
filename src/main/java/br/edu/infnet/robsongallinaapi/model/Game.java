package br.edu.infnet.robsongallinaapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Game {
    private Long id;
    private String title;
    private String genre;
    private int releaseYear;
    private boolean owned;
    private boolean played;
}
