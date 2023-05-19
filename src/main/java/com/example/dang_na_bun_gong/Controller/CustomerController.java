package com.example.dang_na_bun_gong.Controller;

import com.example.dang_na_bun_gong.DTO.MemberDto;
import com.example.dang_na_bun_gong.DTO.MyPageMemberDto;
import com.example.dang_na_bun_gong.Entity.NotionEntity;
import com.example.dang_na_bun_gong.Service.CustomerService;
import com.example.dang_na_bun_gong.Service.MyPageService;
import com.example.dang_na_bun_gong.Vo.ResultVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    MyPageService myPageService;
    @Autowired
    CustomerService customerService;

    //고객센터 메인페이지 (재작성 필요)
    @GetMapping("/customerMain")
    public @ResponseBody ResultVo CustomerMainPage(HttpSession httpSession) {
        String memberid = httpSession.getAttribute("memberid").toString(); // 세션에서 memberid 가져오기  // test_id = hi123
        if (memberid == null) {
            return new ResultVo(101, "false", "회원정보가 없습니다.");
        } else {
            List<MemberDto> myPageMemberInfo = myPageService.myPage_memberInfo(memberid);
            JSONObject jsonObject = new JSONObject();

            List<MyPageMemberDto> myPageMemberDto = new ArrayList<>();

            MyPageMemberDto data1 = new MyPageMemberDto(myPageMemberInfo.get(0)); // 프로필, 닉네임, 사진경로
            myPageMemberDto.add(data1);
            jsonObject.put("memberInfo", myPageMemberDto);
            myPageMemberDto.clear();

            return new ResultVo(0, "ture", "고객센터 메인페이지 회원정보 출력", jsonObject.toString());
        }
    }

    //공지사항 목록
    @GetMapping("/notionList")   // /notionList?page=숫자
    public @ResponseBody ResultVo notionList(@PageableDefault(size = 5, sort = "notionid", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<NotionEntity> notionList = customerService.notionList(pageable);
        List<NotionEntity> content = notionList.getContent();

        if (pageable.getPageNumber() + 1 > notionList.getTotalPages()) {
            return new ResultVo(309, "false", "마지막 페이지를 초과하였습니다.");
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("NotionList", content);

            return new ResultVo(0, "true", "공지사항 목록 출력", jsonObject.toString());
        }
    }

    // 1대1 질문 작성 페이지


    // 내 문의 내역


    // 자주하는 질문 목록


    // 질문 및 답변 상세보기


}
