package com.example.dang_na_bun_gong.Service;

import com.example.dang_na_bun_gong.DTO.QuestCommenDto;
import com.example.dang_na_bun_gong.DTO.QuestPrivateDto;
import com.example.dang_na_bun_gong.Entity.*;
import com.example.dang_na_bun_gong.Repository.CustomerRepository;
import com.example.dang_na_bun_gong.Repository.QuestCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    QuestCategoryRepository questCategoryRepository;

    //공지사항 목록 출력
    public Page<NotionEntity> notionList(Pageable pageable){
        return customerRepository.findAll(pageable);
    }

    //질문 카테고리 일괄 출력 (고객센터 메인페이지와 함께 출력)
    public List<QuestCategoryEntity> questCategoryList(){
        return questCategoryRepository.findAll();
    }


//자주하는 질문 목록 출력
    public Page<QuestCommenDto> questCommenListAll(Pageable pageable){
        return customerRepository.questCommenListAll(pageable);
    }
    public Page<QuestCommenDto> questCommenList(Pageable pageable, Integer quest_category_id){
        return customerRepository.questCommenList(pageable, quest_category_id);
    }

//내 질문 목록 출력
    public Page<QuestPrivateDto> questPrivateListAll(Pageable pageable, String questioner_id){
        return customerRepository.questPrivateListAll(pageable, questioner_id);
    }
    public Page<QuestPrivateDto> questPrivateList(Pageable pageable, String questioner_id, Integer selected_quest_category_id){
        return customerRepository.questPrivateList(pageable, questioner_id, selected_quest_category_id);
}



}