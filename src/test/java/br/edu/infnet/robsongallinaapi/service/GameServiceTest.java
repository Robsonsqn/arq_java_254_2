package br.edu.infnet.robsongallinaapi.service;

import br.edu.infnet.robsongallinaapi.model.BoardGame;
import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.model.VideoGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    @Test
    void save_shouldAssignIdToNewGame() {

        Game newGame = new VideoGame(null, "Cyberpunk 2077", "RPG", 2020, true, false, "PC", "CD Projekt Red");

        Game savedGame = gameService.save(newGame);

        assertNotNull(savedGame);
        assertEquals(1L, savedGame.getId());
    }

    @Test
    void save_shouldUpdateExistingGame() {

        Game game = new VideoGame(null, "Cyberpunk", "RPG", 2020, true, false, "PC", "CDPR");
        Game savedGame = gameService.save(game);
        savedGame.setTitle("Cyberpunk 2077 Phantom Liberty");

        Game updatedGame = gameService.save(savedGame);
        Optional<Game> foundGame = gameService.findById(savedGame.getId());

        assertEquals(1, gameService.findAll().size());
        assertEquals("Cyberpunk 2077 Phantom Liberty", updatedGame.getTitle());
        assertTrue(foundGame.isPresent());
        assertEquals("Cyberpunk 2077 Phantom Liberty", foundGame.get().getTitle());
    }

    @Test
    void findAll_shouldReturnAllGames() {

        Game videoGame = new VideoGame(null, "Stardew Valley", "Simulation", 2016, true, true, "PC", "ConcernedApe");
        Game boardGame = new BoardGame(null, "Catan", "Strategy", 1995, true, true, 3, 4, "Devir");
        gameService.save(videoGame);
        gameService.save(boardGame);

        List<Game> games = gameService.findAll();

        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    void findById_shouldReturnGameWhenExists() {

        Game game = new VideoGame(null, "Elden Ring", "Action RPG", 2022, true, true, "PC", "FromSoftware");
        Game savedGame = gameService.save(game);

        Optional<Game> foundGame = gameService.findById(savedGame.getId());

        assertTrue(foundGame.isPresent());
        assertEquals("Elden Ring", foundGame.get().getTitle());
    }

    @Test
    void findById_shouldReturnEmptyWhenNotExists() {
        Optional<Game> foundGame = gameService.findById(99L);

        assertFalse(foundGame.isPresent());
    }

    @Test
    void deleteById_shouldRemoveGameFromStorage() {

        Game game = new VideoGame(null, "Elden Ring", "Action RPG", 2022, true, true, "PC", "FromSoftware");
        Game savedGame = gameService.save(game);

        assertEquals(1, gameService.findAll().size());

        gameService.delete(savedGame.getId());

        assertEquals(0, gameService.findAll().size());
        assertFalse(gameService.findById(savedGame.getId()).isPresent());
    }
}