package com.hr.main.service;

import java.util.List;

import com.hr.main.domain.MemberDTO;

public interface MemberService {

	public boolean registerMember(MemberDTO params);
	public MemberDTO getMemberDetail(long idx);
	public boolean updateMember(MemberDTO params);
	public boolean deleteMember(long idx);
	public List<MemberDTO> getMemberList(int startPage, int recordPerPage);
	
	public MemberDTO login(String userId, String password);
	
	public boolean writeCv(MemberDTO params);
}
