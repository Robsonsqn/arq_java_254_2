package br.edu.infnet.robsongallinaapi.controller;

import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games") // API endpoint in English
public class GamesController {

    private final GameService gameService;

    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> findAll() {
        return gameService.findAll();
    }
}