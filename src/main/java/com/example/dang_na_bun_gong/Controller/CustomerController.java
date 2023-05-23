package com.example.dang_na_bun_gong.Controller;

import com.example.dang_na_bun_gong.DTO.*;
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
    public @ResponseBody ResultVo CustomerMainPage() {
        List<QuestCommenDto> questCommenListCurrent = customerService.questCommenListCurrent();
        JSONObject jsonObject = new JSONObject();
        List<QuestCommenListDto> questCommenListDto = new ArrayList<>();

        for(int i=0;i<questCommenListCurrent.size();i++) {
            QuestCommenListDto data = new QuestCommenListDto((questCommenListCurrent.get(i)));
            questCommenListDto.add(data);
        }
        jsonObject.put("QuestCommenListCurrent", questCommenListDto);
        questCommenListDto.clear();

        return new ResultVo(0, "ture", "자주하는 질문 상위 5개 출력", jsonObject.toString());
    }

    //공지사항 목록
    @GetMapping("/notionList")   // /notionList?page=숫자
    public @ResponseBody ResultVo notionList(@PageableDefault(size = 5, sort = "notionid", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<NotionEntity> notionList = customerService.notionList(pageable);
        List<NotionEntity> content = notionList.getContent();
        JSONObject jsonObject = new JSONObject();

        if (pageable.getPageNumber() + 1 > notionList.getTotalPages()) {
            return new ResultVo(309, "false", "마지막 페이지를 초과하였습니다.");
        } else {

            jsonObject.put("nowPage", pageable.getPageNumber() + 1);   //이건 0부터 시작해서 +1 해줌
            jsonObject.put("endPage", notionList.getTotalPages());  //이건 1부터 시작
            jsonObject.put("NotionList", content);

            return new ResultVo(0, "true", "공지사항 목록 출력", jsonObject.toString());
        }
    }

    // 1대1 질문 작성 페이지


    // 내 질문 목록
    @GetMapping("/questPrivateList") //   /questPrivateList?page=숫자
    public @ResponseBody ResultVo questPivateList(@PageableDefault(size = 5, sort = "quest_id", direction = Sort.Direction.DESC) Pageable pageable, HttpSession httpSession) {

        String questioner_id = "hi123";//httpSession.getAttribute("memberid").toString();
        Integer selected_quest_category_id = (Integer) httpSession.getAttribute("quest_category_id");
        List<QuestPrivateListDto> questPrivateListDto = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        if (questioner_id == null) {
            return new ResultVo(289, "false", "비로그인입니다.");
        } else {
            if (selected_quest_category_id == null) {
                Page<QuestPrivateDto> questPrivateListAll = customerService.questPrivateListAll(pageable, questioner_id);
                Integer lastPageCnt = Math.toIntExact(questPrivateListAll.getTotalElements() - pageable.getOffset());

                if (pageable.getPageNumber() + 1 > questPrivateListAll.getTotalPages()) {
                    return new ResultVo(309, "false", "마지막 페이지를 초과하였습니다.");
                } else {
                    if (pageable.getPageNumber() == (questPrivateListAll.getTotalPages() - 1)) {
                        for (int i = 0; i < lastPageCnt; i++) {
                            QuestPrivateListDto data = new QuestPrivateListDto(questPrivateListAll.getContent().get(i));
                            questPrivateListDto.add(data);
                        }
                    } else {
                        for (int i = 0; i < questPrivateListAll.getSize(); i++) {
                            QuestPrivateListDto data = new QuestPrivateListDto(questPrivateListAll.getContent().get(i));
                            questPrivateListDto.add(data);
                        }
                    }
                    jsonObject.put("nowPage", pageable.getPageNumber() + 1);   //이건 0부터 시작해서 +1 해줌
                    jsonObject.put("endPage", questPrivateListAll.getTotalPages());  //이건 1부터 시작
                    jsonObject.put("QuestPrivateList", questPrivateListDto);

                    return new ResultVo(0, "true", "내 질문 목록 출력(카테고리 미입력)", jsonObject.toString());
                }
            } else {
                Page<QuestPrivateDto> questPrivateList = customerService.questPrivateList(pageable, questioner_id, selected_quest_category_id);
                Integer lastPageCnt = Math.toIntExact(questPrivateList.getTotalElements() - pageable.getOffset());

                if (pageable.getPageNumber() + 1 > questPrivateList.getTotalPages()) {
                    return new ResultVo(309, "false", "마지막 페이지를 초과하였습니다.");
                } else {
                    if (pageable.getPageNumber() == (questPrivateList.getTotalPages() - 1)) {
                        for (int i = 0; i < lastPageCnt; i++) {
                            QuestPrivateListDto data = new QuestPrivateListDto(questPrivateList.getContent().get(i));
                            questPrivateListDto.add(data);
                        }
                    } else {
                        for (int i = 0; i < questPrivateList.getSize(); i++) {
                            QuestPrivateListDto data = new QuestPrivateListDto(questPrivateList.getContent().get(i));
                            questPrivateListDto.add(data);
                        }
                    }

                    jsonObject.put("nowPage", pageable.getPageNumber() + 1);   //이건 0부터 시작해서 +1 해줌
                    jsonObject.put("endPage", questPrivateList.getTotalPages());  //이건 1부터 시작
                    jsonObject.put("QuestPrivateList", questPrivateListDto);

                    return new ResultVo(1, "true", "내 질문 목록 출력(카테고리 입력)", jsonObject.toString());
                }
            }
        }
    }


    // 자주하는 질문 목록
    @GetMapping("/questCommenList")
    public @ResponseBody ResultVo questCommenList(@PageableDefault(size = 5, sort = "quest_id", direction = Sort.Direction.DESC) Pageable pageable, HttpSession httpSession){

        Integer selected_quest_category_id = 1;//(Integer) httpSession.getAttribute("quest_category_id");
        JSONObject jsonObject = new JSONObject();
        List<QuestCommenListDto> questCommenListDto = new ArrayList<>();

        if(selected_quest_category_id == null) {
            Page<QuestCommenDto> questCommenListAll = customerService.questCommenListAll(pageable);
            Integer lastPageCnt = Math.toIntExact(questCommenListAll.getTotalElements() - pageable.getOffset());

            if (pageable.getPageNumber() + 1 > questCommenListAll.getTotalPages()) {
                return new ResultVo(309, "false", "마지막 페이지를 초과하였습니다.");
            } else {
                if (pageable.getPageNumber() == (questCommenListAll.getTotalPages() - 1)) {
                    for (int i = 0; i < lastPageCnt; i++) {
                        QuestCommenListDto data = new QuestCommenListDto(questCommenListAll.getContent().get(i));
                        questCommenListDto.add(data);
                    }
                } else {
                    for (int i = 0; i < questCommenListAll.getSize(); i++) {
                        QuestCommenListDto data = new QuestCommenListDto(questCommenListAll.getContent().get(i));
                        questCommenListDto.add(data);
                    }
                }

                jsonObject.put("nowPage", pageable.getPageNumber() + 1);   //이건 0부터 시작해서 +1 해줌
                jsonObject.put("endPage", questCommenListAll.getTotalPages());  //이건 1부터 시작
                jsonObject.put("QuestCommenList", questCommenListDto);

                return new ResultVo(0, "true", "자주하는 질문 목록 출력(카테고리 미입력)", jsonObject.toString());
            }
        } else {
            Page<QuestCommenDto> questCommenList = customerService.questCommenList(pageable, selected_quest_category_id);
            Integer lastPageCnt = Math.toIntExact(questCommenList.getTotalElements() - pageable.getOffset());

            if (pageable.getPageNumber() + 1 > questCommenList.getTotalPages()) {
                return new ResultVo(309, "false", "마지막 페이지를 초과하였습니다.");
            } else {
                if (pageable.getPageNumber() == (questCommenList.getTotalPages() - 1)) {
                    for (int i = 0; i < lastPageCnt; i++) {
                        QuestCommenListDto data = new QuestCommenListDto(questCommenList.getContent().get(i));
                        questCommenListDto.add(data);
                    }
                } else {
                    for (int i = 0; i < questCommenList.getSize(); i++) {
                        QuestCommenListDto data = new QuestCommenListDto(questCommenList.getContent().get(i));
                        questCommenListDto.add(data);
                    }
                }

                jsonObject.put("nowPage", pageable.getPageNumber() + 1);   //이건 0부터 시작해서 +1 해줌
                jsonObject.put("endPage", questCommenList.getTotalPages());  //이건 1부터 시작
                jsonObject.put("QuestCommenList", questCommenListDto);

                return new ResultVo(0, "true", "자주하는 질문 목록 출력(카테고리 입력)", jsonObject.toString());
            }

        }
    }


    // 질문 및 답변 상세보기


}
