package com.kira.ai.codereview.controller;

import com.kira.ai.codereview.comcmon.model.R;
import com.kira.ai.codereview.service.CodeReviewService;
import com.kira.ai.codereview.service.GitHubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.PagedIterable;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;

/**
 * @author: kira
 * @date: 2025/04/22 14:50
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class WebHookController {

    private final GitHubService gitHubService;

    @Autowired
    OpenAiChatModel chatModel;

    @Autowired
    CodeReviewService codeReviewService;

    @PostMapping("/webhook")
    public R<String> handleWebHook(
            @RequestHeader("X-Hub-Signature-256") String signature,
            @RequestHeader("X-GitHub-Event") String event,
            @RequestBody String payloadRaw) {
        log.debug("Received webhook event: {}", event);
        log.trace("Payload: {}", payloadRaw);
        log.trace("Signature: {}", signature);

        return R.ok();
    }

    @GetMapping("/displayCurrentUserInfo")
    public R<String> displayCurrentUserInfo() throws IOException {
        gitHubService.displayCurrentUserInfo();
        return R.ok();
    }

    @GetMapping("/getCommit")
    public R<String> getCommit() throws IOException {
        GHCommit commit = gitHubService.getCommit("Yuno-no/ai-code-review", "33559e3");
        PagedIterable<GHCommit.File> commitFiles = gitHubService.getCommitFiles(commit);
        codeReviewService.performAutomatedReview("Yuno-no/ai-code-review", "33559e3");
        return R.ok();
    }

    @GetMapping("ai/chat")
    public R<String> chat(@RequestParam(value = "message", defaultValue = "你好") String message) {
        ChatResponse response = chatModel.call(
                new Prompt(
                        new UserMessage(message)
                ));

        return R.ok(response.getResult().getOutput().getText());
    }

    @GetMapping(value = "/ai/generateStream", produces = "text/html;charset=utf-8")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "生成小众有灵感的昵称") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt).map(r -> r.getResult().getOutput().getText());
    }
}
