
// 이미지 미리보기
const createElement = (e, file) =>{
    const li = document.createElement('li');
    const img = document.createElement('img');
    const upload = $('.upload')[0];
    img.setAttribute('src', e.target.result);
    img.setAttribute('data-file', file.name);
    img.setAttribute('class', 'col prev-img');
    if($('.float-left')[0] == null){
        upload.classList.add('float-left');

    }
    li.appendChild(img);

    return li;
}

const getImageFiles = (e) =>{
    const uploadFiles = [];
    const files = e.currentTarget.files;
    const imagePreview = $('.image-preview')[0];
    console.log(typeof files, files);
    const docFrag = new DocumentFragment();

    if ([...files].length >= 10) {
        alert('이미지는 최대 10개까지 업로드 할 수 있습니다.');
        return;
    }
    [...files].forEach(file => {
        if(!file.type.match("image/.*")) {
            alert('이미지만 업로드 가능합니다.');
            return;
        }
        if([...files].length < 10){
            uploadFiles.push(file);
            const reader = new FileReader();
            reader.onload = (e) => {
                const preview = createElement(e, file);
                imagePreview.appendChild(preview);
            };
            reader.readAsDataURL(file);
        }
    });
}


const imgShow = () =>{
    const realUpload = $('.real-upload')[0];
    const upload = $('.upload')[0];

    upload.addEventListener('click', () => realUpload.click());
    realUpload.addEventListener('change', getImageFiles);
}