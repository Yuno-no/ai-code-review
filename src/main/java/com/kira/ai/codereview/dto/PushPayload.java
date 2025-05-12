package com.kira.ai.codereview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * @author: Kira
 * @date: 2025/05/12 10:13
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushPayload {

    @JsonProperty("ref")
    private String ref; // 推送的引用，例如 "refs/heads/main" 或 "refs/tags/v1.0"

    @JsonProperty("before")
    private String before; // 推送前的SHA哈希值 (如果是新分支，则为全0)

    @JsonProperty("after")
    private String after; // 推送后的SHA哈希值 (如果是删除分支，则为全0)

    @JsonProperty("repository")
    private Repository repository;

    @JsonProperty("pusher")
    private Pusher pusher;

    @JsonProperty("sender") // 发送此webhook事件的用户/应用
    private User sender;

    @JsonProperty("created")
    private boolean created; // 如果此推送创建了新的引用（分支/标签），则为true

    @JsonProperty("deleted")
    private boolean deleted; // 如果此推送删除了引用（分支/标签），则为true

    @JsonProperty("forced")
    private boolean forced; // 如果这是一个强制推送，则为true

    @JsonProperty("base_ref")
    private String baseRef; // 通常为null，除非是合并推送

    @JsonProperty("compare")
    private String compareUrl; // 比较此推送中更改的URL

    @JsonProperty("commits")
    private List<Commit> commits; // 本次推送中包含的提交列表

    @JsonProperty("head_commit")
    private Commit headCommit; // 最新的提交（通常是commits列表中的最后一个）



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Repository {
        @JsonProperty("id")
        private long id;

        @JsonProperty("node_id")
        private String nodeId;

        @JsonProperty("name")
        private String name; // 仓库名称

        @JsonProperty("full_name")
        private String fullName; // 例如 "octocat/Hello-World"

        @JsonProperty("private")
        private boolean isPrivate;

        @JsonProperty("owner")
        private User owner; // 仓库所有者

        @JsonProperty("html_url")
        private String htmlUrl; // 仓库的URL

        @JsonProperty("description")
        private String description;

        @JsonProperty("fork")
        private boolean fork;

        @JsonProperty("url") // API URL
        private String url;

        @JsonProperty("ssh_url")
        private String sshUrl;

        @JsonProperty("clone_url")
        private String cloneUrl;

        // ... 其他仓库相关字段，例如 topics, visibility, default_branch 等


    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User { // 用于 owner 和 sender

        @JsonProperty("name") // Git committer name if available in pusher, otherwise login
        private String name;

        @JsonProperty("email") // Git committer email if available in pusher
        private String email;

        @JsonProperty("login") // GitHub username
        private String login;

        @JsonProperty("id")
        private long id;

        @JsonProperty("node_id")
        private String nodeId;

        @JsonProperty("avatar_url")
        private String avatarUrl;

        @JsonProperty("html_url")
        private String htmlUrl;

        @JsonProperty("type") // "User" or "Organization"
        private String type;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pusher { // 推送者信息 (通常来自git配置)
        @JsonProperty("name")
        private String name; // git config user.name

        @JsonProperty("email")
        private String email; // git config user.email
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Commit {
        @JsonProperty("id")
        private String id; // SHA哈希值

        @JsonProperty("tree_id")
        private String treeId;

        @JsonProperty("distinct")
        private boolean distinct; // 此提交是否是此推送中的新提交 (对于强制推送中的旧提交可能为false)

        @JsonProperty("message")
        private String message; // 提交信息

        @JsonProperty("timestamp") // ISO 8601 格式的时间戳
        private String timestamp;

        @JsonProperty("url")
        private String url; // 提交的URL

        @JsonProperty("author")
        private CommitUser author; // 提交的作者 (来自git commit)

        @JsonProperty("committer")
        private CommitUser committer; // 提交的提交者 (来自git commit)

        @JsonProperty("added")
        private List<String> added; // 添加的文件列表

        @JsonProperty("removed")
        private List<String> removed; // 删除的文件列表

        @JsonProperty("modified")
        private List<String> modified; // 修改的文件列表
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommitUser { // 用于Commit内的author和committer
        @JsonProperty("name")
        private String name; // git commit 中的名称

        @JsonProperty("email")
        private String email; // git commit 中的邮箱

        @JsonProperty("username") // GitHub username, if the email is associated with an account
        private String username;

        @JsonProperty("date") // GitHub特有的，有时author/committer对象里会有date字段
        private String date; // ISO 8601 格式的时间戳
    }
}
