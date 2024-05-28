package com.example.dlarticle.api.service;

import com.example.dlarticle.IntegrationHelper;
import com.example.dlarticle.api.dto.CommentResponse;
import com.example.dlarticle.domain.article.Article;
import com.example.dlarticle.domain.article.constant.ArticleCategory;
import com.example.dlarticle.domain.article.repository.ArticleRepository;
import com.example.dlarticle.domain.comment.Comment;
import com.example.dlarticle.domain.comment.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.dlarticle.IntegrationHelper.NON_ASCII;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings(NON_ASCII)
class CommentIntegrationTest extends IntegrationHelper {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        Article article = articleRepository.save(
                Article.builder()
                        .memberId(1L)
                        .title("Test Article")
                        .detail("Test Detail")
                        .category(ArticleCategory.DIARY)
                        .build()
        );

        commentRepository.saveAll(List.of(
                Comment.builder().articleId(article.getId()).memberId(1L).content("Comment 1").build(),
                Comment.builder().articleId(article.getId()).memberId(1L).content("Comment 2").build()
        ));
    }

    @Test
    void 댓글_리스트_조회() {
        // given
        Article article = articleRepository.findByTitle("Test Article").get();

        // when
        List<CommentResponse> commentList = commentService.getCommentsByArticleId(article.getId());

        // then
        assertNotNull(commentList);
        assertEquals(2, commentList.size());
    }

    @Test
    void 댓글_등록() {
        // given
        Article article = articleRepository.findByTitle("Test Article").get();
        String content = "New Comment";

        // when
        CommentResponse comment = commentService.createComment(article.getId(), article.getMemberId(), content);

        // then
        assertNotNull(comment);
        assertEquals(content, comment.content());
    }

    @Test
    void 댓글_삭제() {
        // given
        Comment comment = commentRepository.findAll().get(0);
        Long commentId = comment.getId();

        // when
        commentService.deleteComment(commentId);

        // then
        assertFalse(commentRepository.existsById(commentId));
    }
}
