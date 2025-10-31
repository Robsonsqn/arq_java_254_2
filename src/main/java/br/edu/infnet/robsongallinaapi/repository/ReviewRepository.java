package br.edu.infnet.robsongallinaapi.repository;

import br.edu.infnet.robsongallinaapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
