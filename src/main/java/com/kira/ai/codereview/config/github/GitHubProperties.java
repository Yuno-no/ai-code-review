package com.kira.ai.codereview.config.github;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: kira
 * @date: 2025/04/22 15:54
 **/
@Component
@ConfigurationProperties(prefix = "github")
@Data
public class GitHubProperties {
    private String webhookSecret;

    private String appId;

    private Long installationId;

    private String privateKey;

    private String token;
}
