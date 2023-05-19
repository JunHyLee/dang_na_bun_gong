package com.example.dang_na_bun_gong.Controller;

import com.example.dang_na_bun_gong.DTO.ArticleDto;
import com.example.dang_na_bun_gong.DTO.MemberDto;
import com.example.dang_na_bun_gong.DTO.MyPageDto;
import com.example.dang_na_bun_gong.DTO.MyPageMemberDto;
import com.example.dang_na_bun_gong.Entity.MemberEntity;
import com.example.dang_na_bun_gong.Service.MyPageService;
import com.example.dang_na_bun_gong.Vo.ResultVo;
import io.swagger.models.auth.In;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MyPageController {

    @Autowired
    private MyPageService myPageService;


    // 마이페이지 메인
    @GetMapping("/myPage")
    public @ResponseBody ResultVo myPage(HttpSession httpSession){

        String memberid = "hi123"; //httpSession.getAttribute("memberid").toString(); // 세션에서 memberid 가져오기

        List<MemberDto> myPageMemberInfo = myPageService.myPage_memberInfo(memberid);
        List<ArticleDto> myPageSell = myPageService.myPage_sellList(memberid);
        List<ArticleDto> myPageBuy = myPageService.myPage_buyList(memberid);
        List<ArticleDto> myPageLike = myPageService.myPage_likeList(memberid);

        JSONObject jsonObject = new JSONObject();

        List<MyPageMemberDto> myPageMemberDto = new ArrayList<>();

        MyPageMemberDto data1 = new MyPageMemberDto(myPageMemberInfo.get(0)); // 프로필, 닉네임, 사진경로
        myPageMemberDto.add(data1);
        jsonObject.put("memberInfo", myPageMemberDto);
        myPageMemberDto.clear();

        List<MyPageDto> myPageDto = new ArrayList<>();

        for(int i = 0; i<myPageSell.size(); i++){  //구매목록 (상위 5개)
            MyPageDto data = new MyPageDto(myPageBuy.get(i));
            myPageDto.add(data);
        }
        jsonObject.put("buyList", myPageDto);
        myPageDto.clear();

        for(int i = 0; i< myPageBuy.size(); i++){ //판매목록 상위(5개)
           MyPageDto data = new MyPageDto(myPageSell.get(i));
           myPageDto.add(data);
        }
        jsonObject.put("sellList", myPageDto);
        myPageDto.clear();

        for(int i = 0; i< myPageLike.size(); i++){ //찜 목록 상위(5개)
            MyPageDto data = new MyPageDto(myPageLike.get(i));
            myPageDto.add(data);
        }
        jsonObject.put("likeList", myPageDto);
        myPageDto.clear();

        //거래후기 목록
        JSONObject review_count = new JSONObject();

        List<ArticleDto> myPageReview5 = myPageService.myPage_reviewCount(memberid, "거래 상품 상태가 좋아요.");
        MyPageDto data6 = new MyPageDto(myPageReview5.get(0));
        myPageDto.add(data6);
        review_count.put("5",myPageDto);
        myPageDto.clear();

        List<ArticleDto> myPageReview4 = myPageService.myPage_reviewCount(memberid, "채팅 답변이 빨라요.");
        MyPageDto data5 = new MyPageDto(myPageReview4.get(0));
        myPageDto.add(data5);
        review_count.put("4",myPageDto);
        myPageDto.clear();

        List<ArticleDto> myPageReview3 = myPageService.myPage_reviewCount(memberid,"약속시간을 잘지켜요.");
        MyPageDto data4 = new MyPageDto(myPageReview3.get(0));
        myPageDto.add(data4);
        review_count.put("3",myPageDto);
        myPageDto.clear();

        List<ArticleDto> myPageReview2 = myPageService.myPage_reviewCount(memberid,"가격이 착해요.");
        MyPageDto data3 = new MyPageDto(myPageReview2.get(0));
        myPageDto.add(data3);
        review_count.put("2",myPageDto);
        myPageDto.clear();

        List<ArticleDto> myPageReview1 = myPageService.myPage_reviewCount(memberid, "친절해요.");
        MyPageDto data2 = new MyPageDto(myPageReview1.get(0));
        myPageDto.add(data2);
        review_count.put("1",myPageDto);
        myPageDto.clear();

        jsonObject.put("reviewKey", review_count);

        return new ResultVo(0,"true", "success", jsonObject.toString());
        //.replaceAll("\\\\", "")
    }

    // 구매목록 전체보기


    //판매목록 전체보기


    //찜목록 전체보기

    //상대상세페이지
    @GetMapping("/opponentPage")
    public @ResponseBody ResultVo opponentPage(HttpSession httpSession) {
        String article_id = httpSession.getAttribute("article_id").toString(); // 거래 중인 게시글 아이디
        String memberid = myPageService.opponentPage_memberid(article_id);// 게시글 아이디로 회원아이디 골라오기

        List<MemberDto> opponentPageMemberInfo = myPageService.myPage_memberInfo(memberid); //상대 회원정보
        List<ArticleDto> opponentPageSell = myPageService.myPage_sellList(memberid); // 상대 판매목록

        JSONObject jsonObject = new JSONObject();

        List<MyPageMemberDto> myPageMemberDto = new ArrayList<>();

        MyPageMemberDto data1 = new MyPageMemberDto(opponentPageMemberInfo.get(0)); // 프로필, 닉네임, 사진경로
        myPageMemberDto.add(data1);
        jsonObject.put("memberInfo", myPageMemberDto);
        myPageMemberDto.clear();

        List<MyPageDto> myPageDto = new ArrayList<>();

        for (int i = 0; i < opponentPageSell.size(); i++) { //판매목록(상위 5개/Limit 5)
            MyPageDto data = new MyPageDto(opponentPageSell.get(i));
            myPageDto.add(data);
        }
        jsonObject.put("sellList", myPageDto);
        myPageDto.clear();

        //거래후기
        JSONObject review_count = new JSONObject();

        List<ArticleDto> myPageReview5 = myPageService.myPage_reviewCount(memberid, "거래 상품 상태가 좋아요.");
        MyPageDto data6 = new MyPageDto(myPageReview5.get(0));
        myPageDto.add(data6);
        review_count.put("5",myPageDto);
        myPageDto.clear();

        List<ArticleDto> myPageReview4 = myPageService.myPage_reviewCount(memberid, "채팅 답변이 빨라요.");
        MyPageDto data5 = new MyPageDto(myPageReview4.get(0));
        myPageDto.add(data5);
        review_count.put("4",myPageDto);
        myPageDto.clear();

        List<ArticleDto> myPageReview3 = myPageService.myPage_reviewCount(memberid,"약속시간을 잘지켜요.");
        MyPageDto data4 = new MyPageDto(myPageReview3.get(0));
        myPageDto.add(data4);
        review_count.put("3",myPageDto);
        myPageDto.clear();

        List<ArticleDto> myPageReview2 = myPageService.myPage_reviewCount(memberid,"가격이 착해요.");
        MyPageDto data3 = new MyPageDto(myPageReview2.get(0));
        myPageDto.add(data3);
        review_count.put("2",myPageDto);
        myPageDto.clear();

        List<ArticleDto> myPageReview1 = myPageService.myPage_reviewCount(memberid, "친절해요.");
        MyPageDto data2 = new MyPageDto(myPageReview1.get(0));
        myPageDto.add(data2);
        review_count.put("1",myPageDto);
        myPageDto.clear();

        jsonObject.put("reviewKey", review_count);
        return new ResultVo(0,"true", "success", jsonObject.toString()); //.replaceAll("\\\\", "")

    }


}
