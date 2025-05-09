package com.kira.ai.codereview.service;

import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.PagedIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: kira
 * @date: 2025/04/22 15:49
 **/
@Service
@Slf4j
public class CodeReviewService {

    @Autowired
    GitHubService gitHubService;

    @Autowired
    ChatService chatService;

    public void performAutomatedReview(String repositoryFullName, String commitSha) {
        log.info("Starting automated review for commit {} in repository {}", commitSha, repositoryFullName);
        try {
            // 1. 获取 Commit 信息
            GHCommit commit = gitHubService.getCommit(repositoryFullName, commitSha);
            if (commit == null) {
                log.error("Review aborted: Commit {} not found in repository {}", commitSha, repositoryFullName);
                return;
            }

            // 2. 获取变更文件列表 (现在是 PagedIterable)
            PagedIterable<GHCommit.File> changedFilesIterable = gitHubService.getCommitFiles(commit);

            // 3. 过滤文件并获取内容
            Map<String, String> filesToAnalyzeContent = new HashMap<>();
            boolean fileProcessed = false; // 用于检查是否有文件进入循环处理

            // 直接遍历 PagedIterable，库会自动处理分页
            for (GHCommit.File file : changedFilesIterable) {
                fileProcessed = true; // 标记至少有一个文件被迭代到

                // 过滤逻辑 (保持不变)
                boolean shouldAnalyze = file.getFileName().endsWith(".md") && !"removed".equals(file.getStatus());

                if (shouldAnalyze) {
                    // 获取文件内容 (逻辑保持不变)
                    String content = gitHubService.getFileContent(repositoryFullName, commitSha, file.getFileName());
                    if (content != null && !content.isEmpty()) {
                        filesToAnalyzeContent.put(file.getFileName(), content);
                    } else {
                        log.warn("Skipping analysis for file {} due to missing or empty content/patch.", file.getFileName());
                    }
                } else {
                    log.debug("Skipping file not matching criteria: {}", file.getFileName());
                }
            } // 增强 for 循环结束，所有分页（如果存在）都已处理

            // 检查是否有文件被处理或过滤后符合条件
            if (!fileProcessed) {
                log.info("Review finished: No changed files found in commit {}", commitSha);
                // gitHubService.postCommitComment(repositoryFullName, commitSha, "AI Analysis: No file changes detected in this commit.");
                return;
            }
            if (filesToAnalyzeContent.isEmpty()) {
                log.info("Review finished: No suitable files found for analysis in commit {}", commitSha);
                // gitHubService.postCommitComment(repositoryFullName, commitSha, "AI Analysis: No files suitable for analysis were found in this commit.");
                return;
            }

            // 4. 调用 AI 分析 (保持不变)
            String analysisResult = chatService.analyzeCodeChanges(filesToAnalyzeContent, commitSha);

            // 5. 格式化最终评论 (可选, 保持不变)
            String finalComment = "AI Code Review for commit `" + commitSha.substring(0, 7) + "`:\n\n" + analysisResult;

            // 6. 发表评论 (保持不变)
            gitHubService.postCommitComment(repositoryFullName, commitSha, finalComment);

            log.info("Automated review completed successfully for commit {} in repository {}", commitSha, repositoryFullName);

        } catch (IOException e) { // IOException 可能来自 getCommit, getCommitFiles, getFileContent, postCommitComment
            log.error("GitHub API error during automated review for {} in {}: {}", commitSha, repositoryFullName, e.getMessage(), e);
            // 尝试发表失败评论
            try {
                // 避免在失败处理中再次调用可能失败的 getCommit
                // 理想情况下 postCommitComment 应该能接受 commitSha
                // (当前实现中 postCommitComment 内部会调用 getCommit，所以这里可能再次失败)
                gitHubService.postCommitComment(repositoryFullName, commitSha, "AI Review Failed: An error occurred while interacting with GitHub. Please check logs.");
            } catch (IOException ex) {
                log.error("Failed to post error comment for commit {}: {}", commitSha, ex.getMessage());
            }
        } catch (IllegalArgumentException e) { // 捕获 getCommitFiles 可能抛出的异常
            log.error("Error during automated review setup for {} in {}: {}", commitSha, repositoryFullName, e.getMessage(), e);
        } catch (Exception e) { // 捕获 AI Service 或其他未预期异常
            log.error("Unexpected error during automated review for {} in {}: {}", commitSha, repositoryFullName, e.getMessage(), e);
            try {
                gitHubService.postCommitComment(repositoryFullName, commitSha, "AI Review Failed: An unexpected error occurred. Please check logs.");
            } catch (IOException ex) {
                log.error("Failed to post error comment for commit {}: {}", commitSha, ex.getMessage());
            }
        }
    }
}
