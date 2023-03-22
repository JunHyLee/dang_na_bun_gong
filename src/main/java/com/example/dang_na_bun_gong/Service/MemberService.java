package com.example.dang_na_bun_gong.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dang_na_bun_gong.Entity.MemberEntity;
import com.example.dang_na_bun_gong.Repository.MemberRepository;

@Service
public class MemberService {
	//test 만들기: 컨트롤 쉬프트 t

	@Autowired
	private MemberRepository memberRepository;
	
	//아이디 확인
	public boolean memberIDcheck(String memberid) {
		
		return memberRepository.existsById(memberid);
	}

	public boolean memberTelcheck(String membertel) {

		return memberRepository.existsByMembertel(membertel);
	}

	public boolean memberMailcheck(String membermail) {

		return memberRepository.existsByMembermail(membermail);
	}
	
	//로그인 확인
	public Integer loginCheck(String memberid, String memberpw) {
		 
		return memberRepository.loginCheck(memberid, memberpw);
	}
	
	//회원 정보 가져오기
    public List<MemberEntity> getMemberInfo(String memberid) {
        return memberRepository.findByMemberid(memberid);
    }
    
	//회원가입
	public String memberWrite(MemberEntity member) {
		memberRepository.save(member);

		return member.getMemberid();
	}
	
	//회원수정
	public void memberUpdate(List<MemberEntity> findEntity) {
		memberRepository.saveAll(findEntity);
	}
	
	//회원탈퇴
	
	// 게시글 리스트 처리
    public Page<MemberEntity> boardList(Pageable pageable) {
    	System.out.println("pageable: " + pageable);

        return memberRepository.findAll(pageable);
    }

	//소셜로그인 토큰 받아오기
	/* 프론트에서 해주는건데 내가 구현한거래... 나 이걸로 일주일을 하루에 7,8시간씩 머리싸맸는데...
	public String getAccessToken(String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=0aab8850b0f1de3b11837d1e5e945b8b");
			sb.append("&redirect_uri=http://localhost:8080/kakaoLogin");
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();

			//int responseCode = conn.getResponseCode();
			//System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			//System.out.println("response body : " + result);


			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			//System.out.println("access_token : " + access_Token);
			//System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return access_Token;
	}
	*/

	//소셜로그인 회원 정보 가져오기
	public HashMap<String, Object> getUserInfo(String access_Token) {

		HashMap<String, Object> userInfo = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			//int responseCode = conn.getResponseCode();
			//System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			//System.out.println("response body : " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String email = kakao_account.getAsJsonObject().get("email").getAsString();
			String gender = kakao_account.getAsJsonObject().get("gender").getAsString();
			String birth = kakao_account.getAsJsonObject().get("birthday").getAsString();

			userInfo.put("membermail", email);
			userInfo.put("membernickname", nickname);
			userInfo.put("membergender", gender);
			userInfo.put("memberbirth", birth);


		} catch (IOException e) {
			e.printStackTrace();
		}

		return userInfo;
	}
    
}
