package ru.max.nc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("integration-test")
@SpringBootTest(
        webEnvironment = RANDOM_PORT
)
@TestPropertySource(properties = {
        "HOSTNAME=localhost"
})
@AutoConfigureMockMvc
class BaseIT {

    @Test
    void contextLoads() {

    }

}
