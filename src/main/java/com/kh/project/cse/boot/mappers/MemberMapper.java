package com.kh.project.cse.boot.mappers;

import com.kh.project.cse.boot.domain.vo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    int insertMember(Member member);

    Member loginMember(@Param("memberId") String memberId);

    int idCheck(@Param("checkId") String checkId);

    int updateMember(Member member);

    Member selectMemberById(@Param("memberId") String memberId);

}
