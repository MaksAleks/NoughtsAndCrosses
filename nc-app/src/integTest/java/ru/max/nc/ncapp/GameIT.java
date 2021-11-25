package ru.max.nc.ncapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.data.Game;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.max.nc.ncapp.api.GameApi.GAME_API_PATH;
import static ru.max.nc.ncapp.service.GameTestDataFactory.aNewDefaultGameDto;

public class GameIT extends BaseITWithAllRepositories {

    @Test
    @DisplayName("User should be able to create a game with specific name if no game exists with this name")
    public void shouldCreateGame() throws Exception {
        //given
        var gameDto = aNewDefaultGameDto().build();

        //when
        ResultActions result = mockMvc.perform(post(GAME_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsString(gameDto))
        );

        //then
        result.andExpect(status().isOk());

        var createdGameDto = readValueFromString(
                result.andReturn().getResponse().getContentAsString(),
                GameDto.class
        );
        Game savedGame = gameRepository.getByNameOrThrow(createdGameDto.getName());
        assertThat(savedGame.getName()).isEqualTo(gameDto.getName());
        assertThat(savedGame.getCreatedBy()).isEqualTo(gameDto.getCreatedBy());
        assertThat(savedGame.getFieldSize()).isEqualTo(gameDto.getFieldSize());
    }
}
