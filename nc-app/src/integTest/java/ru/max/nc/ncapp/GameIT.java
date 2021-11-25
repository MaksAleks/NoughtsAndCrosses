package ru.max.nc.ncapp;

import com.github.dockerjava.api.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import ru.max.nc.ncapp.api.dto.GameDto;
import ru.max.nc.ncapp.service.GameTestDataFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.max.nc.ncapp.api.GameApi.GAME_API_PATH;

@Transactional
public class GameIT extends BaseIT {

    @BeforeEach
    public void init() {
        var auth = getAuthentication();
        if (auth == null) {
            throw new UnauthorizedException("You are not authorized");
        }
        userDetailsManager.createUser((User) auth.getPrincipal());
    }

    @Test
    @DisplayName("User should be able to create a game with specific name if no game exists with this name")
    public void shouldCreateGame() throws Exception {
        //given
        var gameDto = GameTestDataFactory.aNewDefaultGameDto().build();

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
        assertThat(createdGameDto.getId()).isNotNull();
        assertThat(createdGameDto.getName()).isEqualTo(gameDto.getName());
        assertThat(createdGameDto.getCreatedBy()).isEqualTo(gameDto.getCreatedBy());
        assertThat(createdGameDto.getFieldSize()).isEqualTo(gameDto.getFieldSize());
    }

}
