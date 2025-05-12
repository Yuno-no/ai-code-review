package com.kira.ai.codereview.config.github;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: kira
 * @date: 2025/05/12 15:54
 **/
@Component
@ConfigurationProperties(prefix = "analysis.filter")
@Data
public class AnalysisFilterProperties {
    // 允许分析的文件扩展名列表
    private List<String> allowedExtensions = new ArrayList<>();

    // 排除分析的文件状态列表
    private List<String> excludedStatuses = new ArrayList<>();
}
