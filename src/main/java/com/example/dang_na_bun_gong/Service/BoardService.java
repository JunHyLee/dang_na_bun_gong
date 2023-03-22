package com.example.dang_na_bun_gong.Service;

import java.util.List;

import com.example.dang_na_bun_gong.DTO.ArticleDto;
import com.example.dang_na_bun_gong.Entity.BoardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dang_na_bun_gong.Repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;


    //main페이지
    public List<ArticleDto> mainPage_current(){
        return boardRepository.currentArticle();
    }

    public List<ArticleDto> mainPage_popular(){
        return boardRepository.popularArticle();
    }

    public Page<BoardEntity> articleListAll(Pageable pageable, Integer regionid){
        return boardRepository.articleList(pageable, regionid);
    }

	//articleList 페이지 (아직 실패)



}
