package ru.max.nc.ncapp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.max.nc.ncapp.api.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterApi {

    private final PasswordEncoder passwordEncoder;
    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    @PostMapping
    public void login(@RequestBody UserDto user) {
        jdbcUserDetailsManager.createUser(
                new User(user.getName(),
                        passwordEncoder.encode(user.getPassword()),
                        List.of(new SimpleGrantedAuthority("USER")))
        );
    }
}
