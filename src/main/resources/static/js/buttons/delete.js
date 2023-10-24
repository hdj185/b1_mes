document.getElementById("deleteButton").addEventListener("click", function() {
    Swal.fire({
        title: "삭제하시겠습니까?",
        icon: "question",
        showCancelButton: true,
        confirmButtonText: "삭제",
        cancelButtonText: "취소"
    }).then((result) => {
        if (result.isConfirmed) {
            // 삭제 동작을 실행하는 코드 작성
            console.log("삭제 실행");
        } else {
            // 취소 동작을 실행하는 코드 작성
            console.log("삭제 취소");
        }
    });
});
