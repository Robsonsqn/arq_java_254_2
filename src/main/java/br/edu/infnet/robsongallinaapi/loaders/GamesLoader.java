package br.edu.infnet.robsongallinaapi.loaders;

import br.edu.infnet.robsongallinaapi.model.BoardGame;
import br.edu.infnet.robsongallinaapi.model.CardGame;
import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.model.VideoGame;
import br.edu.infnet.robsongallinaapi.service.GameService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class GamesLoader implements ApplicationRunner {

    private final GameService gameService;

    public GamesLoader(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>> Loading initial game data...");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream("/jogsos.txt")), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String type = data[0];
                Game newGame = null;

                if ("VIDEOGAME".equalsIgnoreCase(type)) {
                    newGame = new VideoGame(null, data[1], data[2], Integer.parseInt(data[3]),
                            Boolean.parseBoolean(data[4]), Boolean.parseBoolean(data[5]), data[6], data[7]);
                } else if ("BOARDGAME".equalsIgnoreCase(type)) {
                    newGame = new BoardGame(null, data[1], data[2], Integer.parseInt(data[3]),
                            Boolean.parseBoolean(data[4]), Boolean.parseBoolean(data[5]),
                            Integer.parseInt(data[6]), Integer.parseInt(data[7]), data[8]);
                } else if ("CARDGAME".equalsIgnoreCase(type)) {
                    newGame = new CardGame(null, data[1], data[2], Integer.parseInt(data[3]),
                            Boolean.parseBoolean(data[4]), Boolean.parseBoolean(data[5]),
                            Integer.parseInt(data[6]), data[7]);
                }

                if (newGame != null) {
                    gameService.save(newGame);
                }

                System.out.println(">>> Data loaded successfully.");
                gameService.findAll().forEach(System.out::println);
            }
        } catch (Exception ex) {
            System.out.println(">>> Data not loaded. Verify the file.");
        }
    }
}