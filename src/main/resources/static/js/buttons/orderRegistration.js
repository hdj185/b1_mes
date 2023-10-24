// 등록 버튼
var modal = document.getElementById("myModal");
var btn = document.getElementById("openModalBtn");
var span = document.getElementsByClassName("regClose")[0];

// 등록 버튼 클릭 시 모달 표시 / input 요소에 현재 날짜와 시간 설정
btn.onclick = function () {
  var currentDate = new Date();
  currentDate.setHours(currentDate.getHours() + 9);
  var currentDateTime = currentDate.toISOString().slice(0, 16);
  document.getElementById("regOrderCurrentTimeInput").value = currentDateTime;

  modal.style.display = "block";
  modal.style.opacity = 0; // 모달의 초기 투명도를 0으로 설정

  var opacity = 0;
  var intervalId = setInterval(function () {
    if (opacity >= 1) {
      clearInterval(intervalId); // 일정 시간 간격으로 모달의 투명도를 증가시키는 작업 중지
    } else {
      opacity += 0.1; // 투명도를 0.1씩 증가시킴
      modal.style.opacity = opacity;
    }
  }, 10); // 일정 시간 간격 (밀리초 단위, 조절 가능)
};

// 취소 버튼 눌렀을 때 모달 사라짐
span.onclick = function () {
  // 애니메이션을 위해 hide 클래스 추가
  modal.classList.add("hide");

  var opacity = 1;
  var intervalId = setInterval(function () {
    if (opacity <= 0) {
      clearInterval(intervalId); // 일정 시간 간격으로 모달의 투명도를 감소시키는 작업 중지
      modal.style.display = "none";
      modal.classList.remove("hide"); // 애니메이션 완료 후 hide 클래스 제거
    } else {
      opacity -= 0.1; // 투명도를 0.1씩 감소시킴
      modal.style.opacity = opacity;
    }
  }, 10); // 일정 시간 간격 (밀리초 단위, 조절 가능)
};

window.onclick = function (event) {
  if (event.target == modal) {
    // 애니메이션을 위해 hide 클래스 추가
    modal.classList.add("hide");

    var opacity = 1;
    var intervalId = setInterval(function () {
      if (opacity <= 0) {
        clearInterval(intervalId); // 일정 시간 간격으로 모달의 투명도를 감소시키는 작업 중지
        modal.style.display = "none";
        modal.classList.remove("hide"); // 애니메이션 완료 후 hide 클래스 제거
      } else {
        opacity -= 0.1; // 투명도를 0.1씩 감소시킴
        modal.style.opacity = opacity;
      }
    }, 10); // 일정 시간 간격 (밀리초 단위, 조절 가능)
  }
};
