package com.hr.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hr.main.domain.MemberDTO;
import com.hr.main.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping(value="/member/register.do")
	public String openRegisterMember(@RequestParam(value="idx", required=false) Long idx, Model model) {
		if(idx==null) {
			model.addAttribute("member", new MemberDTO());
		}
		else {
			MemberDTO member=memberService.getMemberDetail(idx);
			if(member==null) {
				return "redirect:/member/login";
			}
			model.addAttribute("member", member);
		}
		return "member/register";
	}
	
	@PostMapping(value="/member/register.do")
	public String registerMember(final MemberDTO params, Model model) {
		try {
			boolean isRegister=memberService.registerMember(params);
			if(isRegister==false) {
				model.addAttribute("error", "사용할 수 없는 아이디입니다.");
				return "member/register";
			}
			if(params.getUserId().equals("admin")) {
				model.addAttribute("error", "사용할 수 없는 아이디입니다.");
				return "member/register";
			}
		} catch(DataAccessException e) {
			model.addAttribute("error", "사용할 수 없는 아이디입니다.");
			return "member/register";
		} catch(Exception e) {
			model.addAttribute("error", "사용할 수 없는 아이디입니다.");
			return "member/register";
		}
	    
	    return "member/login";
		
	}
	
	
	@GetMapping(value="/member/login.do")
	public String openLoginMember() {
		return "member/login";
	}
	
	@PostMapping(value="/member/login.do")
	public String loginMember(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpSession session, Model model) {
		 MemberDTO member = memberService.login(userId, password);
	        
	        if (member != null) {
	            // 로그인 성공 시, 사용자 정보를 모델에 추가하고 성공 페이지로 이동
	        	session.setAttribute("loginMember", member);
	            model.addAttribute("member", member);
	            
	            if(userId.equals("admin")) {
	            	return "member/adminPage";
	            }
	            else {
	            	return "member/myPage";
	            }
	        } else {
	            // 로그인 실패 시, 에러 메시지를 모델에 추가하고 로그인 페이지로 다시 이동
	            model.addAttribute("error", "등록되지 않은 아이디입니다.");
	            return "member/login"; // 로그인 실패 후 로그인 페이지로 돌아감
	        }
	}
	
	
	@GetMapping(value="/member/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
