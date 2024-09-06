package com.hr.main;

import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hr.main.mapper.MemberMapper;
import com.hr.main.domain.MemberDTO;

@SpringBootTest
public class MapperTests {
	@Autowired
	private MemberMapper memeberMapper;
	
	@Test
	public void testOfInsert() {
		MemberDTO params=new MemberDTO();
		params.setUserId("test");
		params.setPassword("test");
		params.setUserName("tester");
		params.setPhone("010-1111");
		
		int result=memeberMapper.insertMember(params);
		System.out.println("결과는"+result+"입니다");
	}
	
	@Test
	public void testOfSelectDetail() {
		System.out.println("11");
		MemberDTO member= memeberMapper.selectMemberDetail((long)1);
		System.out.println("22");
		try {
			String boardJson=new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(member);
			System.out.println("=============");
			System.out.println(boardJson);
			System.out.println("=============");
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOfUpdate() {
		MemberDTO params=new MemberDTO();
		params.setUserId("admin");
		params.setPassword("admin");
		params.setUserName("admin");
		params.setPhone("010-1234-1234");
		params.setIdx((long)1);
		
		int result=memeberMapper.updateMember(params);
		if(result==1) {
			MemberDTO member=memeberMapper.selectMemberDetail((long)1);
			try {
				String memberJson=new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(member);
				
				System.out.println("============");
				System.out.println(memberJson);
				System.out.println("============");
			} catch(JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testOfDelete() {
		int result=memeberMapper.deleteMember((long)1);
		System.out.println("11");
		if(result==1) { // result는 0 아니면 1
			MemberDTO member=memeberMapper.selectMemberDetail((long)1);
			System.out.println("22");
			try {
				String memberJson=new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(member);
				
				System.out.println("=================");
				System.out.println(memberJson);
				System.out.println("==================");
			} catch(JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testSelectList() {
		 int memberTotalCount = memeberMapper.selectMemberTotalCount();

	        if (memberTotalCount > 0) {
	            // 페이지네이션 파라미터 설정
	            Map<String, Object> params = new HashMap<>();
	            params.put("startPage", 0); // 시작 페이지
	            params.put("recordPerPage", 10); // 페이지당 레코드 수

	            // 회원 리스트 조회
	            List<MemberDTO> memberList = memeberMapper.selectMemberList(params);

	            // 리스트가 비어있지 않은지 검증
	            assertFalse(CollectionUtils.isEmpty(memberList), "회원 리스트가 비어있습니다.");

	            // 각 회원 정보 출력 및 검증
	            for (MemberDTO member : memberList) {
	                assertNotNull(member.getUserId(), "UserId는 null일 수 없습니다.");
	                assertNotNull(member.getUserName(), "UserName은 null일 수 없습니다.");
	                
	                System.out.println("===========");
	                System.out.println("UserId: " + member.getUserId());
	                System.out.println("Password: " + member.getPassword());
	                System.out.println("UserName: " + member.getUserName());
	                System.out.println("Phone: " + member.getPhone());
	                System.out.println("===========");
	            }
	        }
	}
	
	@Test
	public void testLogin() {
		MemberDTO successMember = new MemberDTO();
        successMember.setUserId("a");
        successMember.setPassword("a");

        MemberDTO successResult = memeberMapper.loginMember(successMember);
        assertNotNull(successResult, "로그인 성공");

        MemberDTO failureMember = new MemberDTO();
        failureMember.setUserId("a");
        failureMember.setPassword("wrongpassword");

        MemberDTO failureResult = memeberMapper.loginMember(failureMember);
        assertNull(failureResult, "비밀번호를 잘못 입력하였습니다.");
	}
	
	@Test
	public void testwriteCv() {
		MemberDTO params = new MemberDTO();
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        Date birthDate = sdf.parse("1977-11-11");
	        params.setBirth(birthDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return; // Exit the test if date parsing fails
	    }

	    params.setEmail("a@a.com");
	    params.setUniversity("가상대");
	    params.setMajor("경영학과");
	    params.setGrade(4.01);
	    params.setDegree("bachelor");
	    params.setCV("활기찬 에너지 냥냥입니다~");
	    params.setIdx(2L);
	    params.setPassword("your_password"); // Set a value for password if necessary

	    int result = memeberMapper.writeCv(params);
	    if (result == 1) {
	        MemberDTO member = memeberMapper.selectMemberDetail(2L);
	        try {
	            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	            String memberJson = objectMapper.writeValueAsString(member);
	            
	            System.out.println("============");
	            System.out.println(memberJson);
	            System.out.println("============");
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
}
