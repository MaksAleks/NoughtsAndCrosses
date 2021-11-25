package ru.max.nc.ncapp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    Optional<Game> findByName(String name);

    default Game getByNameOrThrow(String name) {
        return findByName(name).orElseThrow(() -> new EntityNotFoundException(
                String.format("Game %s not does not exist", name)
        ));
    }
}
