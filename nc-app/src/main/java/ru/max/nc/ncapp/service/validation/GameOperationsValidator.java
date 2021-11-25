package ru.max.nc.ncapp.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.data.GameRepository;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
public class GameOperationsValidator {

    private final GameRepository repository;

    public void validateCreation(GameDto dto, String username) {
        repository.findByName(dto.getName()).ifPresent(game -> {
            throw new EntityExistsException(
                    String.format("Game with name %s already exists. Creator %s",
                            dto.getName(), game.getCreatedBy()
                    )
            );
        });
    }
}
