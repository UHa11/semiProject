package com.kh.project.cse.boot.service;

import com.kh.project.cse.boot.domain.vo.*;

import java.util.ArrayList;
import java.util.List;

public interface HeadService {
    //상품추가
    int insertProduct(Product product);
    //상품검색
    ArrayList<Product> searchProduct(String condition, String keyword);
    //전체상품목록
    ArrayList<Product> selectAllProduct(PageInfo pi);

    //카테고리목록
    List<Category> selectAllCategories();
    //상품전체목록수
    int ProductListCount();






    //공지사항추가
    int insertAnnouncement(Announcement announcement);

    //공지사항총수
    int selectAnnouncementCount();

    //공지사항불러오기
    ArrayList<Announcement> selectAnnouncementlist(PageInfo pi);

    //공지사항 세부
    Announcement selectDetailAnnouncement(int announcementNo);



    int updateProduct(Product product);

    //발주관리사항 총수
    int selectcirculation();

    //발주관리사항 불러오기
    ArrayList<Circulation> selectCirculationlist(PageInfo pi);

}
