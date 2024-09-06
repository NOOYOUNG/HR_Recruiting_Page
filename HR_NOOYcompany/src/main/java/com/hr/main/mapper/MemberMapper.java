package com.hr.main.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hr.main.domain.MemberDTO;

@Mapper
public interface MemberMapper {

	public int insertMember(MemberDTO params);
	public MemberDTO selectMemberDetail(Long idx);
	public int updateMember(MemberDTO params);
	public int deleteMember(Long idx);
	public List<MemberDTO> selectMemberList(Map<String, Object> params);
	public int selectMemberTotalCount();
	
	public MemberDTO loginMember(MemberDTO member);
	public int writeCv(MemberDTO params);
}
