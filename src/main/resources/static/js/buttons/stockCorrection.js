// 수정 버튼
var stockCorModal = document.getElementById("stockCorModal");
var stockCorBtn = document.getElementById("stockCorBtn");
var stockCorClose = document.getElementsByClassName("stockCorClose")[0];

stockCorBtn.onclick = function () {
    stockCorModal.style.display = "block";
    stockCorModal.style.opacity = "0";

    var opacity = 0;
    var intervalId = setInterval(function () {
        if (opacity >= 1) {
            clearInterval(intervalId);
        } else {
            opacity += 0.1;
            stockCorModal.style.opacity = opacity;
        }
    }, 10);
};

stockCorClose.onclick = function () {
    var opacity = 1;
    var intervalId = setInterval(function () {
        if (opacity <= 0) {
            clearInterval(intervalId);
            stockCorModal.style.display = "none";
        } else {
            opacity -= 0.1;
            stockCorModal.style.opacity = opacity;
        }
    }, 10);
};

window.onclick = function (event) {
    if (
        event.target === stockCorModal &&
        event.target !== stockCorClose
    ) {
        return;
    }
};

$(document).ready(function () {
    $("#datepicker").datepicker();
});
