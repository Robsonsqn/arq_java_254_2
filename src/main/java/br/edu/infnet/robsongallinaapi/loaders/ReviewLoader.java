package br.edu.infnet.robsongallinaapi.loaders;

import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.model.Review;
import br.edu.infnet.robsongallinaapi.repository.GameRepository;
import br.edu.infnet.robsongallinaapi.repository.ReviewRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Order(4)
public class ReviewLoader implements ApplicationRunner {

    private final ReviewRepository reviewRepository;
    private final GameRepository gameRepository;

    public ReviewLoader(ReviewRepository reviewRepository, GameRepository gameRepository) {
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>> Loading Review data...");
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/reviews.txt"))) {
            reader.lines().forEach(line -> {
                String[] data = line.split(",");
                String gameTitle = data[0];

                Game game = gameRepository.findByTitleIgnoreCase(gameTitle)
                        .orElseThrow(() -> new RuntimeException("Game not found for review: " + gameTitle));

                Review review = new Review();
                review.setGame(game);
                review.setReviewerName(data[1]);
                review.setRating(Integer.parseInt(data[2]));
                review.setComment(data[3]);
                reviewRepository.save(review);
            });
        }
        System.out.println(">>> Review data loaded.");
    }
}