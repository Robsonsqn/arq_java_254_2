package br.edu.infnet.robsongallinaapi.service;

import br.edu.infnet.robsongallinaapi.exceptions.GameNotFoundException;
import br.edu.infnet.robsongallinaapi.model.BoardGame;
import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.model.Publisher;
import br.edu.infnet.robsongallinaapi.model.VideoGame;
import br.edu.infnet.robsongallinaapi.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    private Publisher publisher = new Publisher(1L, "publisher1", "Brazil");

    @BeforeEach
    void setUp() {
        // evita UnnecessaryStubbingException tornando o stub lenient
        lenient().when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void findAll_returnsAllGames() {
        Game g1 = new VideoGame(null, "Stardew Valley", "Simulation", 2016, true, true, "PC", "ConcernedApe", publisher);
        Game g2 = new BoardGame(null, "Catan", "Strategy", 1995, true, true, 3, 4, publisher);

        when(gameRepository.findAll()).thenReturn(List.of(g1, g2));

        List<Game> result = gameService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gameRepository).findAll();
    }

    @Test
    void findById_presentAndEmpty() {
        Game g = new VideoGame(10L, "Elden Ring", "Action RPG", 2022, true, true, "PC", "FromSoftware", publisher);

        when(gameRepository.findById(10L)).thenReturn(Optional.of(g));
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Game> found = gameService.findById(10L);
        Optional<Game> notFound = gameService.findById(99L);

        assertTrue(found.isPresent());
        assertEquals("Elden Ring", found.get().getTitle());
        assertFalse(notFound.isPresent());
        verify(gameRepository, times(2)).findById(anyLong());
    }

    @Test
    void create_savesAndReturnsGame() {
        Game newGame = new VideoGame(null, "Cyberpunk 2077", "RPG", 2020, true, false, "PC", "CDPR", publisher);

        Game saved = gameService.create(newGame);

        assertNotNull(saved);
        assertEquals("Cyberpunk 2077", saved.getTitle());
        verify(gameRepository).save(newGame);
    }

    @Test
    void update_throwsWhenNotExists() {
        long id = 5L;
        Game toUpdate = new VideoGame(null, "Old", "Genre", 2000, true, false, "PC", "Dev", publisher);

        when(gameRepository.existsById(id)).thenReturn(false);

        GameNotFoundException ex = assertThrows(GameNotFoundException.class, () -> gameService.update(id, toUpdate));
        assertTrue(ex.getMessage().contains("Game not found with ID: " + id));
        verify(gameRepository).existsById(id);
        verify(gameRepository, never()).save(any());
    }

    @Test
    void update_successSetsIdAndSaves() {
        long id = 7L;
        Game toUpdate = new VideoGame(null, "Some Title", "Genre", 2010, true, false, "PC", "Dev", publisher);

        when(gameRepository.existsById(id)).thenReturn(true);

        Game updated = gameService.update(id, toUpdate);

        assertEquals(id, toUpdate.getId());
        assertEquals(id, updated.getId());
        verify(gameRepository).existsById(id);
        verify(gameRepository).save(toUpdate);
    }

    @Test
    void delete_throwsWhenNotExists() {
        long id = 11L;
        when(gameRepository.existsById(id)).thenReturn(false);

        GameNotFoundException ex = assertThrows(GameNotFoundException.class, () -> gameService.delete(id));
        assertTrue(ex.getMessage().contains("Game not found with ID: " + id));
        verify(gameRepository).existsById(id);
        verify(gameRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_successInvokesRepository() {
        long id = 12L;
        when(gameRepository.existsById(id)).thenReturn(true);

        gameService.delete(id);

        verify(gameRepository).existsById(id);
        verify(gameRepository).deleteById(id);
    }

    @Test
    void togglePlayedStatus_throwsWhenNotFound() {
        long id = 20L;
        when(gameRepository.findById(id)).thenReturn(Optional.empty());

        GameNotFoundException ex = assertThrows(GameNotFoundException.class, () -> gameService.togglePlayedStatus(id));
        assertTrue(ex.getMessage().contains("Game not found with ID: " + id));
        verify(gameRepository).findById(id);
    }

    @Test
    void togglePlayedStatus_togglesAndSaves() {
        long id = 21L;
        Game g = new VideoGame(id, "ToggleGame", "Genre", 2021, true, true, "PC", "Dev", publisher);
        g.setPlayed(true);

        when(gameRepository.findById(id)).thenReturn(Optional.of(g));

        Game result = gameService.togglePlayedStatus(id);

        assertFalse(result.isPlayed());
        assertFalse(g.isPlayed());
        verify(gameRepository).findById(id);
        verify(gameRepository).save(g);
    }
}