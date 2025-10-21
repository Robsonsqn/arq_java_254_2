package br.edu.infnet.robsongallinaapi.controller;

import br.edu.infnet.robsongallinaapi.model.BoardGame;
import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.model.Publisher;
import br.edu.infnet.robsongallinaapi.model.VideoGame;
import br.edu.infnet.robsongallinaapi.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GamesController.class)
class GamesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameService gameService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public GameService gameService() {
            return mock(GameService.class);
        }
    }

    @Test
    void findAll_shouldReturnListOfGamesAndOkStatus() throws Exception {
        Publisher publisherVg = new Publisher(1L, "publisher1", "Brazil");
        Publisher publisherBg = new Publisher(2L, "publisher2", "Brazil");

        Game videoGame = new VideoGame(1L, "Stardew Valley", "Simulation", 2016, true, true, "PC", "ConcernedApe", publisherVg);
        Game boardGame = new BoardGame(2L, "Catan", "Strategy", 1995, true, true, 3, 4, publisherBg);
        List<Game> gamesList = List.of(videoGame, boardGame);

        when(gameService.findAll()).thenReturn(gamesList);

        mockMvc.perform(get("/games")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Stardew Valley"))
                .andExpect(jsonPath("$[0].platform").value("PC"))
                .andExpect(jsonPath("$[1].title").value("Catan"))
                .andExpect(jsonPath("$[1].minPlayers").value(3));
    }

    @Test
    void findById_whenNotFound_shouldReturnNotFound() throws Exception {
        when(gameService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/games/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
