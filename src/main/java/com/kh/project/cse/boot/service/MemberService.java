package com.kh.project.cse.boot.service;

import com.kh.project.cse.boot.domain.vo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberService {
    int insertMember(Member member);

    Member loginMember(String memberId);

    int idCheck(String checkId);

    int insertStore(String storeName);

    int checkStoreName(String checkStore);

    int updateMember(Member member);

    Member selectMemberById(String memberId);

    int updateMemberStatus(Member member);

    List<Member> selectMemberList();
}
