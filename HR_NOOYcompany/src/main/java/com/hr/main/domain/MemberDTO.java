package com.hr.main.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

	private Long idx;
	private String userId;
	private String password;
	private String userName;
	private String phone;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;
	
	private String email;
	private String university;
	private String major;
	private double grade;
	private String degree;
	private String CV;
		
		
}
