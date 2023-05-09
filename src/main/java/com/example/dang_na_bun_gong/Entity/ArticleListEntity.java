package com.example.dang_na_bun_gong.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "articleList")
@Table(name = "article")
@Data
@NoArgsConstructor
public class ArticleListEntity {

    @Id
    @Column(name="article_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer articleid;

    @Column(name="article_title", nullable = false)
    private String articletitle;

    @Column(name="photo_fp")
    private String photofp;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name="product_category_id")
    private Integer pcategoryid;

    @Column(name="region_id")
    private Integer regionid;

}

