package com.example.dang_na_bun_gong.Entity;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "article")
@Data
@NoArgsConstructor
public class BoardEntity {

	@Id
	@Column(name="article_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleid;

	@Column(name = "article_title", nullable = false)
	private String articletitle;

	@Column(name = "photo_fp", nullable = false)
	private String photofp;

	@Column(name = "price", nullable = false)
	private String price;

	@Builder
	public BoardEntity(Integer articleid, String articletitle, String price, String photofp){
		this.articleid = articleid;
		this.articletitle = articletitle;
		this.price = price;
		this.photofp = photofp;
	}


}