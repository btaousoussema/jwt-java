package com.ouss.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="secret")
@Getter
@Setter
public class SecretConfig {

    private String key;
}
