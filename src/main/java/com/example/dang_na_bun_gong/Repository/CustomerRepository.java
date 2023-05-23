package com.example.dang_na_bun_gong.Repository;

import com.example.dang_na_bun_gong.DTO.QuestCommenDto;
import com.example.dang_na_bun_gong.DTO.QuestPrivateDto;
import com.example.dang_na_bun_gong.Entity.NotionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<NotionEntity, Integer>, JpaSpecificationExecutor<NotionEntity> {

    //공지사항 목록
    Page<NotionEntity> findAll(Pageable pageable);
//자주 하는 질문 목록
    @Query(value = "SELECT quest_title, quest_content_fp from quest_commen", countQuery = "SELECT COUNT(*) from quest_commen", nativeQuery = true)
    Page<QuestCommenDto> questCommenListAll(Pageable pageable);

    @Query(value = "SELECT quest_title, quest_content_fp from quest_commen where quest_category_id = :quest_category_id", countQuery = "SELECT COUNT(*) from quest_commen where quest_category_id = :quest_category_id", nativeQuery = true)
    Page<QuestCommenDto> questCommenList(Pageable pageable, Integer quest_category_id);

//내 질문 목록
    @Query(value = "select quest_title, quest_content_fp, quest_answer_fp from quest_private where questioner_id = :questioner_id and quest_category_id = :selected_quest_category_id ", countQuery = "SELECT COUNT(*) from quest_private where questioner_id = :questioner_id and quest_category_id = :selected_quest_category_id", nativeQuery = true)
    Page<QuestPrivateDto> questPrivateList(Pageable pageable, String questioner_id, Integer selected_quest_category_id);

    @Query(value = "select quest_title, quest_content_fp, answer_content_fp from quest_private where questioner_id = :questioner_id", countQuery = "SELECT COUNT(*) from quest_private where questioner_id = :questioner_id", nativeQuery = true)
    Page<QuestPrivateDto> questPrivateListAll(Pageable pageable, String questioner_id);
}