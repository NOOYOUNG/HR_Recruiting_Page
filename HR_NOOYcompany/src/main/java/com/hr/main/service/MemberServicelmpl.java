package com.hr.main.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.main.domain.MemberDTO;
import com.hr.main.mapper.MemberMapper;

@Service
public class MemberServicelmpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public boolean registerMember(MemberDTO params) {
		int queryResult=0;
		
		if(params.getIdx()==null) {
			queryResult=memberMapper.insertMember(params);
		} else {
			queryResult=memberMapper.updateMember(params);
		}
		
		return (queryResult==1)? true:false;
	}
	
	@Override
	public MemberDTO getMemberDetail(long idx) {
		return memberMapper.selectMemberDetail(idx);
	}
	
	@Override
	public boolean updateMember(MemberDTO params) {
		 int queryResult = 0;
	        
	        if (params != null && params.getIdx() != null) {
	            try {
	                queryResult = memberMapper.updateMember(params);
	            } catch (Exception e) {
	                System.out.println("Exception during update: " + e.getMessage());
	            }
	        }
	        
	        return queryResult == 1;
	}
	
	@Override
	public boolean deleteMember(long idx) {
		int queryResult=0;
		
		MemberDTO member=memberMapper.selectMemberDetail(idx);
		
		queryResult = memberMapper.deleteMember(idx);
	        
		return (queryResult==1)? true:false;
	}
	
	@Override
	public List<MemberDTO> getMemberList(int startPage, int recordPerPage) {
//		List<MemberDTO> memberList=Collections.emptyList();
//		
//		int memberTotalCount=memberMapper.selectMemberTotalCount();
//		
//		if(memberTotalCount>0) {
//			memberList=memberMapper.selectMemberList();
//		}
//		
//		return memberList;
		
		Map<String, Object> params = new HashMap<>();
	    params.put("startPage", startPage);
	    params.put("recordPerPage", recordPerPage);
	    
	    return memberMapper.selectMemberList(params);
	}
	
	@Override
    public MemberDTO login(String userId, String password) {
        // 로그인 요청에 대해 MemberMapper의 로그인 메서드를 호출하여 인증 수행
        MemberDTO member = new MemberDTO();
        member.setUserId(userId);
        member.setPassword(password);

        return memberMapper.loginMember(member);
    }
	
	@Override
	public boolean writeCv(MemberDTO params) {
	    int queryResult = 0;
	    
	    if (params.getUserId() != null) {
	        try {
	            queryResult = memberMapper.writeCv(params);
	            System.out.println("Query result: " + queryResult); // Debugging output
	        } catch (Exception e) {
	            System.out.println("Exception during update: " + e.getMessage()); // Log exceptions
	        }
	    }
	    
	    return queryResult == 1;
	}
	
}
