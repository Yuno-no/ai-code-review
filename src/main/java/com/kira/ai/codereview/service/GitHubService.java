package com.kira.ai.codereview.service;

import com.kira.ai.codereview.comcmon.constants.ResultCode;
import com.kira.ai.codereview.comcmon.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @author: kira
 * @date: 2025/04/22 15:48
 **/
@Service
@Slf4j
@AllArgsConstructor
public class GitHubService {

    private static final long MAX_FILE_SIZE_BYTES = 1_000_000; // 1MB limit

    private GitHub gitHubClient;

    public void displayCurrentUserInfo() throws IOException {
        if (gitHubClient.isCredentialValid()) { // 检查凭证有效性 [1]
            GHMyself myself = gitHubClient.getMyself(); // [1]
            log.info("当前登录用户: {}", myself.getLogin());
            log.info("邮箱: {}", myself.getEmail()); // 注意：邮箱可能需要特定 scope 权限
            log.info("关注者数量: {}", myself.getFollowersCount());
        } else {
            log.info("客户端未经过身份验证或凭证无效。");
        }
    }

    /**
     * 获取指定的 Commit 对象
     * @param repositoryFullName "owner/repo"
     * @param commitSha Commit SHA
     * @return GHCommit 对象，如果找不到则返回 null 或抛出异常
     */
    public GHCommit getCommit(String repositoryFullName, String commitSha) {
        log.debug("Fetching commit {} from repository {}", commitSha, repositoryFullName);
        try {
            return gitHubClient.getRepository(repositoryFullName).getCommit(commitSha);
        } catch (IOException e) {
            log.error("Failed to fetch commit {} from {}: {}", commitSha, repositoryFullName, e.getMessage());
            throw new BusinessException(ResultCode.Fail, "获取get内容失败");
        }

    }

    /**
     * 获取 Commit 中变更的文件列表 (返回可分页的 Iterable)
     * @param commit GHCommit 对象，不能为 null
     * @return PagedIterable<GHCommit.File> 文件迭代器
     * @throws IOException GitHub API 交互错误
     * @throws BusinessException 如果 commit 为 null
     */
    public PagedIterable<GHCommit.File> getCommitFiles(GHCommit commit) throws IOException {
        if (commit == null) {
            throw new BusinessException(ResultCode.NotFindError, "commit记录为空");
        }
        try {
            log.debug("Requesting file list iterable for commit {}", commit.getSHA1());
            return commit.listFiles();
        } catch (IOException e) {
            log.error("Failed to list files for commit {}: {}", commit.getSHA1(), e.getMessage());
            throw new BusinessException(ResultCode.Fail, "获取changeList失败");
        }
    }

    /**
     * 获取指定文件在特定 Commit 时的内容
     * @param repositoryFullName "owner/repo"
     * @param commitSha Commit SHA
     * @param filePath 文件路径
     * @return 文件内容字符串，如果文件太大、找不到或为目录等则返回 null
     * @throws IOException GitHub API 交互错误
     */
    public String getFileContent(String repositoryFullName, String commitSha, String filePath) throws IOException {
        try {
            log.debug("Fetching content for file {} at commit {} in repo {}", filePath, commitSha.substring(0, 7), repositoryFullName);
            GHRepository repository = gitHubClient.getRepository(repositoryFullName); // 可以缓存 Repository 对象以优化
            GHContent fileContent = repository.getFileContent(filePath, commitSha);

            if (fileContent == null || fileContent.isDirectory()) {
                log.warn("Skipping content fetch for non-file or missing content: {}", filePath);
                return null;
            }
            if (fileContent.getSize() > MAX_FILE_SIZE_BYTES) {
                log.warn("Skipping large file {} ({} bytes)", filePath, fileContent.getSize());
                return "File content skipped (too large).";
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent.read(), StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (GHFileNotFoundException e) {
            log.warn("File not found during content fetch (might be binary or submodule change): {}", filePath);
            return null; // 文件不存在，返回 null
        } catch (IOException e) {
            log.error("Error fetching content for file {}: {}", filePath, e.getMessage());
            throw e; // 其他 IO 错误，向上抛出
        }
    }

    /**
     * 获取指定文件的 Patch/Diff 信息
     * @param file GHCommit.File 对象
     * @return Patch 字符串，可能为 null
     */
    public String getFilePatch(GHCommit.File file) {
        if (file == null) {
            return null;
        }
        // 注意：patch 可能很大，也需要考虑大小限制
        log.debug("Getting patch for file: {}", file.getFileName());
        return file.getPatch();
    }

    /**
     * 在指定的 Commit 上发表评论
     * @param repositoryFullName "owner/repo"
     * @param commitSha Commit SHA
     * @param comment 评论内容
     * @throws IOException GitHub API 交互错误
     */
    public void postCommitComment(String repositoryFullName, String commitSha, String comment) throws IOException {
        try {
            log.info("Posting comment to commit {} in repository {}", commitSha, repositoryFullName);
            GHCommit commit = getCommit(repositoryFullName, commitSha); // 需要重新获取或传入 commit 对象
            if (commit != null) {
                commit.createComment(comment);
                log.info("Successfully posted comment.");
            } else {
                log.error("Cannot post comment, commit {} not found.", commitSha);
                // 可以考虑抛出自定义异常
            }
        } catch (IOException e) {
            log.error("Failed to post comment to commit {} in {}: {}", commitSha, repositoryFullName, e.getMessage());
            throw e;
        }
    }

    /**
     * 检查 Rate Limit 的方法
     * @return GHRateLimit
     * @throws IOException GitHub api 交互异常
     */
    public GHRateLimit getRateLimit() throws IOException {
        return gitHubClient.getRateLimit();
    }
}
