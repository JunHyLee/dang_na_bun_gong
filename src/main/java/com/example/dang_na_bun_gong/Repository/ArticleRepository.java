package com.example.dang_na_bun_gong.Repository;

import com.example.dang_na_bun_gong.DTO.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	//main 페이지 최신순
	@Query(value = "SELECT article_id, article_title, article_content_fp, price, article.region_id, region.region_name FROM article join region on article.region_id = region.region_id ORDER BY article_created DESC LIMIT 15", nativeQuery = true)
	List<ArticleDto> currentArticle();

	//main 페이지 인기순 (좋아요 갯수로 정렬)
	@Query(value = "SELECT article_id, article_title, article_content_fp, price, article.region_id, region.region_name FROM article join region on article.region_id = region.region_id ORDER BY like_cnt DESC LIMIT 15", nativeQuery = true)
	List<ArticleDto> popularArticle();

	//게시글 리스트 전체 출력 (카테고리 미선택)
	@Query(value = "Select article_id, photo_fp, article_title, price From article Where region_id = :regionid Order by article_created DESC", countQuery ="SELECT COUNT(*) FROM article Where article.region_id = :regionid", nativeQuery = true)
	Page<ArticleDto> articleListAll(Pageable pageable, Integer regionid);

	//게시글 리스트 카테고리별 출력 (선택한 카테고리만 출력)
	@Query(value = "Select article_id, photo_fp, article_title, price From article Where region_id = :regionid and product_category_id = :pcategoryid Order by article_created DESC", countQuery ="SELECT COUNT(*) FROM article Where article.region_id = :regionid and article.product_category_id = :pcategoryid", nativeQuery = true)
	Page<ArticleDto> articleList(Pageable pageable, Integer regionid, Integer pcategoryid);

}