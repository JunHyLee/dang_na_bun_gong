package com.example.dang_na_bun_gong.DTO;

import java.sql.Timestamp;

public interface QuestPrivateDto {
    Integer getQuest_id();
    String getQuest_title();
    String getQuest_content_fp();
    String getAnswer_content_fp();
    String getQuestioner_id();
    Integer getQuest_category_id();
    Timestamp getQuest_created();
}
