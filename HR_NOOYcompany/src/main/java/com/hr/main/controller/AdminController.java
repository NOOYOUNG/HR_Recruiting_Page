package com.hr.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hr.main.domain.MemberDTO;
import com.hr.main.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping(value="/member/detailManagePage.do")
	public String openDetailApplyPage(Model model) {
		
		int startPage=0;
		int recordPerPage=10;
		
		List<MemberDTO> memberdata = memberService.getMemberList(startPage, recordPerPage);
		model.addAttribute("memberdata", memberdata);
	    return "member/detailManagePage";
	}
	
}
