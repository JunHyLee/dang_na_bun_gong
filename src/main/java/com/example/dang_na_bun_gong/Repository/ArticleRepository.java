package com.example.dang_na_bun_gong.Repository;

import com.example.dang_na_bun_gong.DTO.ArticleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dang_na_bun_gong.Entity.ArticleEntity;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer>, JpaSpecificationExecutor<ArticleEntity> {
	// 키워드 검색
	// Page<BoardEntity> findByTitleContaining(String searchKeyword, Pageable pageable);
	@Modifying
	@Query("UPDATE article p SET p.likecnt = p.likecnt + 1 WHERE p.articleid = :postId")
	void increaseLikeCnt(@Param("postId") Integer postId);

	@Modifying
	@Query("UPDATE article p SET p.likecnt = p.likecnt - 1 WHERE p.articleid = :postId")
	void decreaseLikeCnt(@Param("postId") Integer postId);

	@Query(value = "SELECT article_id, article_title, article_content_fp, price, article.region_id, region.region_name FROM article join region on article.region_id = region.region_id ORDER BY article_created DESC LIMIT 15", nativeQuery = true)
	List<ArticleDto> currentArticle();

	@Query(value = "SELECT article_id, article_title, article_content_fp, price, article.region_id, region.region_name FROM article join region on article.region_id = region.region_id ORDER BY like_cnt DESC LIMIT 15", nativeQuery = true)
	List<ArticleDto> popularArticle();

}