package com.example.dang_na_bun_gong.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageDto {

    String photo_fp, member_id, sell_member_id, buy_member_id, review_key;
    Integer article_id, like;
    Long count;

    public MyPageDto(ArticleDto articleDto){
        this.article_id = articleDto.getArticle_id();
        this.member_id  = articleDto.getMember_id();
        this.sell_member_id = articleDto.getSell_member_id();
        this.buy_member_id = articleDto.getBuy_member_id();
        this.photo_fp = articleDto.getPhoto_fp();
        this.review_key = articleDto.getReview_key();
        this.count = articleDto.getCount();
    }


}
