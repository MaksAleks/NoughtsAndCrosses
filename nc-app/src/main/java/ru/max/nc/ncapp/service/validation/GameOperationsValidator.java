package ru.max.nc.ncapp.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.data.Game;
import ru.max.nc.ncapp.data.GameRepository;

import javax.persistence.EntityExistsException;

import static ru.max.nc.ncapp.api.dto.GameDto.Status.IN_PROGRESS;

@Service
@RequiredArgsConstructor
public class GameOperationsValidator {

    private final GameRepository repository;
    private JdbcUserDetailsManager userDetailsManager;

    public void validateCreation(GameDto dto, String username) {
        repository.findByName(dto.getName()).ifPresent(game -> {
            throw new EntityExistsException(
                    String.format("Game with name %s already exists. Creator %s",
                            dto.getName(), game.getCreatedBy()
                    )
            );
        });
    }

    public void validateJoin(Game game, String username) {
        validateGameStarted(game);
    }

    public void validateStart(Game game, String username) {
        validateGameStarted(game);
        if (!game.getCreatedBy().equals(username)) {
            throw new IllegalStateException("Game can be started only by creator");
        }
        if (!StringUtils.hasText(game.getSecondPlayer())) {
            throw new IllegalStateException("Can't start game without a second player");
        }
    }

    private void validateGameStarted(Game game) {
        if (IN_PROGRESS.equals(game.getStatus())) {
            throw new IllegalStateException(
                    String.format("Game %s is already started", game.getName())
            );
        }
    }
}
