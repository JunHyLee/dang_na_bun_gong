package com.example.dang_na_bun_gong.Service;

import com.example.dang_na_bun_gong.DTO.ArticleDto;
import com.example.dang_na_bun_gong.DTO.MemberDto;
import com.example.dang_na_bun_gong.DTO.MyPageMemberDto;
import com.example.dang_na_bun_gong.Entity.MemberEntity;
import com.example.dang_na_bun_gong.Repository.MyPageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {

    @Autowired
    private MyPageRepository myPageRepository;

    public List<ArticleDto> myPage_sellList(String memberid){
        return myPageRepository.sellList(memberid);
    }

    public List<ArticleDto> myPage_buyList(String memberid){
        return myPageRepository.buyList(memberid);
    }

    public List<MemberDto> myPage_memberInfo(String memberid){
        return myPageRepository.memberInfo(memberid);
    }

    public List<ArticleDto> myPage_likeList(String memberid){
        return myPageRepository.likeList(memberid);
    }

    public List<ArticleDto> myPage_reviewCount(String memberid, String review){
        return myPageRepository.reviewCount(memberid, review);
    }

    public String opponentPage_memberid(String articleId){
        return myPageRepository.getmemberid(articleId);
    }
}
