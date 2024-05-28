package com.example.dlarticle.api.controller;

import com.example.dlarticle.api.service.CommentService;
import com.example.dlarticle.common.response.JsonResult;
import com.example.dlarticle.domain.comment.Comment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 특정 글의 댓글 리스트 조회
    @GetMapping("/article/{articleId}/comment")
    public JsonResult<?> getComments(@PathVariable Long articleId) {

        return JsonResult.successOf(commentService.getCommentsByArticleId(articleId));
    }

    // 특정 글의 댓글 등록
    @PostMapping("/article/{articleId}/comment")
    public JsonResult<?> createComment(@PathVariable Long articleId,
                                       @RequestParam Long memberId,
                                       @Valid @RequestParam String content) {

        return JsonResult.successOf(commentService.createComment(articleId, memberId, content));
    }

    // 특정 글의 댓글 삭제
    @PostMapping("/comment/delete/{commentId}")
    public JsonResult<?> deleteComment(@PathVariable Long articleId,
                                       @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return JsonResult.successOf("댓글 삭제가 완료되었습니다.");
    }
}
