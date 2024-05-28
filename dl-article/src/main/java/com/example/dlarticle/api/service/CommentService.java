package com.example.dlarticle.api.service;

import com.example.dlarticle.api.dto.CommentResponse;
import com.example.dlarticle.domain.comment.Comment;
import com.example.dlarticle.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentResponse> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId).stream()
                .map(CommentResponse::of)
                .toList();
    }

    @Transactional
    public CommentResponse createComment(Long articleId, Long memberId, String content) {

        Comment comment = commentRepository.save(Comment.builder()
                .articleId(articleId)
                .memberId(memberId)
                .content(content)
                .build());

        return CommentResponse.of(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
