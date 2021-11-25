package ru.max.nc.ncapp;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import ru.max.nc.ncapp.data.GameRepository;
import ru.max.nc.ncapp.data.MoveRepository;

public class BaseITWithAllRepositories extends BaseIT {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    MoveRepository moveRepository;

    @AfterEach
    public void clear() {
        transactionTemplate.executeWithoutResult(s -> {
            gameRepository.deleteAll();
            moveRepository.deleteAll();
        });
    }
}
