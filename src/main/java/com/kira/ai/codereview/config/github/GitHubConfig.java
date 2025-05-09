package com.kira.ai.codereview.config.github;

import com.kira.ai.codereview.comcmon.exception.BusinessException;
import jakarta.annotation.Resource;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author: kira
 * @date: 2025/04/23 10:18
 **/
@Configuration
public class GitHubConfig {
    @Resource
    private GitHubProperties properties;

    @Bean
    public GitHub oauthGitHubClient() throws IOException {
        String token = properties.getToken();
        // 检查令牌是否存在
        if (token == null || token.isBlank()) {
            throw new BusinessException("缺少必要的 GitHub OAuth 令牌配置: github.oauth.token");
        }
        return new GitHubBuilder().withOAuthToken(token).build();
    }
}
