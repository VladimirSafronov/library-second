package ru.safronov.timer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Класс-конфигурация. Нужен для того, чтобы стартер был гибким (его можно было настраивать из
 * клиента).
 */
@Data
@ConfigurationProperties("timer")
public class TimerProperties {
  //настройки
}
