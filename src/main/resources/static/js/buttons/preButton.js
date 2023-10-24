// "예측" 버튼 클릭 시 실행되는 함수
document.getElementById("regSearchbarPreButton").addEventListener("click", function() {
    // 품목명과 수량 값을 가져옴
    var productName = document.getElementById("regProductId").options[document.getElementById("regProductId").selectedIndex].text;
    var orderQuantity = document.getElementById("regOrderCnt").value;

    // 결과 테이블의 품목명과 수량을 변경
    document.getElementById("orderDataTables1").rows[1].cells[0].innerText = productName;
    document.getElementById("orderDataTables1").rows[1].cells[2].innerText = orderQuantity;
});
