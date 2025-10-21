package br.edu.infnet.robsongallinaapi.service;

import br.edu.infnet.robsongallinaapi.exceptions.GameNotFoundException;
import br.edu.infnet.robsongallinaapi.exceptions.InvalidGameDataException;
import br.edu.infnet.robsongallinaapi.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class GameService implements CrudService<Game, Long> {

    private final Map<Long, Game> games = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public List<Game> findAll() {
        return List.copyOf(games.values());
    }

    @Override
    public Optional<Game> findById(Long id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public Game create(Game game) {
        if (game.getTitle() == null || game.getTitle().isBlank()) {
            throw new InvalidGameDataException("Game title cannot be empty.");
        }
        game.setId(idGenerator.incrementAndGet());
        games.put(game.getId(), game);

        System.out.println("[DEBUG] Jogo adicionado: " + game.getTitle() + ". Tamanho atual do mapa: " + games.size());
        return game;
    }

    @Override
    public Game update(Long id, Game gameToUpdate) {
        return findById(id).map(existingGame -> {
            gameToUpdate.setId(existingGame.getId());
            games.put(id, gameToUpdate);
            return gameToUpdate;
        }).orElseThrow(() -> new GameNotFoundException("Game not found with ID: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!games.containsKey(id)) {
            throw new GameNotFoundException("Game not found with ID: " + id);
        }
        games.remove(id);
    }

    public Game togglePlayedStatus(Long id) {
        Game game = findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with ID: " + id));
        game.setPlayed(!game.isPlayed());
        games.put(id, game);
        return game;
    }

}