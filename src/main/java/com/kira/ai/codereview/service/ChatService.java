package com.kira.ai.codereview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: kira
 * @date: 2025/04/22 15:48
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final OpenAiChatModel chatModel;

    /**
     * 分析代码变更
     * @param filesContent Map<String, String> key: 文件路径, value: 文件内容 (或 patch)
     * @param commitSha 用于 Prompt 上下文
     * @return AI 生成的分析结果字符串
     */
    public String analyzeCodeChanges(Map<String, String> filesContent, String commitSha) {
        if (filesContent == null || filesContent.isEmpty()) {
            log.warn("No file content provided for AI analysis.");
            return "No content provided for analysis.";
        }

        // ---- 准备 AI 输入 ----
        String combinedContent = filesContent.entrySet().stream()
                .map(entry -> "--- File: " + entry.getKey() + " ---\n" + entry.getValue() + "\n")
                .collect(Collectors.joining("\n"));

        // ---- 内容截断 ----
        /*if (combinedContent.length() > maxInputChars) {
            log.warn("Combined content length ({}) exceeds limit ({}), truncating.", combinedContent.length(), maxInputChars);
            combinedContent = combinedContent.substring(0, maxInputChars) + "\n... (Content truncated due to length)";
        }*/

        // ---- 构建 Prompt ----
        // 可以使用 PromptTemplate 实现更灵活的构建
        // PromptTemplate template = new PromptTemplate(analysisPromptTemplate);
        // Map<String, Object> model = Map.of("commitSha", commitSha.substring(0, 7), "codeChanges", combinedContent);
        // Prompt prompt = template.create(model);
        // String finalPrompt = prompt.getContents();

        // 简单的字符串格式化方式
        //String finalPrompt = String.format(analysisPromptTemplate, commitSha.substring(0, 7), combinedContent);

        //log.info("Sending content ({} chars) to AI for analysis for commit {}...", finalPrompt.length(), commitSha.substring(0,7));

        try {
            // ---- 调用 AI ----
            ChatResponse response = chatModel.call(
                    new Prompt(
                            combinedContent
                    ));
            log.info("AI analysis received.");
            return response.getResult().getOutput().getText();
        } catch (Exception e) {
            // 根据 Spring AI 具体的异常类型进行捕获和处理
            log.error("Error calling AI service: {}", e.getMessage(), e);
            return "Error occurred during AI analysis: " + e.getMessage();
        }
    }
}
