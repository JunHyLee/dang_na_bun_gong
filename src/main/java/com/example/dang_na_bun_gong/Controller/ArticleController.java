package com.example.dang_na_bun_gong.Controller;

import com.example.dang_na_bun_gong.DTO.ArticleDto;
import com.example.dang_na_bun_gong.DTO.ArticleListDto;
import com.example.dang_na_bun_gong.DTO.ArticleWriteDto;
import com.example.dang_na_bun_gong.DTO.MainDto;
import com.example.dang_na_bun_gong.Entity.ArticleEntity;
import com.example.dang_na_bun_gong.Entity.CategoryEntity;
import com.example.dang_na_bun_gong.Entity.RegionEntity;
import com.example.dang_na_bun_gong.Service.ArticleService;
import com.example.dang_na_bun_gong.Service.BookMarkSerivce;
import com.example.dang_na_bun_gong.Vo.ResultVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private BookMarkSerivce bookMarkSerivce;

    //main 페이지
    @GetMapping("/main")
    public @ResponseBody ResultVo main(){
        List<ArticleDto> mainCurrent = articleService.mainPage_current();
        List<ArticleDto> mainPopular = articleService.mainPage_popular();
        List<CategoryEntity> mainCategory = articleService.mainPage_product_category();
        List<RegionEntity> mainRegion = articleService.mainPage_region();


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

        jsonObject.put("productCategory", mainCategory);
        jsonObject.put("region", mainRegion);


        return new ResultVo(0, "true", "불러오기 성공", jsonObject.toString());
    }

    //게시물 리스트 보기(비로그인상태)                     //로그인 상태의 게시물 리스트와 지역코드만 제외
    @GetMapping("/articleListNoLogin")  //:8080/articleListNoLogin?page=(숫자)
    public @ResponseBody ResultVo articleListNoLogin(
            @PageableDefault(size = 5) Pageable pageable, HttpSession httpSession) {

        Integer pcategoryid = (Integer) httpSession.getAttribute("pcategoryid"); //test id = 64

        JSONObject jsonObject = new JSONObject();
        List<ArticleListDto> articleListDto = new ArrayList<>();

        //카테고리가 선택되지 않았을 때 (전체 출력)
        if (pcategoryid == null) {
            Page<ArticleDto> articlelistAllNoLogin = articleService.articleListAllNoLogin(pageable);
            Integer lastPageCnt = Math.toIntExact(articlelistAllNoLogin.getTotalElements() - pageable.getOffset()); //마지막 페이지 index overflow 일으키지 않기 위해 따로 계산

            if (pageable.getPageNumber() + 1 > articlelistAllNoLogin.getTotalPages()) {
                return new ResultVo(309, "false", "마지막 페이지를 초과하였습니다.");
            } else {
                if (pageable.getPageNumber() == (articlelistAllNoLogin.getTotalPages() - 1)) {
                    for (int i = 0; i < lastPageCnt; i++) {
                        ArticleListDto data = new ArticleListDto(articlelistAllNoLogin.getContent().get(i));
                        articleListDto.add(data);
                    }
                } else {
                    for (int i = 0; i < articlelistAllNoLogin.getSize(); i++) {
                        ArticleListDto data = new ArticleListDto(articlelistAllNoLogin.getContent().get(i));
                        articleListDto.add(data);
                    }
                }

                jsonObject.put("nowPage", pageable.getPageNumber() + 1);   //이건 0부터 시작해서 +1 해줌
                jsonObject.put("endPage", articlelistAllNoLogin.getTotalPages());  //이건 1부터 시작
                jsonObject.put("ArticleList", articleListDto);
                articleListDto.clear();

                return new ResultVo(0, "ture", "전체 출력(비로그인입니다.)", jsonObject.toString());
            }
        }
        //카테고리가 선택되었을 때
        else {
            Page<ArticleDto> articleListNoLogin = articleService.articleListNoLogin(pageable, pcategoryid);
            Integer lastPageCnt = Math.toIntExact(articleListNoLogin.getTotalElements() - pageable.getOffset());  //마지막 페이지 index overflow 일으키지 않기 위해 따로 계산

            if (pageable.getPageNumber() + 1 > articleListNoLogin.getTotalPages()) {
                return new ResultVo(309, "false", "마지막 페이지를 초과하였습니다.");
            } else {
                if (pageable.getPageNumber() == (articleListNoLogin.getTotalPages() - 1)) {
                    for (int i = 0; i < lastPageCnt; i++) {
                        ArticleListDto data = new ArticleListDto(articleListNoLogin.getContent().get(i));
                        articleListDto.add(data);
                    }
                } else {

                    for (int i = 0; i < articleListNoLogin.getSize(); i++) {
                        ArticleListDto data = new ArticleListDto(articleListNoLogin.getContent().get(i));
                        articleListDto.add(data);
                    }
                }

                jsonObject.put("nowPage", pageable.getPageNumber() + 1);  //이건 0부터 시작해서 +1 해줌
                jsonObject.put("endPage", articleListNoLogin.getTotalPages());   //이건 1부터 시작
                jsonObject.put("ArticleList", articleListDto);
                articleListDto.clear();

                return new ResultVo(1, "ture", "해당 카테고리 출력(비로그인입니다.)", jsonObject.toString());
            }
        }
    }

    //게시물 리스트 보기 (로그인상태)
    @GetMapping("/articleList")             //:8080/articleList?page=(숫자)
    public @ResponseBody ResultVo articleList(
            @PageableDefault(size = 5) Pageable pageable, HttpSession httpSession) {

        Integer regionid = (Integer) httpSession.getAttribute("regionid");  // test id = 23080
        Integer pcategoryid = (Integer) httpSession.getAttribute("pcategoryid"); //test id = 64

        JSONObject jsonObject = new JSONObject();
        List<ArticleListDto> articleListDto = new ArrayList<>();

        //카테고리가 선택되지 않았을 때 (전체 출력)
        if(pcategoryid == null) {
            Page<ArticleDto> articlelistAll = articleService.articleListAll(pageable, regionid);
            Integer lastPageCnt = Math.toIntExact(articlelistAll.getTotalElements() - pageable.getOffset()); //마지막 페이지 index overflow 일으키지 않기 위해 따로 계산

            if (pageable.getPageNumber() + 1 > articlelistAll.getTotalPages()) {
                return new ResultVo(309, "false", "마지막 페이지를 초과하였습니다.");
            } else {
                if (pageable.getPageNumber() == (articlelistAll.getTotalPages() - 1)) {
                    for (int i = 0; i < lastPageCnt; i++) {
                        ArticleListDto data = new ArticleListDto(articlelistAll.getContent().get(i));
                        articleListDto.add(data);
                    }
                } else {
                    for (int i = 0; i < articlelistAll.getSize(); i++) {
                        ArticleListDto data = new ArticleListDto(articlelistAll.getContent().get(i));
                        articleListDto.add(data);
                    }
                }

                jsonObject.put("nowPage", pageable.getPageNumber() + 1);   //이건 0부터 시작해서 +1 해줌
                jsonObject.put("endPage", articlelistAll.getTotalPages());  //이건 1부터 시작
                jsonObject.put("ArticleList", articleListDto);
                articleListDto.clear();

                return new ResultVo(0, "ture", "전체 출력", jsonObject.toString());
            }
        }
        //카테고리가 선택되었을 때
        else {
            Page<ArticleDto> articleList = articleService.articleList(pageable, regionid, pcategoryid);
            Integer lastPageCnt = Math.toIntExact(articleList.getTotalElements() - pageable.getOffset());  //마지막 페이지 index overflow 일으키지 않기 위해 따로 계산

            if (pageable.getPageNumber() + 1 > articleList.getTotalPages()) {
                return new ResultVo(309, "false", "마지막 페이지를 초과하였습니다.");
            } else {
                if (pageable.getPageNumber() == (articleList.getTotalPages() - 1)) {
                    for (int i = 0; i < lastPageCnt; i++) {
                        ArticleListDto data = new ArticleListDto(articleList.getContent().get(i));
                        articleListDto.add(data);
                    }
                } else {

                    for (int i = 0; i < articleList.getSize(); i++) {
                        ArticleListDto data = new ArticleListDto(articleList.getContent().get(i));
                        articleListDto.add(data);
                    }
                }

                jsonObject.put("nowPage", pageable.getPageNumber() + 1);  //이건 0부터 시작해서 +1 해줌
                jsonObject.put("endPage", articleList.getTotalPages());   //이건 1부터 시작
                jsonObject.put("ArticleList", articleListDto);
                articleListDto.clear();

                return new ResultVo(1, "ture", "해당 카테고리 출력", jsonObject.toString());
            }
        }
    }

@GetMapping("/articleWrite")
    public String article(){
return "board";
    }

@PostMapping("/articleWriteDo")
    public @ResponseBody ResultVo articleWritedo(ArticleWriteDto article, List<MultipartFile> file, HttpSession session) throws IOException {
    String userid = session.getAttribute("memberid").toString();

    article.setSellmemberid(userid);

    /*
    // Null값 체크
    System.out.println("내용 : "+ article);
    String null_title = article.getArticletitle();
    String null_content = article.getArticlecontent();
    String null_prince = article.getPrice();
    Integer null_categoryid = article.getProductcategoryid();
    Integer null_regionid = article.getRegionid();

    if(null_title.isEmpty() || null_content.isEmpty() || null_prince.isEmpty() || null_categoryid == null || null_regionid == null){
        return new ResultVo(301, "false", "필수 입력 정보를 모두 입력해주십시오.");
    }

*/

    articleService.write(article,file);


    return new ResultVo(0, "true", "글쓰기 성공" ,null);
}

    @GetMapping("/articleView") // localhost:8080/article/View?id=1
    public @ResponseBody ResultVo articleView(Model model, Integer id, HttpSession session) {
        int likecheck;
    model.addAttribute("article",articleService.articleview(id));

        String memberId = (String)session.getAttribute("memberid");

       /* if(memberId == null) {
            System.out.println("세션에 로그인값이 존재 하지 않음");
            likecheck = 3;

            ArticleEntity jsonObject = articleService.articleview(id);
            System.out.println(jsonObject.toString());


            return new ResultVo(0, "true", "불러오기 성공", jsonObject.toString()+ ", likecheck: " + likecheck);
        }

        */

            likecheck = bookMarkSerivce.Likecheck(id, memberId);


            ArticleEntity jsonObject = articleService.articleview(id);
            System.out.println(jsonObject.toString());


            return new ResultVo(0, "true", "불러오기 성공", jsonObject+ ", likecheck: " + likecheck);




    }






}
