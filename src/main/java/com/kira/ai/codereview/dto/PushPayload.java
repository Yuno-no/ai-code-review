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
    private String ref;

    @JsonProperty("before")
    private String before;

    @JsonProperty("after")
    private String after;

    @JsonProperty("repository")
    private Repository repository;

    @JsonProperty("pusher")
    private Pusher pusher;

    @JsonProperty("sender")
    private User sender; // User who triggered the event

    @JsonProperty("created")
    private boolean created;

    @JsonProperty("deleted")
    private boolean deleted;

    @JsonProperty("forced")
    private boolean forced;

    @JsonProperty("base_ref")
    private String baseRef; // Nullable

    @JsonProperty("compare")
    private String compare; // URL to compare changes

    @JsonProperty("commits")
    private List<Commit> commits;

    @JsonProperty("head_commit")
    private Commit headCommit; // Nullable if no commits

    // Organization and Enterprise might also be present at top level for org/enterprise events
    @JsonProperty("organization")
    private Organization organization; // Nullable

    @JsonProperty("enterprise")
    private Enterprise enterprise; // Nullable


    /**
     * Represents the repository where the push occurred.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Repository {
        @JsonProperty("id")
        private long id;

        @JsonProperty("node_id")
        private String nodeId;

        @JsonProperty("name")
        private String name;

        @JsonProperty("full_name")
        private String fullName;

        @JsonProperty("private")
        private boolean isPrivate;

        @JsonProperty("owner")
        private User owner; // Can be a User or Organization type

        @JsonProperty("html_url")
        private String htmlUrl;

        @JsonProperty("description")
        private String description; // Nullable

        @JsonProperty("fork")
        private boolean fork;

        @JsonProperty("url")
        private String url; // API URL

        // Many other URLs, for brevity not all are listed, add as needed e.g.:
        // forks_url, keys_url, collaborators_url, etc.

        @JsonProperty("created_at")
        private String createdAt; // Timestamp string (ISO 8601) or long (epoch seconds)
        // GitHub often uses epoch seconds or ISO string. String is safer here.

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("pushed_at")
        private String pushedAt;

        @JsonProperty("git_url")
        private String gitUrl;

        @JsonProperty("ssh_url")
        private String sshUrl;

        @JsonProperty("clone_url")
        private String cloneUrl;

        @JsonProperty("svn_url")
        private String svnUrl;

        @JsonProperty("homepage")
        private String homepage; // Nullable

        @JsonProperty("size")
        private int size;

        @JsonProperty("stargazers_count")
        private int stargazersCount;

        @JsonProperty("watchers_count")
        private int watchersCount;

        @JsonProperty("language")
        private String language; // Nullable

        @JsonProperty("has_issues")
        private boolean hasIssues;

        @JsonProperty("has_projects")
        private boolean hasProjects;

        @JsonProperty("has_downloads")
        private boolean hasDownloads;

        @JsonProperty("has_wiki")
        private boolean hasWiki;

        @JsonProperty("has_pages")
        private boolean hasPages;

        @JsonProperty("has_discussions")
        private boolean hasDiscussions;

        @JsonProperty("forks_count")
        private int forksCount;

        @JsonProperty("mirror_url")
        private String mirrorUrl; // Nullable

        @JsonProperty("archived")
        private boolean archived;

        @JsonProperty("disabled")
        private boolean disabled;

        @JsonProperty("open_issues_count")
        private int openIssuesCount;

        @JsonProperty("license")
        private License license; // Nullable, nested object

        @JsonProperty("allow_forking")
        private boolean allowForking;

        @JsonProperty("is_template")
        private boolean isTemplate;

        @JsonProperty("web_commit_signoff_required")
        private boolean webCommitSignoffRequired;

        @JsonProperty("topics")
        private List<String> topics;

        @JsonProperty("visibility")
        private String visibility; // e.g., "public", "private", "internal"

        @JsonProperty("default_branch")
        private String defaultBranch;

        // Aliases often present for convenience
        @JsonProperty("forks")
        private int forks;
        @JsonProperty("open_issues")
        private int openIssues;
        @JsonProperty("watchers")
        private int watchers;

        // Deprecated, use default_branch
        @JsonProperty("master_branch")
        private String masterBranch;
    }

    /**
     * Represents a GitHub user or organization actor.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User { // Used for sender, repository.owner
        @JsonProperty("login")
        private String login;

        @JsonProperty("id")
        private long id;

        @JsonProperty("node_id")
        private String nodeId;

        @JsonProperty("avatar_url")
        private String avatarUrl;

        @JsonProperty("gravatar_id")
        private String gravatarId; // Can be empty string

        @JsonProperty("url")
        private String url; // API URL

        @JsonProperty("html_url")
        private String htmlUrl; // Profile URL

        // Many other URLs, add as needed:
        // followers_url, following_url, gists_url, etc.

        @JsonProperty("type")
        private String type; // e.g., "User", "Organization"

        @JsonProperty("site_admin")
        private boolean siteAdmin;

        // Fields below might be specific to user type, less common for org in this simple form
        @JsonProperty("name")
        private String name; // Nullable, actual name if set

        @JsonProperty("company")
        private String company; // Nullable

        @JsonProperty("blog")
        private String blog; // Nullable

        @JsonProperty("location")
        private String location; // Nullable

        @JsonProperty("email")
        private String email; // Nullable, may not always be present

        @JsonProperty("hireable")
        private Boolean hireable; // Nullable

        @JsonProperty("bio")
        private String bio; // Nullable
    }

    /**
     * Represents the pusher of the commit (from git config).
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pusher {
        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;
        // Sometimes GitHub might add 'username' if it can link the email
        @JsonProperty("username")
        private String username; // Nullable
    }

    /**
     * Represents a commit in the push.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Commit {
        @JsonProperty("id") // This is the SHA
        private String id;

        // 'sha' is often an alias for 'id' in GitHub payloads for commits.
        // Keeping 'id' as primary based on common schema, but 'sha' might also appear.
        // @JsonProperty("sha")
        // private String sha;

        @JsonProperty("tree_id")
        private String treeId;

        @JsonProperty("distinct")
        private boolean distinct;

        @JsonProperty("message")
        private String message;

        @JsonProperty("timestamp")
        private String timestamp; // ISO 8601 format (Author date)

        @JsonProperty("url")
        private String url; // API URL for the commit

        @JsonProperty("author")
        private CommitActor author;

        @JsonProperty("committer")
        private CommitActor committer;

        @JsonProperty("added")
        private List<String> added; // List of added file paths

        @JsonProperty("removed")
        private List<String> removed; // List of removed file paths

        @JsonProperty("modified")
        private List<String> modified; // List of modified file paths
    }

    /**
     * Represents the author or committer of a commit.
     * Note: GitHub's push event payload for commit author/committer
     * usually includes 'username' if the user is a GitHub user.
     * The 'date' field for author/committer is part of the main commit's 'timestamp' (author date)
     * and the committer date is typically the same or inferred if not explicitly different.
     * Some other GitHub API endpoints for commits might have a separate 'date' field within author/committer.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommitActor {
        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("username")
        private String username; // Nullable, GitHub username if associated

        // According to the typical push event payload, 'date' is not part of these nested objects.
        // The commit's 'timestamp' field is the author's timestamp.
        // The committer timestamp is usually the same or handled by GitHub.
        // If you see 'date' here in specific payloads, you can add it:
        // @JsonProperty("date")
        // private String date; // ISO 8601 format
    }

    /**
     * Represents a software license.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class License {
        @JsonProperty("key")
        private String key;

        @JsonProperty("name")
        private String name;

        @JsonProperty("spdx_id")
        private String spdxId;

        @JsonProperty("url")
        private String url; // Nullable, API URL for license details

        @JsonProperty("node_id")
        private String nodeId;
    }

    /**
     * Represents a GitHub Organization.
     * Structure is very similar to User, often represented by the User object with type="Organization".
     * This class is for explicit distinction if needed or if fields diverge significantly.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Organization { // Used for top-level 'organization' field
        @JsonProperty("login")
        private String login;

        @JsonProperty("id")
        private long id;

        @JsonProperty("node_id")
        private String nodeId;

        @JsonProperty("url")
        private String url;

        @JsonProperty("repos_url")
        private String reposUrl;

        @JsonProperty("events_url")
        private String eventsUrl;

        @JsonProperty("hooks_url")
        private String hooksUrl;

        @JsonProperty("issues_url")
        private String issuesUrl;

        @JsonProperty("members_url")
        private String membersUrl; // Placeholder for the actual URL structure

        @JsonProperty("public_members_url")
        private String publicMembersUrl; // Placeholder for the actual URL structure

        @JsonProperty("avatar_url")
        private String avatarUrl;

        @JsonProperty("description")
        private String description; // Nullable
    }

    /**
     * Represents a GitHub Enterprise account.
     * Fields would be specific to enterprise information.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Enterprise {
        @JsonProperty("id")
        private long id;

        @JsonProperty("slug")
        private String slug;

        @JsonProperty("name")
        private String name;

        @JsonProperty("node_id")
        private String nodeId;

        @JsonProperty("avatar_url")
        private String avatarUrl;

        @JsonProperty("html_url")
        private String htmlUrl;
        // ... other enterprise-specific fields
    }
}
