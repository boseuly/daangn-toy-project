// 이메일 인증
$('#mail-check-btn').click(function() {
    const email = $('#email')[0].value; // 이메일 주소값 얻어오기!
    console.log('완성된 이메일 : ' + email); // 이메일 오는지 확인
    const checkInput = $('#email-auth'); // 인증번호 입력하는곳

    $.ajax({
        type : "GET",
        url : "/account/email?email=" + email, // GET방식이라 Url 뒤에 email을 붙힐수있다.
        success : function (data) {
            console.log("아래는 서버에서 전달 받은 데이터 값");
            console.log("data : " +  data);
            checkInput.attr('disabled',false);
            checkInput.css('background', 'white');
            code =data;
            alert('인증번호가 전송되었습니다.');
            console.log("인증번호 전송까지는 오케");
        }
    }); // end ajax
}); // end send eamil


// 인증번호 비교
// blur -> focus가 벗어나는 경우 발생
$('#email-auth').blur(function () {
    console.log("blur 작동은 하나?")
    const inputCode = $('#email-auth')[0].value;
    console.log("inuptCode : " + inputCode);
    const resultMsg = $('#mail-check-warn')[0];

    if(inputCode === code){
        console.log("code : " + code);console.log("inputCode : " + inputCode);
        resultMsg.innerText = '인증번호가 일치합니다.';
        resultMsg.style.color = 'green';
        $('#mail-check-btn').disabled = true;
        $('#email').readonly = true;

        // $('#userEmail2').attr('onFocus', 'this.initialSelect = this.selectedIndex');
        // $('#userEmail2').attr('onChange', 'this.selectedIndex = this.initialSelect');
    }else{
        resultMsg.html('인증번호가 불일치 합니다. 다시 확인해주세요!.');
        resultMsg.style.color = 'red';
    }
});