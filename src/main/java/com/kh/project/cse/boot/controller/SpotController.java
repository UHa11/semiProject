package com.kh.project.cse.boot.controller;

import com.kh.project.cse.boot.domain.vo.*;
import com.kh.project.cse.boot.service.HeadService;
import com.kh.project.cse.boot.service.MemberService;
import com.kh.project.cse.boot.service.SpotService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;




@RequiredArgsConstructor
@Controller
public class SpotController {
    private final MemberService memberService;
    private final SpotService spotService;
    private final HeadService headService;
    private Member member;


    //대시보드
    @RequestMapping("/spot_dashboard")
    public String spot_dashboard(HttpSession session, Model model) {
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
    //상품검색
    @RequestMapping("/spotSearchForm")
    public String spotSearchProduct(@RequestParam(defaultValue = "1") int cpage,@RequestParam(defaultValue = "Y") String inputcheck, @RequestParam String condition, @RequestParam String keyword, Model model) {

        ArrayList<Product> list ;
        PageInfo pi;
        int listCount = 1;
        inputcheck = inputcheck.equals("on")? "N" : "Y";

        if(inputcheck.equals("Y")){
            listCount = headService.searchProductCount(condition,keyword);
            pi = new PageInfo(listCount,cpage, 10,10);
            list = headService.searchProduct(condition, keyword, pi);
        }else {
            listCount = spotService.spotSearchProductCount(inputcheck,condition,keyword);

            pi = new PageInfo(listCount,cpage, 10,10);
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
                                    @RequestParam(defaultValue = "") String lastMonth, @RequestParam(defaultValue = "") String today,
                                    @RequestParam(defaultValue = "") String since,@RequestParam(defaultValue = "") String until,
                                    @RequestParam(defaultValue = "") String OptionStatus,@RequestParam(defaultValue = "") String searchOutput,@RequestParam(defaultValue = "") String keyword,
                                    Model model, HttpSession session) {
        System.out.println(since +"_"+ until+"_"+OptionStatus+"_"+searchOutput+"_"+keyword );
        // 로그인 끊기면 로그아웃.
        Member member = (Member)session.getAttribute("loginMember");
        int storeNo;
        if(member != null){
            storeNo =  member.getStoreNo();
        } else {
            session.setAttribute("alertMsg", "비정상적인 접근입니다.");
            return "redirect:/loginForm";
        }
        
        //OptionStatus == null 이면 0 값
        int status = 0;
        if(!OptionStatus.isBlank()){
            status = Integer.parseInt(OptionStatus);
        }

        //최근
        LocalDate Today = LocalDate.now();
        if(!today.isBlank()){
           until = Today.toString();
        }
        //저번 달
        if(!lastMonth.isBlank()){
            LocalDate LastMonth = Today.minusMonths(1);
            until = LastMonth.toString();
        }

        int boardCount = spotService.searchOutputCount(storeNo,since,until,status,searchOutput,keyword);
        PageInfo pi = new PageInfo(boardCount,cpage,10,10);
        ArrayList<Circulation> outputList = spotService.searchOutputList(storeNo,since.trim(),until.trim(),status,searchOutput,keyword,pi);

        model.addAttribute("outputList",outputList);
        model.addAttribute("pi",pi);
        model.addAttribute("since",since);
        model.addAttribute("until", until);
        model.addAttribute("OptionStatus",status);
        model.addAttribute("searchOutput",searchOutput);
        model.addAttribute("keyword",keyword);

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
        Member loginUser = (Member) session.getAttribute("loginMember");
        int storeNo = loginUser.getStoreNo();
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
    public String spot_notice(@RequestParam(defaultValue = "1") int cpage, Model model) {

        int announcementCount = headService.selectAnnouncementCount();

        PageInfo pi = new PageInfo(announcementCount, cpage, 10 , 10);
        ArrayList<Announcement> list = headService.selectAnnouncementlist(pi);

        model.addAttribute("list", list);
        model.addAttribute("pi", pi);
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
        ArrayList<Circulation> odlist = spotService.spotOrderDetail(setNo);
        return odlist;
    }
    //발주 - 발주 취소(삭제)
    @ResponseBody
    @PostMapping("/spot_order/cancelOrder")
    public String cancelOrder(@RequestParam("setNo") String setNo, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginMember");
        int storeNo = loginUser.getStoreNo();

        int result = spotService.cancelOrder(setNo, storeNo);
        if (result > 0) {
            return "success";
        } else {
            return "fail";
        }
    }


    //입고
    @RequestMapping("/spot_input")
    public String spot_input(
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

        ArrayList<Circulation> resultList;
        PageInfo pi;

        if (setNo != null || status != null || startDate != null || endDate != null) {
            int searchCount = spotService.inputSearchListCount(storeNo, setNo, status, startDate, endDate);
            pi = new PageInfo(searchCount, cpage, 10, 10);

            resultList = spotService.inputSearchList(pi, storeNo, setNo, status, startDate, endDate);

            model.addAttribute("ilist", null);
            model.addAttribute("islist", resultList);
            model.addAttribute("pi", pi);
        } else {
            int listCount = spotService.inputListCount(storeNo);
            pi = new PageInfo(listCount, cpage, 10, 10);

            resultList = spotService.inputList(pi, storeNo);

            model.addAttribute("ilist", resultList);
            model.addAttribute("islist", null);
            model.addAttribute("pi", pi);
        }

        model.addAttribute("setNo", setNo);
        model.addAttribute("status", status);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "spot/spotInput";
    }
    //입고 상세
    @ResponseBody
    @GetMapping("/spot_order/inputDetail")
    public ArrayList<Circulation> inputDetail(@RequestParam("setNo") String setNo) {
        System.out.println(setNo);
        ArrayList<Circulation> idlist = spotService.inputDetail(setNo);

        return idlist;
    }
    //입고 완료
    @PostMapping("/spot_order/insertInput")
    public ResponseEntity<String> insertInput(@RequestBody List<Circulation> inputList, HttpSession session) {

        Member loginUser = (Member) session.getAttribute("loginMember");
        int storeNo = loginUser.getStoreNo();

        int result = 0;

        result = spotService.insertInput(inputList, storeNo);
        if (result > 0) {
            return ResponseEntity.ok("입고 완료");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입고 실패");
        }
    }




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
    //근태정보 조회
    @GetMapping("/spot_attendance")
    public String spotAttendanceInfo(@SessionAttribute("loginMember") Member loginMember, Model model) {
        List<Attendance> attendanceList = spotService.selectInfoList();
        model.addAttribute("loginMember", loginMember);

        model.addAttribute("attendanceList", attendanceList);

        return "spot/spotAttendance";
    }
    //근태정보 갱신
    @GetMapping("/spot_attendance/refresh")
    public String refreshAttendanceList(Model model) {
        List<Attendance> attendanceList = spotService.selectInfoList();
        model.addAttribute("attendanceList", attendanceList);
        return "spot/spotAttendance :: attendanceTable";  // 'attendanceTable' 부분만 반환
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
        String type = request.get("type");

        String timeString = request.get("time");
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(timeString);
        Timestamp timestamp = Timestamp.from(offsetDateTime.toInstant());

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberId", memberId);
        paramMap.put("time", timestamp);

        int result = 0;
        if ("출근".equals(type)) {
            int status = 1;
            paramMap.put("status", status);
            result = spotService.insertWorkingTime(paramMap);
        } else if ("퇴근".equals(type)) {
            int status = 2;
            paramMap.put("status", status);
            result = spotService.updateLeaveTime(paramMap);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", result == 1 ? "success" : "fail");
        return response;
        }
        //근태관리 - 출퇴근 상태 가져오기
        @GetMapping("/spot_attendance/getStatus")
        @ResponseBody
        public Map<String, Object> getAttendanceStatus(@RequestParam("memberId") String memberId) {
            Map<String, Object> response = new HashMap<>();

            int status = spotService.getAttendanceStatus(memberId);
            if(status == 1) {
                response.put("status", "출근");
            } else if(status == 2) {
                response.put("status","퇴근");
            } else {
                response.put("status","none");
            }
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

            Member loginMember = (Member) session.getAttribute("loginMember");
            int storeNo = loginMember.getStoreNo();

            LocalDate startDate = LocalDate.parse(startMonth + "-01");
            LocalDate endDate = YearMonth.parse(endMonth).plusMonths(1).atDay(1); // 다음 달 1일

            List<Circulation> result = spotService.getSalesByMonth(storeNo, startDate, endDate);

            model.addAttribute("list", result);
            model.addAttribute("startMonth", startMonth);
            model.addAttribute("endMonth", endMonth);
            System.out.println("로그인 유저: " + loginMember);
            System.out.println("조회기간: " + startDate + " ~ " + endDate.minusDays(1)); // 확인용 출력
            System.out.println("조회된 매출 데이터 건수: " + result.size());

            return "spot/spotSales";
        }

        //매출 집계 - 모달
        @GetMapping("/spot_sales/detail")
        @ResponseBody
        public List<Circulation> getDetail(@RequestParam("date") String date, HttpSession session) {
            Member loginMember = (Member) session.getAttribute("loginMember");
            int storeNo = loginMember.getStoreNo();
            return spotService.getDetailsByDate(date, storeNo);
        }

        //매출집계 - 년도별 초기 로딩
        @GetMapping("/spot_salesYear")
        public String spotSalesPage(Model model, HttpSession session) {
            Member loginMember = (Member) session.getAttribute("loginMember");
            int storeNo = loginMember.getStoreNo();
            String year = String.valueOf(LocalDate.now().getYear());

            List<Circulation> monthList = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                Circulation c = new Circulation();
                c.setMonth(String.valueOf(i));
                c.setTotalInput(0);
                c.setTotalSale(0);
                c.setTotalMargin(0);
                monthList.add(c);
            }

            // 실제 데이터 덮어쓰기
            List<Circulation> realData = spotService.getDetailsByDate(year, storeNo);
            for (Circulation real : realData) {
                int index = Integer.parseInt(real.getMonth()) - 1;
                monthList.set(index, real);
            }

            model.addAttribute("yearCurrent", year);
            model.addAttribute("circulationList", monthList);
            return "spot/spotSalesYear";
        }
        //년매출 집계 - 선택시
        @GetMapping("/spot_sales/year")
        @ResponseBody
        public List<Circulation> spotSales_year(@RequestParam("year") String year, HttpSession session) {
            Member loginMember = (Member) session.getAttribute("loginMember");
            int storeNo = loginMember.getStoreNo();

            List<Circulation> monthList = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                Circulation c = new Circulation();
                c.setMonth(String.valueOf(i));
                c.setTotalInput(0);
                c.setTotalSale(0);
                c.setTotalMargin(0);
                monthList.add(c);
            }

            // 실제 데이터 덮어쓰기
            List<Circulation> realData = spotService.getDetailsByDate(year, storeNo);
            for (Circulation real : realData) {
                int index = Integer.parseInt(real.getMonth()) - 1;
                monthList.set(index, real);
            }
            return monthList;
        }







}
