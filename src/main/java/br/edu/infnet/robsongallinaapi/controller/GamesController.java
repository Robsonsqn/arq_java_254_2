package br.edu.infnet.robsongallinaapi.controller;

import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesController {

    private final GameService gameService;

    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> findAll() {
        return ResponseEntity.ok(gameService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> findById(@PathVariable Long id) {
        return gameService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Game>> findByName(@PathVariable String title) {
        return ResponseEntity.ok(gameService.findByTitle(title));
    }

    @GetMapping("/releaseYear")
    public ResponseEntity<List<Game>> findByYearRange(@RequestParam int start, @RequestParam int end) {
        return ResponseEntity.ok(gameService.findByDate(start, end));
    }

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody Game game) {
        Game createdGame = gameService.create(game);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdGame.getId()).toUri();
        return ResponseEntity.created(location).body(createdGame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> update(@PathVariable Long id, @RequestBody Game game) {
        Game updatedGame = gameService.update(id, game);
        return ResponseEntity.ok(updatedGame);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-played")
    public ResponseEntity<Game> togglePlayedStatus(@PathVariable Long id) {
        Game updatedGame = gameService.togglePlayedStatus(id);
        return ResponseEntity.ok(updatedGame);
    }
}