package com.kh.project.cse.boot.service;

import com.kh.project.cse.boot.domain.vo.Member;
import com.kh.project.cse.boot.domain.vo.PageInfo;
import com.kh.project.cse.boot.domain.vo.Store;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface MemberService {
    int insertMember(Member member);

    Member loginMember(String memberId);

    int idCheck(String checkId);

    int insertStore(String storeName);

    int checkStoreName(String checkStore);

    int checkStoreStatus(int storeNo);

    Store checkStore(Store store);

    //개인정보수정
    int updateMember(Member member);
    Member selectMemberById(String memberId);
    int updateMemberStatus(Member member);
    //직원정보 조회
    List<Member> selectMemberList();
    List<Member> selectMemberBykeyword(String keyword);

    int memberListCount();

    ArrayList<Member> selectHeadMemberList(PageInfo pi);

    ArrayList<Member> searchMember(String condition, String keyword, PageInfo pi);

    int searchMemberListCount(String condition, String keyword);
}
