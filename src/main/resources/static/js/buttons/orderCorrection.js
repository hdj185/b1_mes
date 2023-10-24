// 수정 버튼
var editModal = document.getElementById("editModal");
var editBtn = document.getElementById("editModalBtn");
var editClose = document.getElementsByClassName("editClose")[0];

editBtn.onclick = function () {
    var checkedCnt = document.querySelectorAll('input[type="checkbox"]:checked').length;
    console.log('-----------' + checkedCnt);
    if (checkedCnt == 0)
        Swal.fire({
            title: "선택 필요",
            text: "수정할 행을 선택해주세요.",
            icon: "warning",
            confirmButtonText: "확인"
        });
    else if (checkedCnt > 1)
        Swal.fire({
            title: "선택 오류",
            text: "1개의 행만 선택해주세요.",
            icon: "warning",
            confirmButtonText: "확인"
        });
    else {
        var selectedId = document.querySelector('input[type=checkbox][name=selectedIds]:checked').value;
        console.log("checkedId=" + selectedId);
        $.ajax({
            url: '/rorder/edit',
            method: 'GET',
            data: {
                selectedId: selectedId
            },
            success: function (response) {
                //수주일 설정
                var editInfoDate = response.editInfoDate;
                var formattedDate = editInfoDate.replace(' ', 'T');

                console.log("editInfoDate=" + formattedDate);
                document.getElementById('editOrderCurrentTimeInput').value = formattedDate;

                //수주번호 설정
                var editInfoId = response.editInfoId;
                document.getElementById('editId').value = editInfoId;

                //거래처 설정
                var editInfoCustomerId = response.editInfoCustomerId;
                var customerList = document.getElementById('editCustomerId');

                for (let i = 0; i < customerList.options.length; i++) {
                    if (customerList.options[i].value == editInfoCustomerId) {
                        customerList.options[i].selected = true;
                        break;
                    }
                }

                var editInfoCustomerName = response.editInfoCustomerName;
                document.getElementById('hiddenEditCustomer').value = editInfoCustomerName;

                //품목 설정
                var editInfoProductId = response.editInfoProductId;
                var productList = document.getElementById('editProductId');
                document.getElementById('editTableProductCode').textContent = editInfoProductId;

                for (let i = 0; i < productList.options.length; i++) {
                    if (productList.options[i].value == editInfoProductId) {
                        productList.options[i].selected = true;
                        document.getElementById('editTableProductSort').textContent = productList.options[i].dataset.sort;
                        document.getElementById('editTableProductPrice').textContent = productList.options[i].dataset.price;
                        break;
                    }
                }

                var editInfoProductName = response.editInfoProductName;
                document.getElementById('hiddenEditProduct').value = editInfoProductName;
                document.getElementById('editTableProductName').textContent = editInfoProductName;

                //수주량 설정
                var editInfoCnt = response.editInfoCnt;
                document.getElementById('editOrderCnt').value = editInfoCnt;
                document.getElementById('editTableCnt').textContent = editInfoCnt;

                //수주 가격 설정
                var editInfoPrice = response.editInfoPrice;
                document.getElementById('editPrice').value = editInfoPrice;
                document.getElementById('editTableSum').textContent = editInfoPrice;

                //납품예정일 설정
                var editInfoDeadline = response.editInfoDeadline;
                var dateObj = new Date(editInfoDeadline);
                var year = dateObj.getFullYear();
                var month = ('0' + (dateObj.getMonth() + 1)).slice(-2);
                var day = ('0' + dateObj.getDate()).slice(-2);
                var hour = ('0' + dateObj.getHours()).slice(-2);
                var minute = ('0' + dateObj.getMinutes()).slice(-2);
                var formattedDate = year + '-' + month + '-' + day + ' ' + (hour < 12 ? '오전' : '오후') + ' ' + ('0' + (hour % 12 || 12)).slice(-2) + ':' + ('0' + minute).slice(-2);

                document.getElementById('editDeliveryDate').value = formattedDate;


                // 모달 천천히 나타내기
                editModal.style.opacity = 0;
                editModal.style.display = "block";
                setTimeout(function () {
                    var opacity = 0;
                    var interval = setInterval(function () {
                        if (opacity < 1) {
                            opacity += 0.1;
                            editModal.style.opacity = opacity;
                        } else {
                            clearInterval(interval);
                        }
                    }, 10);
                }, 10); // 일정 시간 간격 뒤에 애니메이션 시작
            },
            error: function (error) {
                // 오류 처리
                console.error('-----------------AJAX 요청 실패:', error);
            }
        });
    }
};

editClose.onclick = function () {
    // 모달 천천히 사라지게 하기
    var opacity = 1;
    var interval = setInterval(function () {
        if (opacity > 0) {
            opacity -= 0.1;
            editModal.style.opacity = opacity;
        } else {
            clearInterval(interval);
            editModal.style.display = "none";
        }
    }, 10);
};

window.onclick = function (event) {
    if (event.target == editClose) {
        // 모달 천천히 사라지게 하기
        var opacity = 1;
        var interval = setInterval(function () {
            if (opacity > 0) {
                opacity -= 0.1;
                editModal.style.opacity = opacity;
            } else {
                clearInterval(interval);
                editModal.style.display = "none";
            }
        }, 10);
    }
};
