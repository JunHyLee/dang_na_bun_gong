package com.example.dang_na_bun_gong.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuestPrivateViewDto {

    Integer quest_id, quest_category_id;
    String quest_title, quest_content_fp, answer_content_fp, questioner_id;
    Timestamp quest_created;

    public QuestPrivateViewDto(QuestPrivateDto questPrivateDto){
        this.quest_id = questPrivateDto.getQuest_id();
        this.quest_title  = questPrivateDto.getQuest_title();
        this.quest_content_fp = questPrivateDto.getQuest_content_fp();
        this.answer_content_fp = questPrivateDto.getAnswer_content_fp();
        this.questioner_id = questPrivateDto.getQuestioner_id();
        this.quest_category_id = questPrivateDto.getQuest_category_id();
        this.quest_created = questPrivateDto.getQuest_created();
    }
}
