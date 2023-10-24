var currentDate = new Date();
var currentYear = currentDate.getFullYear();
var currentMonth = currentDate.getMonth();

var yearSelect = document.getElementById("year");
var monthSelect = document.getElementById("month");

yearSelect.value = currentYear;
monthSelect.value = currentMonth;

function updateCalendar() {
    var year = parseInt(yearSelect.value);
    var month = parseInt(monthSelect.value);

    createCalendar(year, month);
}

function createCalendar(year, month) {
    var container = document.getElementById("calendar-container");
    container.innerHTML = "";

    createHeader(container, year, month);
    createDateTable(container, year, month);
}

function createHeader(container, year, month) {
    // 헤더 생성
    var header = document.createElement("h2");
    header.innerHTML = year + "년 " + (month + 1) + "월";
    container.appendChild(header);
}

function createDateTable(container, year, month) {
    // 달력 테이블 생성
    var table = document.createElement("table");
    table.className = "calendar";
    container.appendChild(table);

    // 요일 헤더 생성
    var weekdays = ["일", "월", "화", "수", "목", "금", "토"];
    var tr = document.createElement("tr");
    for (var i = 0; i < weekdays.length; i++) {
        var th = document.createElement("th");
        th.innerHTML = weekdays[i];
        tr.appendChild(th);
    }
    table.appendChild(tr);

    // 날짜 채우기
    var firstDay = new Date(year, month, 1).getDay();
    var lastDate = new Date(year, month + 1, 0).getDate();

    var date = 1;
    for (var row = 0; row < 6; row++) {
        var tr = document.createElement("tr");

        for (var col = 0; col < 7; col++) {
            if (row === 0 && col < firstDay) {
                var td = document.createElement("td");
                tr.appendChild(td);
            } else if (date > lastDate) {
                break;
            } else {
                var td = document.createElement("td");
                td.innerHTML = date;

                // 클릭 이벤트 처리
                td.addEventListener("click", function () {
                    var clickedDate = new Date(year, month, parseInt(this.innerHTML));
                    // SweetAlert 팝업
                    Swal.fire({
                        title: "일일 재공 & capa 현황",
                        html: "제품명: 1000ea<br>제품명: 100ea<br>재료명: 100ea<br>작업 수량: 100Box",
                        icon: "info",
                        confirmButtonText: "확인"
                    });
                });

                if (
                    date === currentDate.getDate() &&
                    month === currentDate.getMonth() &&
                    year === currentDate.getFullYear()
                ) {
                    td.className = "today";
                }

                tr.appendChild(td);
                date++;
            }
        }

        table.appendChild(tr);
    }
}

createCalendar(currentYear, currentMonth);