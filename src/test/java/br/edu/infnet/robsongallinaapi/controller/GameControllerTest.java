package br.edu.infnet.robsongallinaapi.controller;

import br.edu.infnet.robsongallinaapi.controller.GamesController;
import br.edu.infnet.robsongallinaapi.model.BoardGame;
import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.model.VideoGame;
import br.edu.infnet.robsongallinaapi.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GamesController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @Test
    void findAll_shouldReturnListOfGamesAndOkStatus() throws Exception {
        Game videoGame = new VideoGame(1L, "Stardew Valley", "Simulation", 2016, true, true, "PC", "ConcernedApe");
        Game boardGame = new BoardGame(2L, "Catan", "Strategy", 1995, true, true, 3, 4, "Devir");
        List<Game> gamesList = Arrays.asList(videoGame, boardGame);

        when(gameService.findAll()).thenReturn(gamesList);

        mockMvc.perform(get("/games")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Stardew Valley"))
                .andExpect(jsonPath("$[0].platform").value("PC"))
                .andExpect(jsonPath("$[1].title").value("Catan"))
                .andExpect(jsonPath("$[1].minPlayers").value(3));
    }
}