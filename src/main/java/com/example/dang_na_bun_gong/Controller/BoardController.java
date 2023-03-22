package com.example.dang_na_bun_gong.Controller;

import java.util.ArrayList;
import java.util.List;

import com.example.dang_na_bun_gong.DTO.ArticleDto;
import com.example.dang_na_bun_gong.DTO.MainDto;
import com.example.dang_na_bun_gong.Entity.BoardEntity;
import com.example.dang_na_bun_gong.Vo.ResultVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.dang_na_bun_gong.Service.BoardService;

import javax.servlet.http.HttpSession;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //main 페이지
    @GetMapping("/main")
    public @ResponseBody ResultVo main(){
        List<ArticleDto> mainCurrent = boardService.mainPage_current();
        List<ArticleDto> mainPopular = boardService.mainPage_popular();

        JSONObject jsonObject = new JSONObject();
        List<MainDto> mainDto = new ArrayList<>();

        for (int i=0; i<mainCurrent.size(); i++){
            MainDto data = new MainDto(mainCurrent.get(i));
            mainDto.add(data);
        }
        jsonObject.put("current", mainDto);
        mainDto.clear();

        for (int i=0; i<mainPopular.size(); i++){
            MainDto data = new MainDto(mainPopular.get(i));
            mainDto.add(data);
        }
        jsonObject.put("popular", mainDto);
        mainDto.clear();

        return new ResultVo(0, "true", "불러오기 성공", jsonObject.toString());
    }


	//게시물 리스트 보기 (실패)
    @GetMapping("/articleList")
    public @ResponseBody ResultVo articleList(
            @PageableDefault(page = 0, size = 5)Pageable pageable, HttpSession httpSession) {

        Integer regionid = 23080;
        Page<BoardEntity> articlelist = boardService.articleListAll(pageable, regionid);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ArticleList", articlelist.toString());

        return new ResultVo(0, "ture", "이게 안돼?", jsonObject.toString());
    }

}
