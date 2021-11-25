package ru.max.nc.ncapp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    Optional<Game> findByName(String name);

    default Game getByIdOrThrow(UUID id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));
    }
}
