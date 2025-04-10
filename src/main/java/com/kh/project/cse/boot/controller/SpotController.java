package com.kh.project.cse.boot.controller;

import com.kh.project.cse.boot.domain.vo.*;
import com.kh.project.cse.boot.service.HeadService;
import com.kh.project.cse.boot.service.MemberService;
import com.kh.project.cse.boot.service.SpotService;
import com.kh.project.cse.boot.service.SpotService;
import com.kh.project.cse.boot.service.SpotServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;




@RequiredArgsConstructor
@Controller
public class SpotController {
    private final MemberService memberService;
    private final SpotService spotService;
    private final HeadService headService;
    private Member member;


    //대시보드
    @RequestMapping("/spot_dashboard")
    public String spot_dashboard() {
        return "spot/spotDashboard";
    }

    //상품목록
    @RequestMapping("/spot_product")
    public String spot_product(@RequestParam(defaultValue = "1") int cpage,Model model) {

        int listCount = spotService.ProductListCount();

        PageInfo pi = new PageInfo(listCount,cpage, 10,10);
        ArrayList<Product> list = spotService.spotSelectAllProduct(pi);

        model.addAttribute("list",list);
        model.addAttribute("pi", pi);

        return "spot/spotProduct";
    }
    @PostMapping("/spotSearchForm")
    public String spotSearchProduct(@RequestParam(defaultValue = "1") int cpage,@RequestParam(defaultValue = "Y") String inputcheck, @RequestParam String condition, @RequestParam String keyword, Model model) {

        int listCount = spotService.ProductListCount();
        PageInfo pi = new PageInfo(listCount,cpage, 10,10);

        ArrayList<Product> list = new ArrayList<>();
        inputcheck = inputcheck.equals("on")? "N" : "Y";

        if(inputcheck.equals("Y")){
            list = headService.searchProduct(condition, keyword, pi);
        }else {
            list = spotService.spotSearchProduct(inputcheck, condition, keyword, pi);
        }

        model.addAttribute("list",list);
        model.addAttribute("pi", pi);
        return "spot/spotProduct";
    }

    //출고
    @RequestMapping("/spot_output")
    public String spot_output(@RequestParam(defaultValue = "1") int cpage, Model model, HttpSession session) {
        Member member = (Member)session.getAttribute("loginMember");
        int storeNo;
        if(member != null){
            storeNo =  member.getStoreNo();
        } else {
            session.setAttribute("alertMsg", "비정상적인 접근입니다.");
            return "redirect:/loginForm";
        }
        int boardCount = spotService.selectOutputCount(storeNo);
        PageInfo pi = new PageInfo(boardCount,cpage,10,10);
        ArrayList<Circulation> outputList = spotService.selectOutputList(storeNo,pi);

        model.addAttribute("outputList",outputList);
        model.addAttribute("pi",pi);
        model.addAttribute("until",LocalDate.now().toString());
        return "spot/spotOutput";
    }
    @RequestMapping("/search_output")
    public String spot_searchOutput(@RequestParam(defaultValue = "1") int cpage,
                                    @RequestParam Date since,@RequestParam Date until,
                                    @RequestParam int status,@RequestParam String searchOutput,@RequestParam String keyword,
                                    Model model, HttpSession session) {
        Member member = (Member)session.getAttribute("loginMember");
        int storeNo;
        if(member != null){
            storeNo =  member.getStoreNo();
        } else {
            session.setAttribute("alertMsg", "비정상적인 접근입니다.");
            return "redirect:/loginForm";
        }
        int boardCount = spotService.searchOutputCount(storeNo,since,until,status,searchOutput,keyword);
        PageInfo pi = new PageInfo(boardCount,cpage,10,10);
        ArrayList<Circulation> outputList = spotService.searchOutputList(storeNo,pi);

        model.addAttribute("outputList",outputList);
        model.addAttribute("pi",pi);
        return "spot/spotOutput";
    }
    //년매출
    @RequestMapping("/spot_salesYear")
    public String spot_salesYear() {
        return "spot/spotSalesYear";
    }

    //멤버관리
//    @RequestMapping("/spot_member")
//    public String spot_member() {
//        return "spot/spotMember";
//    }


    //재고
    @RequestMapping("/spot_inventory")
    public String spot_inventory(@RequestParam(defaultValue = "1") int cpage, Model model, HttpSession session) {
//        Member loginUser = (Member) session.getAttribute("loginUser");
//        int storeNo = loginUser.getStoreNo();
        int storeNo = 5;
        int inventoryCount = spotService.inventoryCount(storeNo);
        PageInfo pi = new PageInfo(inventoryCount, cpage, 10 , 12);
        ArrayList<Inventory> list = spotService.selectInventory(pi, storeNo);

        model.addAttribute("pi", pi);
        model.addAttribute("list", list);
        return "spot/spotInventory";
    }

    @PostMapping("/searchInventory")
    public String searchInventory(
            @RequestParam(defaultValue = "1") int cpage,
            @RequestParam String condition,
            @RequestParam String keyword,
            @RequestParam(value = "check", required = false, defaultValue = "0") int check,
            Model model,
            HttpSession session) {

        int storeNo = 5;

        // 조건에 따라 Service 호출
        int count = spotService.searchInventoryCount(storeNo, condition, keyword, check);
        PageInfo pi = new PageInfo(count, cpage, 10, 10);
        ArrayList<Inventory> list = spotService.searchInventory(pi, storeNo, condition, keyword, check);
        System.out.println("수"+count);
        model.addAttribute("list", list);
        model.addAttribute("pi", pi);
        return "spot/spotInventory";
    }

    //공지사항
    @RequestMapping("/spot_notice")
    public String spot_notice() {
        return "spot/spotAnnouncement";
    }

    //월매출
    @RequestMapping("/spot_sales")
    public String spot_sales() {
        return "spot/spotSales";
    }

    //유통기한
    @RequestMapping("/spot_expiration")
    public String spot_expiration(@RequestParam(defaultValue = "1") int cpage, Model model, HttpSession session) {
        Member member = (Member)session.getAttribute("loginMember");
        int storeNo;
        if(member != null){
            storeNo =  member.getStoreNo();
        } else {
            session.setAttribute("alertMsg", "비정상적인 접근입니다.");
            return "redirect:/loginForm";
        }
        int boardCount = spotService.selectExpiryCount(storeNo);
        PageInfo pi = new PageInfo(boardCount,cpage,10,10);
        ArrayList<Expiry> expiryList = spotService.selectExpiryList(storeNo,pi);

        model.addAttribute("expiryList",expiryList);
        model.addAttribute("pi",pi);

        return "spot/sporExpiration";
    }
    //유통기한 검색
    @RequestMapping("/searchExpiry")
    public String searchExpiry(@RequestParam(defaultValue = "1") int cpage, @RequestParam String searchExpiry,@RequestParam String keyword, HttpSession session, Model model){
        System.out.println(searchExpiry);

        Member member = (Member)session.getAttribute("loginMember");
        int storeNo;
        if(member != null){
            storeNo =  member.getStoreNo();
        } else {
            session.setAttribute("alertMsg", "로그아웃된 상태입니다. 다시 로그인 해주세요. ");
            return "redirect:/loginForm";
        }

        int boardCount = spotService.searchExpiryListCount(searchExpiry, keyword, storeNo);
        PageInfo pi = new PageInfo(boardCount,cpage,10,10);
        ArrayList<Expiry> expiryList = spotService.searchExpiryList(searchExpiry, keyword,storeNo,pi);

        model.addAttribute("expiryList",expiryList);
        model.addAttribute("pi",pi);
        model.addAttribute("searchExpiry",searchExpiry);
        model.addAttribute("keyword",keyword);

        return "spot/sporExpiration";
    }

    @RequestMapping("/inputmodal")
    public String inputmodal() {
        return "spotInputmodal";
    }

    @RequestMapping("/receiving")
    public String receiving() {
        return "receiving/receivingDetailsForm";
    }

    //발주
    @RequestMapping("/spot_order")
    public String spot_order(
            @RequestParam(defaultValue = "1") int cpage,
            @RequestParam(required = false) String setNo,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            HttpSession session,
            Model model) {

        Member loginUser = (Member) session.getAttribute("loginMember");
        int storeNo = loginUser.getStoreNo();

        //date값 보정
        if (startDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            startDate = cal.getTime();
        }
        if (endDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            endDate = cal.getTime();
        }
        List<Category> clist = spotService.orderRequestCategoryList();
        ArrayList<Product> plist = spotService.orderRequestProductList();

        ArrayList<Circulation> resultList;
        PageInfo pi;

        if (setNo != null || status != null || startDate != null || endDate != null) {
            int searchCount = spotService.orderSearchListCount(storeNo, setNo, status, startDate, endDate);
            pi = new PageInfo(searchCount, cpage, 10, 10);

            resultList = spotService.orderSearchList(pi, storeNo, setNo, status, startDate, endDate);

            model.addAttribute("olist", null);
            model.addAttribute("oslist", resultList);
            model.addAttribute("pi", pi);
        } else {
            int listCount = spotService.orderRequestListCount(storeNo);
            pi = new PageInfo(listCount, cpage, 10, 10);

            resultList = spotService.orderRequestList(pi, storeNo);

            model.addAttribute("olist", resultList);
            model.addAttribute("oslist", null);
            model.addAttribute("pi", pi);
        }
        model.addAttribute("clist", clist);
        model.addAttribute("plist", plist);

        model.addAttribute("setNo", setNo);
        model.addAttribute("status", status);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "spot/spotOrder";
    }
    @GetMapping("/spot_order/productSearch")
    @ResponseBody
    public ArrayList<Product> productSearch(@RequestParam("category") String category, @RequestParam("keyword") String keyword, Model model) {

        ArrayList<Product> pslist = spotService.orderRequestProductSearch(category, keyword);

        model.addAttribute("pslist", pslist);
        return pslist;
    }
    //발주 - 발주요청
    @PostMapping("/spot_order/requestOrder")
    public ResponseEntity<String> requestOrder(@RequestBody List<Circulation> orderList, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginMember");
        int storeNo = loginUser.getStoreNo();
        String setNo = storeNo + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));

        int result = 0;

        result = spotService.insertOrder(orderList, storeNo, setNo);
        if (result > 0) {
            return ResponseEntity.ok("발주 요청 완료");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("발주 요청 실패");
        }
    }
    //발주 - 발주 상세 내역
    @ResponseBody
    @GetMapping("/spot_order/orderDetail")
    public ArrayList<Circulation> spotOderDetail(@RequestParam("setNo") String setNo) {
        System.out.println(setNo);
        ArrayList<Circulation> sodlist = spotService.spotOrderDetail(setNo);
        for(Circulation c : sodlist){
            System.out.println(sodlist);
        }
        return sodlist;
    }
    //발주 - 지난 달 발주 목록
    @ResponseBody
    @PostMapping("/spot_order/previousMonthOrder")
    public List<Circulation> previousMonthOrder(
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginMember");
        int storeNo = loginUser.getStoreNo();

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return spotService.previousMonthOrder(start, end, storeNo);
    }


    //입고
    @RequestMapping("/spot_input")
    public String spot_input() { return "spot/spotInput"; }

    //지점관리
    @PostMapping("/update_member")
    @ResponseBody
    public String update_member(@RequestBody Member member) {
        int result = memberService.updateMember(member);

        if (result > 0) {
            return "성공";
        }else {
            return "실패";
        }
    }

    //직원정보 조회
    @GetMapping("/spot_member")
    public String spotMemberInfo(@RequestParam(required = false) String keyword, Model model) {
        List<Member> memberList;

        if (keyword != null && !keyword.trim().isEmpty()) {
            memberList = memberService.selectMemberBykeyword(keyword);
        } else {
            memberList = memberService.selectMemberList();
        }

        model.addAttribute("memberList", memberList);
        model.addAttribute("keyword", keyword);

        return "spot/spotMember";
    }
    //근태정보 조회 - 초기화면
    @GetMapping("/spot_attendance")
    public String spotAttendanceInfo(@SessionAttribute("loginMember") Member loginMember, Model model) {
        List<Attendance> attendanceList = spotService.selectInfoList();
        model.addAttribute("loginMember", loginMember);
        System.out.println("attendanceList: " + attendanceList);

        model.addAttribute("attendanceList", attendanceList);

        return "spot/spotAttendance";
    }
    //근태정보 - 모달 수정버튼
    @PostMapping("/spot_attendance/updateAttendance")
    @ResponseBody
    public Map<String, Object> updateAttendanceNow(@RequestBody Map<String,Object> payload) {
        String memberId = (String) payload.get("memberId");
        String workingTimeStr = (String) payload.get("workingTime");
        String workoutTimeStr = (String) payload.get("workoutTime");

        System.out.println("업데이트 요청 받은 memberId: " + memberId);
        System.out.println("workingTimeStr: " + workingTimeStr);
        System.out.println("workoutTimeStr: " + workoutTimeStr);

        Date workingTime = null;
        Date workoutTime = null;
        try {
            if (workingTimeStr != null) {
                workingTime = Date.from(LocalDateTime.parse(workingTimeStr).atZone(ZoneId.systemDefault()).toInstant());
            }
            if (workoutTimeStr != null) {
                workoutTime = Date.from(LocalDateTime.parse(workoutTimeStr).atZone(ZoneId.systemDefault()).toInstant());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Member member = new Member();
        member.setMemberId(memberId);

        Attendance attendance = new Attendance();
        attendance.setMember(member);
        attendance.setWorkingTime(workingTime);
        attendance.setWorkoutTime(workoutTime);

        int result = spotService.updateWorkTime(attendance);

        Map<String, Object> response = new HashMap<>();
        response.put("result", result > 0 ? "success" : "fail");
        return response;
    }
    //근태관리 - 출퇴근시간 업데이트
    @PostMapping("/spot_attendance/updateTime")
    @ResponseBody
    public Map<String, Object> updateAttendanceTime(@RequestBody Map<String, String> request) {
        String memberId = request.get("memberId");
        String time = request.get("time");
        String type = request.get("type");

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.parse(time));

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberId", memberId);
        paramMap.put("time", timestamp);

        int result = 0;
        if ("출근".equals(type)) {
            result = spotService.updateWorkingTime(paramMap);
        } else if ("퇴근".equals(type)) {
            result = spotService.updateLeaveTime(paramMap);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("result", result == 1 ? "success" : "fail");
        return response;
        }

        //매출 집계 - 검색
        @GetMapping("/spot_sales")
        public String spotSales(
                @RequestParam(value = "startMonth", required = false) String startMonth,
                @RequestParam(value = "endMonth", required = false) String endMonth,
                HttpSession session,
                Model model
        ) {
            if (startMonth == null || endMonth == null) {
                String thisMonth = LocalDate.now().toString().substring(0, 7);
                return "redirect:/spot_sales?startMonth=" + thisMonth + "&endMonth=" + thisMonth;
            }

            Member loginUser = (Member) session.getAttribute("loginUser");
            int storeNo = loginUser.getStoreNo();

            LocalDate startDate = LocalDate.parse(startMonth + "-01");
            LocalDate endDate = YearMonth.parse(endMonth).plusMonths(1).atDay(1); // 다음 달 1일

            List<Circulation> result = spotService.getSalesByMonth(storeNo, startDate, endDate);

            model.addAttribute("list", result);
            model.addAttribute("startMonth", startMonth);
            model.addAttribute("endMonth", endMonth);
            System.out.println("로그인 유저: " + loginUser);
            System.out.println("조회기간: " + startDate + " ~ " + endDate.minusDays(1)); // 확인용 출력
            System.out.println("조회된 매출 데이터 건수: " + result.size());

            return "spot/spotSales";
        }

        //매출 집계 - 모달
        @GetMapping("/spot_sales/detail")
        @ResponseBody
        public List<Circulation> getDetail(@RequestParam("date") String date, HttpSession session) {
            Member loginUser = (Member) session.getAttribute("loginUser");
            int storeNo = loginUser.getStoreNo();
            return spotService.getDetailsByDate(date, storeNo);
        }







}
