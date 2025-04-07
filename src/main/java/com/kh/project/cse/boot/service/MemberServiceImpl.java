package com.kh.project.cse.boot.service;

import com.kh.project.cse.boot.domain.vo.Member;
import com.kh.project.cse.boot.domain.vo.PageInfo;
import com.kh.project.cse.boot.mappers.MemberMapper;
import com.kh.project.cse.boot.mappers.StoreMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    public  final MemberMapper memberMapper;
    public  final StoreMapper storeMapper;

    @Override
    public int insertMember(Member member) {
        return memberMapper.insertMember(member);
    }

    @Override
    public Member loginMember(String memberId) {
        return memberMapper.loginMember(memberId);
    }

    @Override
    public int idCheck(String checkId) {
        return memberMapper.idCheck(checkId);
    }

    @Override
    public int insertStore(String storeName) {
        return storeMapper.insertStore(storeName);
    }

    @Override
    public int checkStoreName(String checkStore) {
        return storeMapper.checkStore(checkStore);
    }

    @Override
    public int updateMember(Member member) {
        return memberMapper.updateMember(member);
    }
    
    @Override
    public Member selectMemberById(String memberId) {
        return memberMapper.selectMemberById(memberId);
    }

    @Override
    public int updateMemberStatus(Member member) {
        return memberMapper.updateMemberStatus(member);
    }

    @Override
    public List<Member> selectMemberList() {
        return memberMapper.selectMemberList();
    }

    @Override
    public int memberListCount() {
        return memberMapper.memberListCount();
    }

    @Override
    public ArrayList<Member> selectHeadMemberList(PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return memberMapper.selectHeadMemberList(rowBounds);
    }

    @Override
    public ArrayList<Member> searchMember(String condition, String keyword, PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return memberMapper.searchMember(condition,keyword,rowBounds);
    }




}
