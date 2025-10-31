package br.edu.infnet.robsongallinaapi.service;

import br.edu.infnet.robsongallinaapi.exceptions.GameNotFoundException;
import br.edu.infnet.robsongallinaapi.exceptions.ReviewNotFoundException;
import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.model.Review;
import br.edu.infnet.robsongallinaapi.repository.GameRepository;
import br.edu.infnet.robsongallinaapi.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final GameRepository gameRepository;

    public ReviewService(ReviewRepository reviewRepository, GameRepository gameRepository) {
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    public Review update(Long id, Review reviewToUpdate) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review not found with ID: " + id);
        }
        reviewToUpdate.setId(id);
        return reviewRepository.save(reviewToUpdate);
    }

    public void delete(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review not found with ID: " + id);
        }
        reviewRepository.deleteById(id);
    }

    public Review reviewGame(Long gameId, Review review) {
        if(!gameRepository.existsById(gameId)){
            throw new GameNotFoundException("Game not found with ID: " + gameId);
        }

        Game reviewedGame = gameRepository.findById(gameId).get();
        review.setGame(reviewedGame);

        return reviewRepository.save(review);
    }
}
