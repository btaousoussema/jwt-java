package com.ouss.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="redis")
@Getter
@Setter
public class RedisConfig {

    private boolean active;
}
