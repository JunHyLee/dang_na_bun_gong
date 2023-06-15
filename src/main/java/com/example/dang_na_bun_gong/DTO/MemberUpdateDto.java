package com.example.dang_na_bun_gong.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class MemberUpdateDto {
    private String memberName;
    private String memberNickname;
    private String memberTel;
    private String memberMail;
    private Integer memberRegionId;
    private MultipartFile memberPhoto;
    private String memberIntro;
    private Timestamp memberChanged;
    private String memberBirth;
    private String memberGender;
}