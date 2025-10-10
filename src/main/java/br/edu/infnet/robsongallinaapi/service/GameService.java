package br.edu.infnet.robsongallinaapi.service;

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
    public Game save(Game game) {
        if (game.getId() == null) {
            game.setId(idGenerator.incrementAndGet());
        }
        games.put(game.getId(), game);
        return game;
    }

    @Override
    public Optional<Game> findById(Long id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public void delete(Long id) {
        games.remove(id);
    }

    @Override
    public List<Game> findAll() {
        return List.copyOf(games.values());
    }
}