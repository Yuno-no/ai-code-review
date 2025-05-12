package com.kira.ai.codereview.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kira.ai.codereview.common.constants.ResultCode;
import com.kira.ai.codereview.common.model.R;
import com.kira.ai.codereview.dto.PushPayload;
import com.kira.ai.codereview.service.CodeReviewService;
import com.kira.ai.codereview.service.GitHubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author: kira
 * @date: 2025/04/22 14:50
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class WebHookController {

    private final GitHubService gitHubService;
    private final CodeReviewService codeReviewService;
    private final ObjectMapper objectMapper;

    @PostMapping("/webhook")
    public R<String> handleWebHook(
            @RequestHeader("X-Hub-Signature-256") String signature,
            @RequestHeader("X-GitHub-Event") String event,
            @RequestBody String payloadRaw) throws JsonProcessingException {
        log.debug("Received webhook event: {}", event);
        log.trace("Payload: {}", payloadRaw);
        log.trace("Signature: {}", signature);

        if (!"push".equals(event)) {
            return R.ok("Ignoring event: " + event);
        }

        // 校验 signature
        if (!gitHubService.isValidSignature(payloadRaw, signature)) {
            return R.fail(ResultCode.ValidateError,"Invalid signature");
        }

        PushPayload pushPayload = objectMapper.readValue(payloadRaw, PushPayload.class);
        // 从 payload 中提取 repository name, owner, commit sha
        String repositoryFullName = pushPayload.getRepository().getFullName();
        String commitSha = pushPayload.getHeadCommit().getId();

        codeReviewService.performAutomatedReview(repositoryFullName, commitSha);

        return R.ok(ResultCode.Success.name());
    }
}
