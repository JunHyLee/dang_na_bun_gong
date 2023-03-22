package com.example.dang_na_bun_gong.Controller;

import com.example.dang_na_bun_gong.DTO.MemberJoinDto;
import com.example.dang_na_bun_gong.DTO.MemberLoginDto;
import com.example.dang_na_bun_gong.DTO.MyPageMemberDto;
import com.example.dang_na_bun_gong.Vo.ResultVo;
import com.example.dang_na_bun_gong.Entity.MemberEntity;
import com.example.dang_na_bun_gong.Repository.MemberRepository;
import com.example.dang_na_bun_gong.Service.MemberService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Member;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class MemberController {

	private final MemberRepository memberRepository = null;

	@Autowired
	private MemberService memberService;

	@PersistenceContext
	private EntityManager entityManager;

	//로그인
	@GetMapping("/login")
	public String login(Model model, HttpSession session) {
		model.addAttribute("memberid", session.getAttribute("memberid"));
		return "login";
	}

	@PostMapping("/loginDo")
	public @ResponseBody ResultVo loginDo(MemberLoginDto memberLoginDto, Model model, HttpSession session) {

		System.out.println("memberLoginDto = " + memberLoginDto.getMemberid() +" "+ memberLoginDto.getMemberpw());
		Integer loginCheck = memberService.loginCheck(memberLoginDto.getMemberid(), memberLoginDto.getMemberpw());
		System.out.println("loginCheck: "+loginCheck);
		
		if (loginCheck == 1) {
			session.setAttribute("memberid", memberLoginDto.getMemberid());
			return new ResultVo(0, "true", "로그인 성공");
		}

		else if(loginCheck == 0){
			System.out.println("아이디나 비밀번호가 잘못되었습니다." + memberLoginDto.getMemberid());
			return new ResultVo(100, "false", "아이디나 비밀번호가 잘못되었습니다.");
		}

		else {//음수 혹은 2 이상일 때
			return new ResultVo(200, "false", "회원정보가 잘못되었습니다. 고객센터에 문의바랍니다.");
		}
	}

	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {

		//session.removeAttribute("memberid");
		if(session.getAttribute("memberid") != null) {
			System.out.println("Logout session: " + session.getAttribute("memberid").toString());
		}
		session.removeAttribute("memberid");
		session.invalidate(); // 세션 초기화

		return "home";
	}

	//회원가입
	@GetMapping("/memberJoin")
	public String member(Model model, HttpSession session) {
		model.addAttribute("memberid", session.getAttribute("memberid"));
		return "memberJoin";
	}

	@PostMapping("/memberJoinDo")
	public @ResponseBody ResultVo memberJoinDo(MemberJoinDto memberJoinDto, Model model) {
		boolean memberId, memberTel, memberMail, isTel, isMail;

		//null값 확인
		String null_id = memberJoinDto.getMemberid();
		String null_pw = memberJoinDto.getMemberpw();
		String null_name = memberJoinDto.getMembername();
		String null_tel = memberJoinDto.getMembertel();
		String null_mail = memberJoinDto.getMembermail();

		if(null_id.isEmpty() || null_pw.isEmpty() || null_name.isEmpty() || null_mail.isEmpty() || null_tel.isEmpty()){
			return new ResultVo(400, "false", "필수 입력 정보를 모두 입력해주십시오.");
		}

		//아이디 중복 확인
		memberId = memberService.memberIDcheck(memberJoinDto.getMemberid());
		if (memberId) { // memberid == true
			System.out.println("이미 존재하는 아이디");
			String message = "이미 존재하는 아이디입니다.";
			model.addAttribute("message", message);
			return new ResultVo(100, "false", "아이디 중복");
		}

		//전화번호 양식 확인
		isTel = Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", memberJoinDto.getMembertel());
		if (!isTel) { // isTel == true
			System.out.println("유효하지 않은 형식의 전화번호");
			String message = "유효한 전화번호를 입력해주십시오.";
			model.addAttribute("message", message);
			return new ResultVo(100, "false", "유효하지 않은 전화번호");
		}

		//전화번호 중복 확인
		memberTel = memberService.memberTelcheck(memberJoinDto.getMembertel());
		if (memberTel) { // memberTel == true
			System.out.println("이미 존재하는 전화번호");
			String message = "이미 사용 중인 전화번호입니다.";
			model.addAttribute("message", message);
			return new ResultVo(100, "false", "전화번호 중복");
		}

		//이메일 양식 확인"^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
		isMail = Pattern.matches("^[a-zA-Z0-9]+\\@[a-zA-Z0-9.-]+\\.[a-zA-Z]+$", memberJoinDto.getMembermail());
		if (!isMail) { // isMail == true
			System.out.println("유효하지 않은 형식의 이메일");
			String message = "유효한 이메일 입력해주십시오.";
			model.addAttribute("message", message);
			return new ResultVo(100, "false", "유효하지 않은 이메일");
		}

		//이메일 중복 확인
		memberMail = memberService.memberMailcheck(memberJoinDto.getMembermail());
		if (memberMail) { // memberMail == true
			System.out.println("이미 존재하는 이메일");
			String message = "이미 사용 중인 이메일입니다.";
			model.addAttribute("message", message);
			return new ResultVo(100, "false", "이메일 중복");
		}

		//회원가입 실행
		else {
			String findMember = memberService.memberWrite(memberJoinDto.toMemberEntity());
			if(memberService.memberIDcheck(findMember)) { //회원 정보 삽입 후 확인
				String message = "회원가입이 완료되었습니다.";
				model.addAttribute("message", message);
				return new ResultVo(0, "true", "회원가입 성공");
				//return "memberJoin";
			}
			else {
				System.out.println("가입 실패");
				String message = "가입 실패";
				model.addAttribute("message", message);
				return new ResultVo(300, "false", "가입 실패");
				//return "memberJoin";
			}
		}

	}

	//카카오 소셜로그인
	@RequestMapping("/kakaoLogin")
	// https://kauth.kakao.com/oauth/authorize?client_id=0aab8850b0f1de3b11837d1e5e945b8b&redirect_uri=http://localhost:8080/kakaoLogin&response_type=code 입력해야함.
	public @ResponseBody ResultVo kakaoLogin(@RequestParam(value = "code", required = false) String access_Token) {
		//String access_Token = memberService.getAccessToken(code); access 토큰은 프론트에서 넘겨줌
		HashMap<String, Object> userInfo = memberService.getUserInfo(access_Token);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("kakaoLoginInfo", userInfo.toString());

		Object member_nickname = userInfo.get("member_nickname"); //카카오에서 넘어온 회원정보가 userInfo에 저장되어있는지 확인

		if(member_nickname == null){
			return new ResultVo(109, "false", "서버오류");
		}else {
			return new ResultVo(0, "ture", "카카오 정보 출력 성공", jsonObject.toString());
		}
	}

	// 회원 리스트 처리
	@GetMapping("/member/list")
	public String memberList(Model model,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {

		Page<MemberEntity> list = null;

		list = memberService.boardList(pageable);

		model.addAttribute("list", list);

		return "memberList";
	}

	// 회원정보 수정
	@GetMapping("/memberUpdate")
	public @ResponseBody ResultVo memberUpdate(HttpSession httpSession){
		String memberid = httpSession.getAttribute("memberid").toString();
		if(memberid == null){
			return new ResultVo(101, "false", "비로그인상태");
		}else{
		List<MemberEntity> findEntity = memberService.getMemberInfo(memberid);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("memberinfo", findEntity);
		return new ResultVo(0, "true", "success", jsonObject.toString());
		}
	}

	//회원정보 저장하기
	@PatchMapping("memberUpdateDo")
	public @ResponseBody ResultVo memberUpdateDo(HttpSession httpSession, MemberEntity memberEntity){
			String memberid = httpSession.getAttribute("memberid").toString();
			List<MemberEntity> findEntity = memberService.getMemberInfo(memberid);
			if(memberid != null) {
				findEntity.get(0).setMembername(memberEntity.getMembername()); // 이름
				findEntity.get(0).setMemberNickname(memberEntity.getMembernickname()); // 닉네임
				findEntity.get(0).setMembertel(memberEntity.getMembertel()); // 전화번호
				findEntity.get(0).setMembermail(memberEntity.getMembermail()); // 이메일
				findEntity.get(0).setMemberregion(memberEntity.getMemberregion()); // 지역코드
				findEntity.get(0).setMemberphotofp(memberEntity.getMemberphotofp());
				findEntity.get(0).setMemberintro(memberEntity.getMemberintro());// 소개글(짧은글)
				LocalDateTime now = LocalDateTime.now();
				findEntity.get(0).setMemberchanged(Timestamp.valueOf(now)); // 수정시간(현재)

				memberService.memberUpdate(findEntity); // memberInfo에 저장한 값 DB로 저장
				return new ResultVo(0, "ture", "변경완료");
			}
			else {
				return new ResultVo(101, "false", "변경실패");
			}
			}

	/*@PostMapping("/memberUpdateDo")
	public @ResponseBody ResultVo memberUpdateDo(HttpSession httpSession, MemberUpdateEntity memberUpdateEntity) {
		List<MemberEntity> findEntity = memberService.getMemberInfo(httpSession.getAttribute("memberid").toString());
		findEntity.get(0).setMembername(memberEntity.getMembername());
	}


	// 회원정보 수정
	/*@GetMapping("/member/modify") // localhost:8080/board/view?id=1
	public String memberModify(Model model, HttpSession session, MemberEntity memberEntity) {

		//회원 수정 못하고 있음. 변경된 필드만 DB update 하고싶다
		//아이디를 가지고 와서 다른 엔티티에 저장
		//넘어오는 엔티티에서 get할 수 있는 데이터는 위 다른 엔티티에 저장하기
		//위 엔티티로 save 하기

		List<MemberEntity> findEntity = memberService.memberInfo(session.getAttribute("memberid").toString());
		System.out.println("findEntity: " + findEntity.toString());

		if(session.getAttribute("memberid") != null) {
			System.out.println(memberEntity);
			findEntity.get(0).setMembername(memberEntity.getMembername());
			findEntity.get(0).setMembertel(memberEntity.getMembertel());

			LocalDateTime now = LocalDateTime.now();
			findEntity.get(0).setMemberchanged(Timestamp.valueOf(now));
//
//			 List<UserEntity> memberInfo = null; memberInfo =
//			 memberService.memberInfo(session.getAttribute("memberid").toString());
//			 model.addAttribute("memberInfo", memberInfo);
//
			memberService.memberUpdate(findEntity);
			model.addAttribute("message", "변경이 완료되었습니다.");
		}

	       return "redirect:/member/mypage";
	   }
    */

	/*
	 * @GetMapping("update") public String updateMember(@RequestParam(value =
	 * "name") String name, @RequestParam(value = "age") int age) {
	 * if(memberEntityRepository.findById(name).isEmpty()) { // 값 존재여부 확인 return
	 * "입력한 " + name + "이 존재하지 않습니다"; } else {
	 * memberEntityRepository.save(UserEntity.builder().name(name).age(age).build());
	 * return name + "의 나이를 " + age + "로 변경 완료"; } }
	 *
	 * @GetMapping("delete") public String deleteMember(@RequestParam(value =
	 * "name") String name) { if(memberEntityRepository.findById(name).isEmpty()) { //
	 * 값 존재여부 확인
	 *
	 * } }
	 *
	 *
	 * @GetMapping("search") public String searchAllMember() {
	 * System.out.println(memberEntityRepository.findAll().toString()); return
	 * memberEntityRepository.findAll().toString(); }
	 *
	 * @GetMapping("searchParam") public String
	 * searchParamMember(@RequestParam(value = "age") int age) { List resultList =
	 * entityManager.createQuery("select name from member where age > :age")
	 * .setParameter("age", age) .getResultList(); return resultList.toString(); }
	 *
	 * @GetMapping("searchParamRepo") public String
	 * searchParamRepoMember(@RequestParam(value = "name") String name) { return
	 * memberEntityRepository.searchParamRepo(name).toString(); }
	 */

}