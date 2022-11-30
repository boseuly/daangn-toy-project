const handleResize = () => {
    const textarea = $('.content-textarea')[0];
    textarea.style.height = 200+'px';
    textarea.style.height = 13 + textarea.scrollHeight + 'px';
}

const save = () =>{

    const postTitle = $('#postTitle')[0].value;
    const price = $('#price')[0].value;
    const postContent = $('#postContent')[0].value;
    const sellerId = $('#userId')[0].value;
    const regionId = $('#regionId')[0].value;
    const categoryId = $('#categoryId')[0].value;

    const free = $('#free')[0].checked === true ? 'Y' : 'N';
    const proposalYn = $('#proposalYn')[0].checked === true ? 'Y' : 'N';

    const data = {
        "postTitle" : postTitle,
        "postContent" : postContent,
        "price" : price,
        "sellerId" : sellerId,
        "regionId" : regionId,
        "categoryId" : categoryId,
        "free" : free,
        "proposalYn" : proposalYn,
    }


    // image 처리
    const formData = new FormData();
    const images = $('#images')[0];
    for(var i = 0; i < images.files.length; i++){
        console.log("images = " + images.files[i]);
        // formData에 image 라는 키값으로 images 값을 append 한다.
        formData.append('image', images.files[i]);
    }

    // key 라는 이름으로 위에서 담은 data를 formData에 append한다.
    formData.append('detailDto', new Blob([JSON.stringify(data)], {type : "application/json"}));

    $.ajax({
        url : '/post',
        data : formData,
        contentType : false,                // 중요
        processData : false,                // 중요
        enctype : 'multipart/form-data',    // 중요
        success : function (data, message) {

        }

    })
}

