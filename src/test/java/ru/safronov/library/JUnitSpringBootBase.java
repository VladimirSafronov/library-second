package ru.safronov.library;

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Если имеется несколько классов, и их настройки будут как-то отличаться, то будет создаваться
 * несколько контеекстов. Можно создать один класс с таким environment, и наследовать от него
 * тестовые классы
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public abstract class JUnitSpringBootBase {

}
