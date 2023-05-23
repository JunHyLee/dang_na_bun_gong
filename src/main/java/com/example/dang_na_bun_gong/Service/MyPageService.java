package com.example.dang_na_bun_gong.Service;

import com.example.dang_na_bun_gong.DTO.ArticleDto;
import com.example.dang_na_bun_gong.DTO.MemberDto;

import com.example.dang_na_bun_gong.Repository.ArticleRepository;
import com.example.dang_na_bun_gong.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {

    @Autowired
    private ArticleRepository articleRepository;
    private MemberRepository memberRepository;

    public List<ArticleDto> myPage_sellList(String memberid){
        return articleRepository.sellList(memberid);
    }

    public List<ArticleDto> myPage_buyList(String memberid){
        return articleRepository.buyList(memberid);
    }

    public List<MemberDto> myPage_memberInfo(String memberid){
        return memberRepository.memberInfo(memberid);
    }

    public List<ArticleDto> myPage_likeList(String memberid){
        return articleRepository.likeList(memberid);
    }

    public List<ArticleDto> myPage_reviewCount(String memberid, String review_key){
        return articleRepository.reviewCount(memberid, review_key);
    }

    public String opponentPage_memberid(String articleId){
        return articleRepository.getmemberid(articleId);
    }
}
