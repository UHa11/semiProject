package com.kh.project.cse.boot.service;

import com.kh.project.cse.boot.domain.vo.Attendance;
import com.kh.project.cse.boot.domain.vo.Expiry;
import com.kh.project.cse.boot.domain.vo.PageInfo;
import com.kh.project.cse.boot.domain.vo.*;
import com.kh.project.cse.boot.mappers.CirculationMapper;
import com.kh.project.cse.boot.mappers.ProductMapper;
import org.apache.ibatis.session.RowBounds;
import com.kh.project.cse.boot.mappers.AttendanceMapper;
import com.kh.project.cse.boot.mappers.ExpiryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class SpotServiceImpl implements SpotService {
    private final AttendanceMapper attendanceMapper;
    private final ExpiryMapper expiryMapper;
    private final CirculationMapper circulationMapper;
    private final ProductMapper productMapper;

    @Override
    public int selectExpiryCount(int storeNo) {
        return expiryMapper.selectExpiryCount(storeNo);
    }

    @Override
    public ArrayList<Expiry> selectExpiryList(int storeNo,PageInfo pi) {

        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset,pi.getBoardLimit());

        return expiryMapper.selectExpiryList(storeNo, rowBounds);
    }
    @Override
    public ArrayList<Expiry> searchExpiryList(String searchExpiry, String keyword, int storeNo, PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset,pi.getBoardLimit());

        return expiryMapper.searchExpiryList(searchExpiry,keyword,storeNo, rowBounds);
    }
    //근태정보 조회 - 초기화면
    @Override
    public List<Attendance> selectInfoList() {
        return attendanceMapper.selectInfoList();
    }
    //근태정보 - 모달 수정버튼
    @Override
    public int updateWorkTime(Attendance attendance) {
        return attendanceMapper.updateWorkTime(attendance);
    }
    //근태관리 - 출퇴근시간 업데이트 //출근
    @Override
    public int updateWorkingTime(Map<String, Object> paramMap) {
        return attendanceMapper.updateWorkingBtnTime(paramMap);
    }
    //근태관리 - 출퇴근시간 업데이트 //퇴근
    @Override
    public int updateLeaveTime(Map<String, Object> paramMap) {
        return attendanceMapper.updateWorkLeaveBtnTime(paramMap);
    }

    @Override
    public int ProductListCount() {
        return productMapper.ProductListCount();
    }

    @Override
    public List<Category> orderRequestCategoryList() {
        return productMapper.selectAllCategories();
    }

    @Override
    public ArrayList<Product> orderRequestProductList() {
//        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
//        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return productMapper.orderRequestProductList();
    }

    @Override
    public ArrayList<Product> orderRequestProductSearch(String category, String keyword) {
//        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
//        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return productMapper.orderRequestProductSearch(category, keyword);
    }

    @Override
    public int Expiry(int storeNo, int productNo, int inventoryCount, String expirationDate) {
        System.out.println(storeNo +" "+ productNo +" "+ inventoryCount +" "+ expirationDate);
        int result = 0;
        result = expiryMapper.insertExpiry(storeNo,productNo,inventoryCount);
        System.out.println("result:"+ result);
        if(result>0){
            return expiryMapper.deleteExpiry(storeNo,productNo,expirationDate);
        }
        return 0;
    }

    @Override
    public int insertOrder(List<Circulation> orderList, int storeNo, String setNo) {

        int result = 1;

        for (Circulation circulation : orderList) {
            System.out.println();
            int insertResult = circulationMapper.insertOrder(circulation, storeNo, setNo);
            if (insertResult == 0) {
                result = 0;
                break;
            }
        }
        return result;
    }

    @Override
    public int orderRequestListCount(int storeNo) {
        return circulationMapper.orderRequestListCount(storeNo);
    }

    @Override
    public ArrayList<Circulation> orderRequestList(PageInfo pi, int storeNo) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());

        return circulationMapper.orderRequestList(rowBounds, storeNo);
    }

    @Override
    public ArrayList<Circulation> orderSearch(int storeNo, String SetNo) {
        return null;
    }

    //매출집계 - 검색
    @Override
    public List<Circulation> getSalesByMonth(int storeNo, LocalDate startDate, LocalDate endDate) {
        return circulationMapper.selectSalesMonth(storeNo,startDate,endDate);
    }

    @Override
    public int searchExpiryListCount(String searchExpiry, String keyword,int storeNo) {
        return expiryMapper.searchExpiryListCount(searchExpiry, keyword, storeNo);
    }


}
