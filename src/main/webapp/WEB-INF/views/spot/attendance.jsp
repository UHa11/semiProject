<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <link rel="stylesheet" href="../../../resources/css/btn.css" />
    <style>
      .waper {
        width: 100%;
        padding: 50px;
        font-family: 'Open Sans', sans-serif;
      }

      #top-manu {
        display: flex;
        justify-content: space-between;
      }
      #attendance-head {
        width: 100%;
        height: 10%;
        display: flex;
        justify-content: left;
        align-items: center;
        font-size: 60px;
        font-weight: bold;

        padding: 10px;
      }
      #attendance-head img {
        width: 70px;
        height: 70px;
        margin-right: 20px;
      }
      .main_name {
        font-weight: bolder;
        font-size: 50px;
        margin: 0;
      }

      #main {
        background-color: #d9d9d9;
        width: 100%;
        height: 90%;
        border-radius: 4px;
        padding: 50px 50px 100px;
      }
      #main-in {
        background-color: white;
        width: 100%;
        height: 100%;
        border-radius: 20px;
        padding: 50px;
      }
      @media screen and (max-width: 1800px) {
        #main-in {
          overflow-x: auto;
        }
      }
      #main-pageing {
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
      }
      #main-pageing img {
        width: 70px;
        padding: 10px;
      }
      #main-pageing img:nth-of-type(2) {
        transform: rotate(180deg);
      }

      #main-pageing button {
        border: #b4b4b4 solid 3px;
        background: white;
        color: #b4b4b4;
        font-weight: bold;
        margin: 5px;
      }
      #main-pageing button:hover {
        border: #3c3c3c solid 3px;
        color: #3c3c3c;
        font-weight: bold;
      }
      #btn-close-modal {
        width: 20px;
        height: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
      }
      #x_img {
        width: 10px;
        height: 10px;
      }
      /* 화면기준 */
      #staticBackdrop {
        width: 350px;
        height: 700px;
        overflow: hidden;
        position: fixed;
        left: 70%;
        top: 50%;
        transform: translate(-50%, -50%);
      }
      .search-input-gray {
        font-weight: normal;
        margin: 5px 0;
      }

      .modal-body {
        display: inline-block;
        margin: auto;
      }
      .modal-body-main > p {
        width: 280px;
      }
      .search-input-gray {
        width: inherit;
      }
      .modal-header,
      .modal-footer {
        background-color: #d9d9d9;
      }

      .modal-footer > div {
        display: flex;
        gap: 5px;
        justify-content: center;
        margin: auto;
      }

      table th:nth-child(6),
      table td:nth-child(6) {
        padding-right: 0px;
      }

      table th:nth-child(8),
      table td:nth-child(8) {
        padding-left: 0px;
      }

      input[type='text'], .gowork-btn:active, .gowork-btn:focus,.gohome-btn:active, .gohome-btn:focus{
       outline: none;
      }
    .modal-body-main input {
        padding-left: 5px;
    }
    </style>
  </head>
  <body>
    <jsp:include page="../common/header.jsp" />
    <div class="waper">
      <div id="top-manu">
        <div id="attendance-head">
          <img src="/resources/menu_icons/근태관리 아이콘.png" />
          <p>근태관리</p>
        </div>
        <button class="gowork-btn" onclick="changebtn(this)">출근</button>
        <button
          class="gohome-btn"
          onclick="changebtn(this)"
          style="display: none"
        >
          퇴근
        </button>
      </div>
      <div id="main">
        <div id="main-in">
          <table class="table table-hover" id="table1">
            <colgroup>
              <col style="width: 10%" />
              <col style="width: 10%" />
              <col style="width: 10%" />
              <col style="width: 16%" />
              <col style="width: 10%" />
              <col style="width: 20%" />
              <col style="width: 4%" />
              <col style="width: 20%" />
            </colgroup>
            <thead>
              <tr>
                <th>직급</th>
                <th>아이디</th>
                <th>이름</th>
                <th>전화번호</th>
                <th>재직</th>
                <th>출근시간&nbsp;</th>
                <th>~</th>
                <th>&nbsp;퇴근시간</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>알바</td>
                <td>abc</td>
                <td>이인혜</td>
                <td>010-1111-2222</td>
                <td>Y</td>
                <td>2025-03-03 10:00:00</td>
                <td>~</td>
                <td>2025-03-03 18:00:00</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div id="main-pageing">
          <img src="/resources/common/공통_페이징바화살표.png" />
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
          <img src="/resources/common/공통_페이징바화살표.png" />
        </div>
      </div>

      <!--start point-->
      <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"
      ></script>

      <!-- Modal -->
      <div
        class="modal fade"
        id="staticBackdrop"
        data-bs-backdrop="static"
        data-bs-keyboard="false"
        tabindex="-1"
        aria-labelledby="staticBackdropLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <div id="header-title">
                <h1 class="modal-title fs-4">근태 수정</h1>
              </div>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
                id="btn-close-modal"
              >
                <img
                  src="<c:url value='/resources/common/공통_Icon.png'/>"
                  id="x_img"
                />
              </button>
            </div>
            <div class="modal-body">
              <div class="modal-body-main">
                <!--모달 내용-->
                <p>
                  <strong>직급</strong> <br />
                  <input type="text" id="modal-rank" class="search-input-gray" readonly />
                </p>
                <p>
                  <strong>아이디</strong><br />
                  <input
                    type="text"
                    id="modal-id"
                    class="search-input-gray"
                    readonly
                  />
                </p>
                <p>
                  <strong>이름</strong> <br />
                  <input
                    type="text"
                    id="modal-name"
                    class="search-input-gray"
                    readonly
                  />
                </p>

                <p>
                  <strong>출근시간</strong><br />
                  <input
                    type="datetime-local"
                    id="modal-working_time"
                    class="search-input-gray"
                  />
                </p>
                <p>
                  <strong>퇴근시간</strong><br />
                  <input
                    type="datetime-local"
                    id="modal-workout_time"
                    class="search-input-gray"
                  />
                </p>
              </div>
            </div>

            <div class="modal-footer">
              <div>
                <button type="button" class="gray-btn" id="footer-btn">
                  수정
                </button>
                <button type="button" class="gray-btn" id="footer-btn">
                  삭제
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <script>
        document.addEventListener('DOMContentLoaded', function () {
          const rows = document.querySelectorAll('#table1 tbody tr');

          rows.forEach((row) => {
            row.classList.add('table-row');

            row.addEventListener('click', function () {
              const rank = this.children[0].textContent;
              const id = this.children[1].textContent;
              const name = this.children[2].textContent;
              const working_time = this.children[5].innerText;
              const workout_time = this.children[7].innerText;

              document.getElementById('modal-rank').value = rank;
              document.getElementById('modal-id').value = id;
              document.getElementById('modal-name').value = name;
              document.getElementById('modal-working_time').value =
                working_time;
              document.getElementById('modal-workout_time').value =
                workout_time;

              const modal = new bootstrap.Modal(
                document.getElementById('staticBackdrop')
              );
              modal.show();
            });
          });
        });

        function changebtn(this_btn) {

            if (this_btn.classList.contains('gowork-btn')) {
              this_btn.innerText = '퇴근';
              this_btn.classList.remove('gowork-btn');
              this_btn.classList.add('gohome-btn');
            } else {
              this_btn.innerText = '출근';
              this_btn.classList.remove('gohome-btn');
              this_btn.classList.add('gowork-btn');
            }

          // var today = new Date();
          // var year = today.getFullYear();
          // var month =('0'+ (today.getMonth() +1)).slice(-2);
          // var day =  ('0'+ today.getDate()).slice(-2);
          // var hour =('0'+ today.getHours()).slice(-2);
          // var min =('0'+ today.getMinutes() ).slice(-2);
          // var sec =('0'+ today.getSeconds()).slice(-2);

          // var dateString = year +'-'+ month +'-'+day +" "+hour+":"+min+":"+sec;
        }
      </script>
    </div>
  </body>
</html>
