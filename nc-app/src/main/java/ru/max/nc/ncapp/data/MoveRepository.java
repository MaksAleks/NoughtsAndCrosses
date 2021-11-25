package ru.max.nc.ncapp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MoveRepository extends JpaRepository<Move, UUID>, JpaSpecificationExecutor<Move> {

    Optional<Move> findByGameAndPosXAndPosY(Game game, int xPos, int yPos);

    List<Move> findByGame(Game game);
}