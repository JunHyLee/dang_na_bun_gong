package com.example.dang_na_bun_gong.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "notion")
@Data
public class NotionEntity {
    @Id
    @Column(name = "notion_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer notionid;

    @Column(name = "notion_title", nullable = false)
    String notionTitle;

    @Column(name = "notion_created", nullable = false)
    String notionCreated;

    @Column(name = "notion_content_fp", nullable = false)
    String notionContentFp;
}
