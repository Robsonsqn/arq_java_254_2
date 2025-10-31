package br.edu.infnet.robsongallinaapi.controller;

import br.edu.infnet.robsongallinaapi.model.Game;
import br.edu.infnet.robsongallinaapi.model.Review;
import br.edu.infnet.robsongallinaapi.repository.GameRepository;
import br.edu.infnet.robsongallinaapi.repository.ReviewRepository;
import br.edu.infnet.robsongallinaapi.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping()
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("/games/{gameId}/review")
    public ResponseEntity<Review> reviewGame(@PathVariable Long gameId, @RequestBody Review review) {
        Review createdReview = reviewService.reviewGame(gameId, review);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdReview.getId()).toUri();
        return ResponseEntity.created(location).body(createdReview);
    }

    @PutMapping("/review/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Review updatedGame = reviewService.update(id, review);
        return ResponseEntity.ok(updatedGame);
    }

    @GetMapping("/review")
    public ResponseEntity<List<Review>> listReviews() {
        return ResponseEntity.ok(this.reviewService.findAll());
    }

}
