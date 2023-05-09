package com.example.dang_na_bun_gong.Repository;

import com.example.dang_na_bun_gong.Entity.ArticleListAllEntity;
import com.example.dang_na_bun_gong.Entity.ArticleListEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleListRepository extends JpaRepository<ArticleListEntity, Integer>, JpaSpecificationExecutor<ArticleListEntity> {

    @Query(value = "Select article_id, photo_fp, article_title, price From article Where article.region_id = :regionid and article.product_category_id = :pcategoryid Order by article_created DESC", countQuery ="SELECT COUNT(*) FROM article Where article.region_id = :regionid", nativeQuery = true)
    Page<ArticleListEntity> articleList(Pageable pageable, Integer regionid, Integer pcategoryid);
}
