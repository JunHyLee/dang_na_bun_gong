package com.example.dang_na_bun_gong.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleListDto {
    private String photo_fp, price, article_title;
    private Integer article_id;

    public ArticleListDto (ArticleDto articleDto){
        this.article_id = articleDto.getArticle_id();
        this.photo_fp = articleDto.getPhoto_fp();
        this.article_title = articleDto.getArticle_title();
        this.price = articleDto.getPrice();
    }


}
