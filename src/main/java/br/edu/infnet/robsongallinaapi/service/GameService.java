package br.edu.infnet.robsongallinaapi.service;

import br.edu.infnet.robsongallinaapi.exceptions.GameNotFoundException;
import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.repository.GameRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GameService { // Removida a implementação de CrudService por enquanto

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }


    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public Game update(Long id, Game gameToUpdate) {
        if (!gameRepository.existsById(id)) {
            throw new GameNotFoundException("Game not found with ID: " + id);
        }
        gameToUpdate.setId(id);
        return gameRepository.save(gameToUpdate);
    }

    public void delete(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new GameNotFoundException("Game not found with ID: " + id);
        }
        gameRepository.deleteById(id);
    }

    public Game togglePlayedStatus(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with ID: " + id));

        game.setPlayed(!game.isPlayed());

        return gameRepository.save(game);
    }
}