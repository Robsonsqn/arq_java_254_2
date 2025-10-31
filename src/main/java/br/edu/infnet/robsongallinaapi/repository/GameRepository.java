package br.edu.infnet.robsongallinaapi.repository;

import br.edu.infnet.robsongallinaapi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
