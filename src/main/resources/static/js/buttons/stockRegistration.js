
// 등록 버튼
var stockRegModal = document.getElementById("stockRegModal");
var stockRegBtn = document.getElementById("stockRegBtn");
var stockRegClose = document.getElementsByClassName("stockRegClose")[0];

stockRegBtn.onclick = function () {
    stockRegModal.style.display = "block";
    stockRegModal.style.opacity = "0";

    var opacity = 0;
    var intervalId = setInterval(function () {
        if (opacity >= 1) {
            clearInterval(intervalId);
        } else {
            opacity += 0.1;
            stockRegModal.style.opacity = opacity;
        }
    }, 10);
};

stockRegClose.onclick = function () {
    var opacity = 1;
    var intervalId = setInterval(function () {
        if (opacity <= 0) {
            clearInterval(intervalId);
            stockRegModal.style.display = "none";
        } else {
            opacity -= 0.1;
            stockRegModal.style.opacity = opacity;
        }
    }, 10);
};

window.onclick = function (event) {
    if (event.target == stockRegModal) {
        var opacity = 1;
        var intervalId = setInterval(function () {
            if (opacity <= 0) {
                clearInterval(intervalId);
                stockRegModal.style.display = "none";
            } else {
                opacity -= 0.1;
                stockRegModal.style.opacity = opacity;
            }
        }, 10);
    }
};

$(document).ready(function () {
    $("#datepicker").datepicker();
});