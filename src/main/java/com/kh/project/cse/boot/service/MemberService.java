package com.kh.project.cse.boot.service;

import com.kh.project.cse.boot.domain.vo.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberService {
    int insertMember(Member member);

    Member loginMember(String memberId);

    int idCheck(String checkId);

    int insertStore(String storeName);
}
