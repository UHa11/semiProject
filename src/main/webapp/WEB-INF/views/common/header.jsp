<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>

    <!-- JavaScript -->
    <script src="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/alertify.min.js"></script>

    <!-- CSS -->
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/alertify.min.css"/>
    <!-- Default theme -->
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/default.min.css"/>
    <!-- Semantic UI theme -->
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/semantic.min.css"/>
    <!-- Bootstrap theme -->
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/bootstrap.min.css"/>


    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <!-- default css -->


    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <style>

      /* default css */

      /* default css */
      div{box-sizing: border-box}
      body{
        width: 100%;
        height: 100%;
        min-width: 1440px;
        min-height: 810px;

        max-width: 1920px;
        max-height: 1080px;
        display: flex;
        border: purple solid 5px;

      }
      #outer{

      }
      nav{
        width: 100%;
        min-width: 230px;
        max-width: 280px;
        height: 100%;
        min-height: 800px;
        max-height: 1080px;
        padding-right: 40px;

        background-color: #477154;
        /*border: red solid 1px;*/
        color: white;
      }
      nav ul{
        list-style-type: none;
      }
      nav ul>li{

      }
      nav ul>li a{
        text-decoration: none;
        color: white;
      }
      nav ul>li a:hover{
        text-decoration: none;
        color: white;
      }


      #pos{
        width: 100%;
        height: 5%;
        /*border: blue solid 1px;*/
        display: flex;
        padding-left: 10px;
        align-items: center
      }
      #logo{
        margin-left: 20px;
        width: 100%;
        height: 20%;
        /*border: orange solid 1px;*/
        display: flex;
        justify-content: center;
        align-items: center;
      }
      #logo img{
        /*width: 50%;*/
        /*height: 80%;*/
      }
      #mypage{
        margin-left: 20px;
        width: 100%;
        height: 10%;
        /*border: green solid 1px;*/
        font-size: 16px;

      }
      #mypage div{
        width: 100%;
        height: 50%;
        /*border: purple solid 1px;*/
        display: flex;
        justify-content: center;
        align-items: center;
      }


      #nav-menu{
        padding-top: 20px;
        font-weight: bold;
        height: 65%;
      }
      .menu-bar{
        padding-left: 30px;
        padding-bottom: 10px;
        display: flex;

        font-size: 22px;

      }
      .menu-bar li{
        font-size: 20px;
        padding-top: 2px;
        position: relative;
        right: 20px;
      }
      
    </style>
  </head>
  <body>
  <div id="outer">
    <nav>
      <div id="pos">
        <a><img src="/resources/menubar_icons/포스 연동 버튼.png"></a>
      </div>
      <div id="logo">
        <a href="dashboard"><img src="/resources/menubar_icons/로고.png"></a>
      </div>
      <div id="mypage">
        <div>
          OO지점 사장님
        </div>
        <div>
<%--          <button>개인정보수정</button>--%>
<%--          <button>로그아웃</button>--%>
          <img src="/resources/menubar_icons/개인정보수정.png">
          <img src="/resources/menubar_icons/로그아웃.png">
        </div>
      </div>
      <div id="nav-menu">
        <div class="menu-bar" id="menu-1">
          <a>공지사항</a>
        </div>
        <div class="menu-bar" id="menu-2">
          <div>
            직원관리
            <div>
              <ul>
                <li><a><img src="/resources/menubar_icons/직원정보 아이콘.png"> 직원정보</a></li>
                <li><a><img src="/resources/menubar_icons/근태관리 아이콘.png"> 근태관리</a></li>
              </ul>
            </div>
          </div>
        </div>
        <div class="menu-bar" id="menu-3">
          <div>
            재고관리
            <div>
              <ul>
                <li><a href="https://naver.com"><img src="/resources/menubar_icons/대시보드 아이콘.png"> 대시보드</a></li>
                <li><a><img src="/resources/menubar_icons/발주 아이콘.png"> 발주</a></li>
                <li><a><img src="/resources/menubar_icons/입고 아이콘.png"> 입고</a></li>
                <li><a><img src="/resources/menubar_icons/출고 아이콘.png"> 출고</a></li>
                <li><a><img src="/resources/menubar_icons/유통기한 아이콘.png"> 유통기한 관리</a></li>
                <li><a><img src="/resources/menubar_icons/재고 아이콘.png"> 재고</a></li>
                <li><a><img src="/resources/menubar_icons/매출집계 아이콘.png"> 매출 집계</a></li>
                <%--              <li><a>폐기 상품</a></li>--%>
                <li><a><img src="/resources/menubar_icons/전체 상품목록.png"> 전체 상품 목록</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </nav>

  </div>
  </body>
</html>
