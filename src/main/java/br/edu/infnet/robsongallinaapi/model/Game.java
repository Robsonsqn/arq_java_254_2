package br.edu.infnet.robsongallinaapi.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = VideoGame.class, name = "video_game"),
        @JsonSubTypes.Type(value = BoardGame.class, name = "board_game"),
        @JsonSubTypes.Type(value = CardGame.class, name = "card_game")
})
public abstract class Game {
    private Long id;
    private String title;
    private String genre;
    private int releaseYear;
    private boolean owned;
    private boolean played;
    private Publisher publisher;
}
