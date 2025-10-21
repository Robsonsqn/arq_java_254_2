package br.edu.infnet.robsongallinaapi.loaders;

import br.edu.infnet.robsongallinaapi.model.BoardGame;
import br.edu.infnet.robsongallinaapi.model.Publisher;
import br.edu.infnet.robsongallinaapi.service.GameService;
import br.edu.infnet.robsongallinaapi.service.PublisherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
@Order(2)
public class BoardGameLoader implements ApplicationRunner {

    private final GameService gameService;
    private final PublisherService publisherService;

    @Value("${game.data.loader.path.boardgames}")
    private Resource resourceFile;

    public BoardGameLoader(GameService gameService, PublisherService publisherService) {
        this.gameService = gameService;
        this.publisherService = publisherService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>> Loading BoardGame data from: " + resourceFile.getFilename());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceFile.getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().forEach(line -> {
                String[] data = line.split(",");
                Publisher publisher = publisherService.findOrCreateByNameAndCountry(data[5], data[6]);
                BoardGame boardGame = new BoardGame(null, data[0], data[1], Integer.parseInt(data[2]),
                        Boolean.parseBoolean(data[3]), Boolean.parseBoolean(data[4]),
                        Integer.parseInt(data[7]), Integer.parseInt(data[8]), publisher);
                gameService.create(boardGame);
            });
        }
        System.out.println(">>> BoardGame data loaded.");
    }
}