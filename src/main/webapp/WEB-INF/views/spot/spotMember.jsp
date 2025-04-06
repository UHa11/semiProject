<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>지점 직원관리</title>
    <link rel="stylesheet" href="/resources/css/btn.css">
    <style>
        .waper{
            width:100%;
            padding: 50px;
        }
        .main_name{
            font-weight: bolder;
            font-size: 50px;
            margin: 0;
        }
        #top-manu{
            display: flex;
            justify-content: space-between;
            padding-left: 50px;
        }
        #top-left{
            background-color: #D9D9D9;
            width: 60%;
        }
        #top-left1{
            background-color: white;
            display: flex;
            align-content: center;
            align-items: center;
            width: 100%;
            border-bottom-right-radius: 20px;
            padding-bottom: 10px;
        }

        #top-right1{
            background-color: #D9D9D9;
            display: flex;
            flex:1;
            justify-content: flex-end;
            align-items: center;
            border-top-left-radius: 20px;
            border-top-right-radius: 20px;
            padding-left: 50px;
            padding-right: 50px;
        }
        #top-left p {
            padding-left: 15px;
        }
        #top-right1 input[type=text]{
            display: flex;
            flex: 1;
            font-size: 24px;

        }
        #top-right1 input[type=submit]{
            margin: 0 0 0 20px;
            font-size: 24px;
        }
        #main{
            background-color: #D9D9D9;
            width: 100%;
            height: 90%;
            border-top-left-radius: 20px;
            border-bottom-left-radius: 20px;
            border-bottom-right-radius: 20px;
            padding: 50px 50px 100px;
        }
        #main-in{
            background-color: white;
            width: 100%;
            height: 100%;
            border-radius: 20px;
            padding: 50px;
        }

        #main-pageing{
            padding-top: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        #table1 {
            text-align: center;
            outline: none;
            width: 100%;
            table-layout: auto;
            font-size: 24px;
        }
        #table1 td{
            padding: 10px;
        }
        #table1 th{
            padding: 14px;
        }

        #main-pageing img{
            width: 70px;
            padding: 10px;
        }
        #main-pageing img:nth-of-type(2) {
            transform: rotate(180deg);
        }

        #main-pageing button{
            border: #B4B4B4 solid 3px;
            background: white;
            color: #B4B4B4;
            font-weight: bold;
            margin: 5px;
        }
        #main-pageing button:hover{
            border: #3C3C3C solid 3px;
            color: #3C3C3C;
            font-weight: bold;
        }
        .modal-dialog {
            position: absolute;
            top:5%;
            left: 20%;
            transform: translateX(20%);
        }
             /* modal */
         #editMember_modal-dialog {
             display: flex;
             height: 90vh;
             width: 100%;
             flex-direction: row;
             max-width: 24%;
         }

        #editMember_modal-header,#editMember_modal-footer{
            background-color: #D9D9D9;
        }

        #editMember_modal-dialog {
            position: absolute;
            transform: translateX(20%);
            left: 60%;
        }

        /* modal-header */
        #editMember_header-title{
            display: flex;
            width: 100%;
            margin: auto;
            justify-content: center;
            align-items: flex-start;
        }

        #editMember_header-name{
            display: flex;
            width: 100%;
        }

        #btn-close-modal{
            display: flex;
            justify-content: flex-start;
            align-items: flex-start;
        }

        #editMember_header-name p{
            display: flex;
            width: 100%;
            font-size: 40px;
            font-weight: bold;
            align-items: center;
            justify-content: center;
        }

        #editMember_x_img{
            width: 15px;
            height: 17px;
        }

        #editMember_modal-header{
            padding-left: 10%;
            height: 12%;
        }
        /* //modal-header */

        /* modal-body */
        #editMember_modal-body{
            display: flex;
            width: 100%;
            height: 60%;
            padding-left: 10%;
            padding-right: 10%;
            flex-direction: column;
        }

        #editMember_modal-content {
            display: flex;
            flex-direction: column;
            gap: 4px;
            width: 100%;
        }

        #editMember_modal-content strong {
            font-size: 18px;
            margin-bottom: 5px;
            margin-top: 10px;
        }

        #editMember_modal-content input {
            padding: 8px 12px;
            font-size: 18px;
            border: 1px solid #ccc;
            border-radius: 5px;
            outline: none;
            text-align: center;
            display: flex;
            width: 100%;
            height: 100%;
            margin-bottom: 10px;
        }

        #editMember_modal-content input[readonly] {
            background-color: #f8f8f8;
            color: #777;
            border: 1px solid #ddd;
        }

        #editMember_select_wrap {
            display: flex;
            height: 40px;
            flex-direction: row-reverse;
            justify-content: space-between;
            margin-bottom: 5px;
        }

        #editMember_select {
            display: flex;
            width: 60%;
            height: 100%;
            flex-direction: row;
            justify-content: flex-end;
        }

        #editMember_select-modal-rank, #editMember_select-modal-status {
            display: flex;
            height: 100%;
            width: 40%;
            border: 1px solid #ccc;
            border-radius: 10px;
            text-align: center;
        }

        #editMember_select_warp{
            display: flex;
            width: 100%;
            height: 40px;
        }

        /* //modal-body */
        /* modal-footer */
        #editMember_modal-footer{
            display: flex;
            height: 8%;
            padding-left: 10%;
            padding-right: 10%;
            align-items: center;
            justify-content: center;
        }

        #editMember_edit_btn {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .gray-long-btn {
            width: 100%;
            height: 100%;
            font-size: 30px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            margin-top: 0;
        }

        #btn-close-modal{
            border : none;
            background: none;
        }

        .modal-content{
            height: 80vh;
        }
        /* //modal-footer */
        /* //modal */
    </style>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="waper">
    <div id="top-manu">
        <div id="top-left">
            <div id="top-left1">
                <img src="../resources/menu_icons/직원 아이콘.png">
                <p class="main_name">직원 정보</p>
            </div>
        </div>

        <div id="top-right1">
            <input class="search-input" type="text" placeholder="아이디 or 이름 "/>
            <input class="search-input-submit" type="submit" value="검색" />
        </div>

    </div>
    <div id="main">
        <div id="main-in">
            <table class="table table-hover table-sm" id="table1">
                <colgroup>
                    <col style="width: 20%;">
                    <col style="width: 20%;">
                    <col style="width: 20%;">
                    <col style="width: 20%;">
                    <col style="width: 20%;">
                </colgroup>
                <thead>
                <tr>
                    <th class="col-2">직급</th>
                    <th class="col-2">아이디</th>
                    <th class="col-2">이름</th>
                    <th class="col-4">전화번호</th>
                    <th class="col-1">재직</th>
                </tr>
                </thead>

                <tbody >
                <c:forEach var="member" items="${memberList}">
                    <tr data-residentno="${member.residentNo}"><%--주민번호 같이 전달--%>
                        <td>
                            <c:choose>
                                <c:when test="${member.position == '1'}">본사</c:when>
                                <c:when test="${member.position == '2'}">지점장</c:when>
                                <c:when test="${member.position == '3'}">매니저</c:when>
                                <c:when test="${member.position == '4'}">알바</c:when>
                                <c:otherwise>없음</c:otherwise>
                            </c:choose>
                        </td>
                            <td>${member.memberId}</td>
                            <td>${member.memberName}</td>
                            <td>${member.phone}</td>
                        <td>
                        <c:choose>
                            <c:when test="${member.status == 'Y'}">재직</c:when>
                            <c:when test="${member.status == 'N'}">퇴직</c:when>
                            <c:otherwise>없음</c:otherwise>
                        </c:choose>
                        </td>
                    </tr>
                </c:forEach>



                </tbody>
            </table>
        </div>
        <div id="main-pageing">
            <img src="/resources/common/공통_페이징바화살표.png">
            <button type="button" class="btn btn-outline-secondary">1</button>
            <button type="button" class="btn btn-outline-secondary">2</button>
            <button type="button" class="btn btn-outline-secondary">3</button>
            <button type="button" class="btn btn-outline-secondary">4</button>
            <button type="button" class="btn btn-outline-secondary">5</button>
            <button type="button" class="btn btn-outline-secondary">6</button>
            <button type="button" class="btn btn-outline-secondary">7</button>
            <button type="button" class="btn btn-outline-secondary">8</button>
            <button type="button" class="btn btn-outline-secondary">9</button>
            <button type="button" class="btn btn-outline-secondary">10</button>
            <img src="/resources/common/공통_페이징바화살표.png">
        </div>
    </div>

    <!--start point-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

    <!-- Modal -->
    <div class="modal fade" id="edit_member" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog" id="editMember_modal-dialog">
            <div class="modal-content">
                <div class="modal-header" id="editMember_modal-header">
                    <div id="editMember_header-title">
                        <div id="editMember_header-name">
                            <p class="modal-title fs-5">직원정보 수정</p>
                        </div>
                    </div>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="btn-close-modal">
                        <img src="<c:url value='/resources/common/공통_Icon.png'/>" id="editMember_x_img">
                    </button>
                </div>
                <div class="modal-body" id="editMember_modal-body">
                    <!--모달 내용-->
                    <div>
                        <div id="editMember_modal-content">
                            <div id="editMember_select_wrap">
                                <div id="editMember_select">
                                <select id="editMember_select-modal-rank">
                                    <option value="본사" hidden>본사</option>
                                    <option value="직급">직급</option>
                                    <option value="알바">알바</option>
                                    <option value="매니저">매니저</option>
                                    <option value="지점장">지점장</option>
                                </select>
                                </div>
                                <strong>이름</strong>
                            </div>
                            <input type="text" id="member_name" placeholder="이름가져오기" readonly="">
                            <strong>휴대폰</strong>
                            <input type="text" id="member_phone" placeholder="010-0000-0000" readonly="">
                            <strong>주민번호</strong>
                            <input type="text" id="member_identify" placeholder="주민번호가져오기 : 121212-1******" readonly="">
                            <strong>아이디</strong>
                            <input type="text" id="member_id" placeholder="아이디가져오기" readonly="">
                            <strong>재직상황</strong>
                            <div id="editMember_select_warp">
                                <select id="editMember_select-modal-status">
                                    <option value="Y">Y</option>
                                    <option value="N">N</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer" id="editMember_modal-footer">
                    <button type="button" class="gray-long-btn" id="editMember_edit_btn">수정</button>
                </div>
            </div>

        </div>
    </div>


    <script>
        document.addEventListener("DOMContentLoaded", function () {

            const rows = document.querySelectorAll("#table1 tbody tr");

            rows.forEach(row => {
                row.classList.add("table-row");

                row.addEventListener("click", function () {
                    const rank = this.children[0].textContent.trim();
                    const id = this.children[1].textContent.trim();
                    const name = this.children[2].textContent.trim();
                    const phone = this.children[3].textContent.trim();
                    const status = this.children[4].textContent.trim();

                    const statusValue = (status === "재직") ? "Y" : (status === "퇴직") ? "N" : "";

                    const residentNo = this.dataset.residentno;
                    document.getElementById("member_identify").value = residentNo;

                    document.getElementById("editMember_select-modal-rank").value = rank;
                    document.getElementById("member_name").value = name;
                    document.getElementById("member_phone").value = phone;
                    document.getElementById("member_id").value = id;

                    document.getElementById("editMember_select-modal-status").value = statusValue;

                    const modal = new bootstrap.Modal(document.getElementById("edit_member"));
                    modal.show();
                });
            });
        });


        document.getElementById("editMember_edit_btn").addEventListener("click", function () {
            const data = {
                memberId: document.getElementById("member_id").value,
                position: document.getElementById("editMember_select-modal-rank").value,
                status: document.getElementById("editMember_select-modal-status").value
            };
            fetch("/update_member", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            })
                .then(res => res.text())
                .then(result => {
                    if (result.trim() === "성공") {
                        alert("직원 정보가 수정되었습니다.");
                        location.reload();
                    } else {
                        alert("수정 실패: " + result);
                    }
                });
        });


    </script>
    <!--end point-->

</div>
</body>
</html>
