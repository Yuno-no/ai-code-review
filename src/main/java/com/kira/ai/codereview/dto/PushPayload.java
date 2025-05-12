package com.kira.ai.codereview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @author: Kira
 * @date: 2025/05/12 10:13
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private GitHubUser sender;

    @JsonProperty("enterprise")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Enterprise enterprise;

    @JsonProperty("installation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Installation installation;

    @JsonProperty("organization")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Organization organization;

    @JsonProperty("created")
    private boolean created;

    @JsonProperty("deleted")
    private boolean deleted;

    @JsonProperty("forced")
    private boolean forced;

    @JsonProperty("base_ref")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String baseRef;

    @JsonProperty("compare")
    private String compare;

    @JsonProperty("commits")
    private List<Commit> commits;

    @JsonProperty("head_commit")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HeadCommit headCommit;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pusher {

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
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
        private boolean isPrivate; // Lombok will generate isPrivate()

        @JsonProperty("owner")
        private GitHubUser owner;

        @JsonProperty("html_url")
        private String htmlUrl;

        @JsonProperty("description")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String description;

        @JsonProperty("fork")
        private boolean fork;

        @JsonProperty("url")
        private String url;

        @JsonProperty("forks_url")
        private String forksUrl;

        @JsonProperty("keys_url")
        private String keysUrl;

        @JsonProperty("collaborators_url")
        private String collaboratorsUrl;

        @JsonProperty("teams_url")
        private String teamsUrl;

        @JsonProperty("hooks_url")
        private String hooksUrl;

        @JsonProperty("issue_events_url")
        private String issueEventsUrl;

        @JsonProperty("events_url")
        private String eventsUrl;

        @JsonProperty("assignees_url")
        private String assigneesUrl;

        @JsonProperty("branches_url")
        private String branchesUrl;

        @JsonProperty("tags_url")
        private String tagsUrl;

        @JsonProperty("blobs_url")
        private String blobsUrl;

        @JsonProperty("git_tags_url")
        private String gitTagsUrl;

        @JsonProperty("git_refs_url")
        private String gitRefsUrl;

        @JsonProperty("trees_url")
        private String treesUrl;

        @JsonProperty("statuses_url")
        private String statusesUrl;

        @JsonProperty("languages_url")
        private String languagesUrl;

        @JsonProperty("stargazers_url")
        private String stargazersUrl;

        @JsonProperty("contributors_url")
        private String contributorsUrl;

        @JsonProperty("subscribers_url")
        private String subscribersUrl;

        @JsonProperty("subscription_url")
        private String subscriptionUrl;

        @JsonProperty("commits_url")
        private String commitsUrl;

        @JsonProperty("git_commits_url")
        private String gitCommitsUrl;

        @JsonProperty("comments_url")
        private String commentsUrl;

        @JsonProperty("issue_comment_url")
        private String issueCommentUrl;

        @JsonProperty("contents_url")
        private String contentsUrl;

        @JsonProperty("compare_url")
        private String compareUrl;

        @JsonProperty("merges_url")
        private String mergesUrl;

        @JsonProperty("archive_url")
        private String archiveUrl;

        @JsonProperty("downloads_url")
        private String downloadsUrl;

        @JsonProperty("issues_url")
        private String issuesUrl;

        @JsonProperty("pulls_url")
        private String pullsUrl;

        @JsonProperty("milestones_url")
        private String milestonesUrl;

        @JsonProperty("notifications_url")
        private String notificationsUrl;

        @JsonProperty("labels_url")
        private String labelsUrl;

        @JsonProperty("releases_url")
        private String releasesUrl;

        @JsonProperty("deployments_url")
        private String deploymentsUrl;

        @JsonProperty("created_at")
        private Long createdAt; // Unix timestamp or ISO 8601. Using Long for flexibility with potential null or numeric.

        @JsonProperty("updated_at")
        private String updatedAt; // ISO 8601

        @JsonProperty("pushed_at")
        private Long pushedAt; // Unix timestamp or ISO 8601. Using Long for flexibility.

        @JsonProperty("git_url")
        private String gitUrl;

        @JsonProperty("ssh_url")
        private String sshUrl;

        @JsonProperty("clone_url")
        private String cloneUrl;

        @JsonProperty("svn_url")
        private String svnUrl;

        @JsonProperty("homepage")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String homepage;

        @JsonProperty("size")
        private int size;

        @JsonProperty("stargazers_count")
        private int stargazersCount;

        @JsonProperty("watchers_count")
        private int watchersCount;

        @JsonProperty("language")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String language;

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

        @JsonProperty("forks_count")
        private int forksCount;

        @JsonProperty("mirror_url")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String mirrorUrl;

        @JsonProperty("archived")
        private boolean archived;

        @JsonProperty("disabled")
        private boolean disabled;

        @JsonProperty("open_issues_count")
        private int openIssuesCount;

        @JsonProperty("license")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private License license;

        @JsonProperty("allow_forking")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean allowForking;

        @JsonProperty("is_template")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean isTemplate;

        @JsonProperty("web_commit_signoff_required")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean webCommitSignoffRequired;

        @JsonProperty("topics")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<String> topics;

        @JsonProperty("visibility")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String visibility;

        @JsonProperty("forks")
        private int forks;

        @JsonProperty("open_issues")
        private int openIssues;

        @JsonProperty("watchers")
        private int watchers;

        @JsonProperty("default_branch")
        private String defaultBranch;

        @JsonProperty("master_branch")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String masterBranch;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GitHubUser { // Used for Repository.Owner and Sender and Installation.Account

        @JsonProperty("login")
        private String login;

        @JsonProperty("id")
        private long id;

        @JsonProperty("node_id")
        private String nodeId;

        @JsonProperty("avatar_url")
        private String avatarUrl;

        @JsonProperty("gravatar_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String gravatarId;

        @JsonProperty("url")
        private String url;

        @JsonProperty("html_url")
        private String htmlUrl;

        @JsonProperty("followers_url")
        private String followersUrl;

        @JsonProperty("following_url")
        private String followingUrl;

        @JsonProperty("gists_url")
        private String gistsUrl;

        @JsonProperty("starred_url")
        private String starredUrl;

        @JsonProperty("subscriptions_url")
        private String subscriptionsUrl;

        @JsonProperty("organizations_url")
        private String organizationsUrl;

        @JsonProperty("repos_url")
        private String reposUrl;

        @JsonProperty("events_url")
        private String eventsUrl;

        @JsonProperty("received_events_url")
        private String receivedEventsUrl;

        @JsonProperty("type")
        private String type; // "User" or "Organization"

        @JsonProperty("site_admin")
        private boolean siteAdmin;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class License {

        @JsonProperty("key")
        private String key;

        @JsonProperty("name")
        private String name;

        @JsonProperty("spdx_id")
        private String spdxId;

        @JsonProperty("url")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String url;

        @JsonProperty("node_id")
        private String nodeId;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Commit { // Used for items in PushPayload.commits

        @JsonProperty("id") // SHA
        private String id;

        @JsonProperty("tree_id")
        private String treeId;

        @JsonProperty("distinct")
        private boolean distinct;

        @JsonProperty("message")
        private String message;

        @JsonProperty("timestamp") // ISO 8601
        private String timestamp;

        @JsonProperty("author")
        private CommitUser author;

        @JsonProperty("committer")
        private CommitUser committer;

        @JsonProperty("added")
        private List<String> added;

        @JsonProperty("removed")
        private List<String> removed;

        @JsonProperty("modified")
        private List<String> modified;
    }

    @Data
    @EqualsAndHashCode(callSuper = true) // Important for subclasses
    @ToString(callSuper = true) // Optional, but good practice
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HeadCommit extends Commit { // Extends Commit and adds a URL

        @JsonProperty("url")
        private String url;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommitUser { // Used for Commit.author and Commit.committer

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("username")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String username;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Organization {

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
        private String membersUrl;

        @JsonProperty("public_members_url")
        private String publicMembersUrl;

        @JsonProperty("avatar_url")
        private String avatarUrl;

        @JsonProperty("description")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String description;

        @JsonProperty("name")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String name;

        @JsonProperty("company")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String company;

        @JsonProperty("blog")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String blog;

        @JsonProperty("location")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String location;

        @JsonProperty("email")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String email;

        @JsonProperty("twitter_username")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String twitterUsername;

        @JsonProperty("is_verified")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean isVerified;

        @JsonProperty("has_organization_projects")
        private boolean hasOrganizationProjects;

        @JsonProperty("has_repository_projects")
        private boolean hasRepositoryProjects;

        @JsonProperty("public_repos")
        private int publicRepos;

        @JsonProperty("public_gists")
        private int publicGists;

        @JsonProperty("followers")
        private int followers;

        @JsonProperty("following")
        private int following;

        @JsonProperty("html_url")
        private String htmlUrl;

        @JsonProperty("created_at") // ISO 8601
        private String createdAt;

        @JsonProperty("updated_at") // ISO 8601
        private String updatedAt;

        @JsonProperty("type")
        private String type; // "Organization"

        @JsonProperty("total_private_repos")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer totalPrivateRepos;

        @JsonProperty("owned_private_repos")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer ownedPrivateRepos;

        @JsonProperty("private_gists")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer privateGists;

        @JsonProperty("disk_usage")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer diskUsage;

        @JsonProperty("collaborators")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer collaborators;

        @JsonProperty("billing_email")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String billingEmail;

        @JsonProperty("plan")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Plan plan;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Plan {
            @JsonProperty("name")
            private String name;

            @JsonProperty("space")
            private int space;

            @JsonProperty("private_repos")
            private int privateRepos;

            @JsonProperty("filled_seats")
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private Integer filledSeats;

            @JsonProperty("seats")
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private Integer seats;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
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

        @JsonProperty("description")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String description;

        @JsonProperty("website_url")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String websiteUrl;

        @JsonProperty("html_url")
        private String htmlUrl;

        @JsonProperty("created_at") // ISO 8601
        private String createdAt;

        @JsonProperty("updated_at") // ISO 8601
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String updatedAt;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Installation {

        @JsonProperty("id")
        private long id;

        @JsonProperty("account")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private GitHubUser account; // Can be User or Organization

        @JsonProperty("app_id")
        private long appId;

        @JsonProperty("app_slug")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String appSlug;

        @JsonProperty("target_id")
        private long targetId;

        @JsonProperty("target_type")
        private String targetType; // "User" or "Organization"

        @JsonProperty("permissions")
        private Map<String, String> permissions;

        @JsonProperty("events")
        private List<String> events;

        @JsonProperty("created_at") // ISO 8601
        private String createdAt;

        @JsonProperty("updated_at") // ISO 8601
        private String updatedAt;

        @JsonProperty("single_file_name")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String singleFileName;

        @JsonProperty("has_multiple_single_files")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean hasMultipleSingleFiles;

        @JsonProperty("single_file_paths")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<String> singleFilePaths;

        @JsonProperty("repository_selection")
        private String repositorySelection; // "all" or "selected"

        @JsonProperty("repositories_url")
        private String repositoriesUrl;

        @JsonProperty("html_url")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String htmlUrl;

        @JsonProperty("suspended_by")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private GitHubUser suspendedBy;

        @JsonProperty("suspended_at")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String suspendedAt; // ISO 8601
    }
}
