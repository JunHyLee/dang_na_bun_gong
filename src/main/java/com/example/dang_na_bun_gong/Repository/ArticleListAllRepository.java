package com.example.dang_na_bun_gong.Repository;

import com.example.dang_na_bun_gong.Entity.ArticleListAllEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleListAllRepository extends JpaRepository<ArticleListAllEntity, Integer>, JpaSpecificationExecutor<ArticleListAllEntity> {

    @Query(value = "Select article_id, photo_fp, article_title, price From article Where region_id = :regionid Order by article_created DESC", countQuery ="SELECT COUNT(*) FROM article Where region_id = :regionid", nativeQuery = true)
    Page<ArticleListAllEntity> articleListAll(Pageable pageable, Integer regionid);
    // \n — #pageable\n
}
