package com.hr.main.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hr.main.domain.MemberDTO;
import com.hr.main.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ApplicantController {

	@Autowired
	private MemberService memberService;
	
	
    @GetMapping("/member/updateMember.do")
    public String showUpdateMemberPage(HttpSession session, Model model) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
	    
	    if (loginMember == null) {
	        return "member/login"; // 사용자 정보가 없으면 로그인 페이지로 리다이렉트
	    }
	    
	    Long idx = loginMember.getIdx();
	    
	    MemberDTO memberdata = memberService.getMemberDetail(idx);
	    if (memberdata == null) {
	    	return "member/login";
	    }
	    
	    model.addAttribute("memberdata", memberdata);
	    return "member/updateMember";
    }

    @PostMapping("/member/updateMember.do")
    public String updateMember(@RequestParam("userId") String userId,
			  				   @RequestParam("password") String password,
			  				   @RequestParam("userName") String userName,
			  				   @RequestParam("phone") String phone,
			  				   HttpSession session, Model model) {

    	 MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

    	 if (loginMember == null) {
    	 	return "member/login";
    	 }
    	    
    	 Long idx = loginMember.getIdx();

    	 MemberDTO params = new MemberDTO();
    	 params.setIdx(idx);
    	 params.setPassword(password);
    	 params.setUserName(userName);
    	 params.setPhone(phone);

    	 boolean isUpdated = memberService.updateMember(params);

    	 if (isUpdated) {
    		 session.setAttribute("loginMember", params);
    		 model.addAttribute("message", "회원 정보가 성공적으로 업데이트되었습니다.");
    	     return "member/myPage";
    	 } else {
    		 model.addAttribute("message", "회원 정보 업데이트에 실패했습니다.");
    		 return "member/updateMember";
    	 }
    }
    
    
    @PostMapping("/member/deleteMember.do")
    public String deleteAccount(HttpSession session) {
    	MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
    	Long idx = loginMember.getIdx();
    	
    	boolean isDeleted = memberService.deleteMember(idx);
        
        if (isDeleted) {
            session.invalidate(); // 세션 무효화
            return "redirect:/"; // 로그인 페이지로 리다이렉트
        } else {
            return "member/myPage"; // 오류 발생 시 수정 페이지로 리다이렉트
        }
    }
	
    
	@GetMapping(value="/coverletter/write.do")
	public String openWriteCv(HttpSession session, Model model) {
		
	    MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
	    
	    if (loginMember == null) {
	        return "member/login";
	    }
	    
	    MemberDTO member = memberService.getMemberDetail(loginMember.getIdx());
	    model.addAttribute("member", member);
	    
	    return "coverletter/write";
	}

	@PostMapping(value="/coverletter/write.do")
	public String writeCv(@RequestParam("userName") String userName,
	                      @RequestParam("phone") String phone,
	                      @RequestParam("birth") String birthStr,
	                      @RequestParam("email") String email,
	                      @RequestParam("university") String university,
	                      @RequestParam("degree") String degree,
	                      @RequestParam("major") String major,
	                      @RequestParam("grade") double grade,
	                      @RequestParam("CV") String CV,
	                      HttpSession session, Model model) {

		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

	    if (loginMember == null) {
	        return "member/login";
	    }
	    
		MemberDTO params = new MemberDTO();
		params.setUserId(loginMember.getUserId());
	    params.setUserName(userName);
	    params.setPhone(phone);
	    
		Date birth=null;
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        birth = sdf.parse(birthStr);
	        params.setBirth(birth);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    
	    params.setEmail(email);
	    params.setUniversity(university);
	    params.setMajor(major);
	    params.setGrade(grade);
	    params.setDegree(degree);
	    params.setCV(CV);
	    params.setIdx(loginMember.getIdx());
	    params.setPassword(loginMember.getPassword()); // Set a value for password if necessary

	    boolean success = memberService.writeCv(params);

	    if (success) {
	        model.addAttribute("message", "Your CV has been successfully updated.");
	        return "coverletter/writeComplete";
	    } else {
	        model.addAttribute("error", "Failed to update your CV. Please try again.");
	        return "coverletter/write";
	    }
	}
	
	
	@GetMapping(value="/member/detailApplyPage.do")
	public String openDetailApplyPage(@RequestParam(value="idx", required=false) Long idx, HttpSession session, Model model) {
	    MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
	    
	    if (loginMember == null) {
	        return "member/login";
	    }
	    
	    if (idx == null) {
	        idx = loginMember.getIdx(); // Use the logged-in user's ID
	    }
	    
	    MemberDTO memberdata = memberService.getMemberDetail(idx);
	    if (memberdata == null) {
	        model.addAttribute("message", "지원 내역이 존재하지 않습니다.");
	        return "member/detailApplyPage";
	    }
	    
	    model.addAttribute("memberdata", memberdata);
	    
	    return "member/detailApplyPage";
	}
	
	
}
