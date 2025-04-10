package com.kh.project.cse.boot.service;

import com.kh.project.cse.boot.domain.vo.Attendance;
import com.kh.project.cse.boot.domain.vo.Expiry;
import com.kh.project.cse.boot.domain.vo.PageInfo;
import com.kh.project.cse.boot.domain.vo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SpotService {

    // 유통기한 페이지 수
    int selectExpiryCount(int storeNo);
    //유통기한 리스트 불러오기
    ArrayList<Expiry> selectExpiryList(int storeNo,PageInfo pi);
    // 유통기한 페이징
    int Expiry(int storeNo, int productNo, int inventoryCount, String expirationDate);

    //근태정보 조회 - 초기화면
    List<Attendance> selectInfoList();

    //근태정보 - 모달 수정버튼
    int updateWorkTime(Attendance attendance);
    //근태관리 - 출퇴근시간 업데이트
    int updateWorkingTime(Map<String, Object> paramMap); //출근
    int updateLeaveTime(Map<String, Object> paramMap); //퇴근

    //상품 전체 목록 수
    int ProductListCount();

    //발주요청 - 카테고리 목록
    List<Category> orderRequestCategoryList();
    //발주요청 - 상품 목록
    ArrayList<Product> orderRequestProductList();
    //발주요청 - 상품 검색
    ArrayList<Product> orderRequestProductSearch(String category, String keyword);
    //발주요청 - 발주
    int insertOrder(List<Circulation> orderList, int storeNo, String setNo);

    //발주요청 - 목록
    //발주요청 - 분단위로 묶은 수
    int orderRequestListCount(int storeNo);
    //발주요청 - 분단위 묶음 목록
    ArrayList<Circulation> orderRequestList(PageInfo pi, int storeNo);

    //발주요청 - 목록 검색
    //발주요청 - 검색한 발주 요청 분단위로 묶은 목록
    int orderSearchListCount(int storeNo, String setNo, Integer status, Date startDate, Date endDate);
    //검색한 발주 요청 목록
    ArrayList<Circulation> orderSearchList(PageInfo pi, int storeNo, String setNo, Integer status, Date startDate, Date endDate);

    //발주요청 발주 상세
    ArrayList<Circulation> spotOrderDetail(String setNo);

    //발주요청 - 지난달 발주 목록 검색
    List<Circulation> previousMonthOrder(LocalDate startDate, LocalDate endDate, int storeNo);

    //매출집계 - 검색
    List<Circulation> getSalesByMonth(int storeNo, LocalDate startDate, LocalDate endDate);
    //매출집계 - 모달
    List<Circulation> getDetailsByDate(String date, int storeNo);

    //재고리스트카운트
    int inventoryCount(int storeNo);

    //재고불러오기
    ArrayList<Inventory> selectInventory(PageInfo pi, int storeNo);

    //재고검색
    int searchInventoryCount(int storeNo, String condition, String keyword, int check);

    ArrayList<Inventory> searchInventory(PageInfo pi, int storeNo, String condition, String keyword, int check);



    int searchExpiryListCount(String searchExpiry, String keyword, int storeNo);

    ArrayList<Expiry> searchExpiryList(String searchExpiry, String keyword, int storeNo, PageInfo pi);

    ArrayList<Product> spotSelectAllProduct(PageInfo pi);

    ArrayList<Product> spotSearchProduct(String inputcheck, String condition, String keyword, PageInfo pi);

    //공지사항 총수
    int selectAnnouncementCount();

    //공지사항불러오기
    ArrayList<Announcement> selectAnnouncementlist(PageInfo pi);


    int spotSearchProductCount(String inputcheck, String condition, String keyword);
}
