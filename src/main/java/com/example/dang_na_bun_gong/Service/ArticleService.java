package com.example.dang_na_bun_gong.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.example.dang_na_bun_gong.DTO.ArticleDto;
import com.example.dang_na_bun_gong.DTO.ArticleWriteDto;
import com.example.dang_na_bun_gong.Entity.ArticleEntity;
import com.example.dang_na_bun_gong.Entity.CategoryEntity;
import com.example.dang_na_bun_gong.Entity.RegionEntity;
import com.example.dang_na_bun_gong.Repository.ProductCategoryRepository;
import com.example.dang_na_bun_gong.Repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dang_na_bun_gong.Repository.ArticleRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private RegionRepository regionRepository;


//여기부터 main페이지
    public List<ArticleDto> mainPage_current(){
        return articleRepository.currentArticle();
    }
    public List<ArticleDto> mainPage_popular(){
        return articleRepository.popularArticle();
    }
    public List<CategoryEntity> mainPage_product_category(){ // 상품 카테고리
        return productCategoryRepository.findAll();
    }
    public List<RegionEntity> mainPage_region(){ // 상품 카테고리
        return regionRepository.findAll();
    }

// 여기부터 게시글 리스트(article list)
    public Page<ArticleDto> articleListAllNoLogin(Pageable pageable){
        return articleRepository.articleListAllNoLogin(pageable);
    }
    public Page<ArticleDto> articleListNoLogin(Pageable pageable, Integer pcategoryid){
        return articleRepository.articleListNoLogin(pageable, pcategoryid);
    }
    public Page<ArticleDto> articleListAll(Pageable pageable, Integer regionid){
        return articleRepository.articleListAll(pageable, regionid);
    }
    public Page<ArticleDto> articleList(Pageable pageable, Integer regionid, Integer pcategoryid){
        return articleRepository.articleList(pageable, regionid, pcategoryid);
    }


    public void write(ArticleWriteDto article, List<MultipartFile> file) throws IOException {

        String contentPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\text";

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();
        UUID content = UUID.randomUUID();

        //String fileName = uuid + "_" + file.getOriginalFilename();
        String fileName = uuid.toString().replaceAll("-", "").toUpperCase();
        String textName = content.toString().replaceAll("-", "").toUpperCase();
        int fileCount = 0;
        for(MultipartFile f: file) {
            File saveFile = new File(projectPath, fileName + Integer.toString(fileCount) + ".jpg");
            //file.get(i).transferTo(saveFile);
            System.out.println("uuid: " + fileName);
            f.transferTo(saveFile);
            article.setPhotofp("/files/" + fileName + ".jpg");
            fileCount += 1;
        }

        try{
            /*File savetext = new File(contentPath);
            File saveText = new File(contentPath, textName + ".txt");
            System.out.println("textFileName: " + textName);
            fileWriter.write(article.getArticlecontent());
          article.setArticlecontentfp("/files/" + textName);
*/
            // BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
            File savetext = new File(contentPath,textName + ".txt");
            BufferedWriter fw = new BufferedWriter(new FileWriter(savetext, true));

            // 파일안에 문자열 쓰기
            fw.write(article.getArticlecontent());
            fw.flush();
            System.out.println("textFileName: " + textName);
            article.setArticlecontentfp("/text/" + textName + ".txt");
            // 객체 닫기
            fw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        article.setPhotocnt(fileCount);
        ArticleEntity articleEntity = ArticleEntity.toSaveEntity(article);
        articleRepository.save(articleEntity);
    }

    public ArticleEntity articleview(Integer id){

        return articleRepository.findById(id).get();
    }



}
