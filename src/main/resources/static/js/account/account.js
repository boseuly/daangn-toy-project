
// 회원가입 -> 페이지 이동이 필요한 경우에는 ajax 사용 x....
const userSignup = () => {

    const resultModal = (data) => {
        const resModal = new bootstrap.Modal(document.getElementById("resultModal"), {
            keyboard: false
        })
        resModal.show()
        modalClose()
    }
    const modalClose = () => {
        $(".modalClose").click(function(){
            location.href="/";
        })
    }

    const userId = $("#userId")[0].value;
    const nickname = $("#nickname")[0].value;
    const email = $("#email")[0].value;
    const userPassword = $("#userPassword")[0].value;
    const userDto = {userId, nickname, email, userPassword};
    console.log(userDto);

    $.ajax ({
        type: "POST",
        url: "/account/signup",
        data: JSON.stringify(userDto),
        contentType:"application/json; charset=UTF-8",
        success: function(data, message){
            resultModal(data);
        },
        error: function () {
            alert("정보를 제대로 입력해주세요.");
        }
    });
}
