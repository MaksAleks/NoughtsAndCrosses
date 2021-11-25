package ru.max.nc.ncapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionTemplate;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static ru.max.nc.ncapp.BaseIT.DEFAULT_USER_NAME;


@WithMockUser(username = DEFAULT_USER_NAME)
@ActiveProfiles("integration-test")
@SpringBootTest(
        webEnvironment = RANDOM_PORT
)
@AutoConfigureMockMvc
abstract class BaseIT {
    public static final String DEFAULT_USER_NAME = "user";
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    protected TransactionTemplate transactionTemplate;
    @Autowired
    protected JdbcUserDetailsManager userDetailsManager;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String writeValueAsString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public <T> T readValueFromString(String s, Class<? extends T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(s, clazz);
    }
}
