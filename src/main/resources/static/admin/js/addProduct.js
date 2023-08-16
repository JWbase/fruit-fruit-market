const imageFiles = [];
const thumbnailFiles = [];
const formData = new FormData();

$(() => {
    $(".totalPrice").hide();
    $(document).on('input', '.calculation', () => {
        const price = Number($("#price").val());
        const discountRate = Number($("#discountRate").val()) / 100;
        if (discountRate != '') {
            $(".totalPrice").show();
        }
        const discountedPrice = price * (1 - discountRate);
        $("#totalPrice").val(formatNumberWithCommas(discountedPrice));
    });

    //상품 이미지 미리보기 코드
    $("#productPicture").on("change", () => {
        const thumbnailList = $("#productPicture")[0].files;
        for (const thumbnail of thumbnailList) {
            thumbnailFiles.push(thumbnail);
        }
        uploadImage();
    });

    //bindingResult에러 후 숫자들 , 표시
    $('.numbers').on('input', function (e) {
        const input = e.target;
        input.value = formatNumberWithCommas(input.value.replace(/\D/g, ''));

        const hiddenField = $(input).next('input[type="hidden"]');
        hiddenField.val(input.value.replace(/,/g, ''));
    });


    //처음 입력시 숫자 ,표시
    $(".numbers").each(function () {
        const input = $(this);
        const hiddenField = input.next('input[type="hidden"]');
        const hiddenValue = hiddenField.val();
        if (hiddenValue !== undefined) {
            const originalValue = formatNumberWithCommas(hiddenValue);
            input.val(originalValue);
        }
    });

    $("#submitBtn").on("click", (e) => {
        e.preventDefault();

        for (const file of imageFiles) {
            formData.append("file", file);
        }
        for (const thumbnail of thumbnailFiles) {
            formData.append("thumbnail", thumbnail)
        }

        formData.append("name", $("#name").val());
        formData.append("categoryId", $("#categoryId").val());
        formData.append("price", $("#price").val());
        formData.append("discountRate", $("#discountRate").val());
        formData.append("stockQuantity", $("#stockQuantity").val());
        formData.append("content", tinymce.get("content").getContent());

        axios({
            method: 'post',
            url: '/admin/addProduct',
            data: formData,
            headers: {'Content-Type': 'multipart/form-data'}
        })
            .then(response => {
                if (!response.data) {
                    alert("상품등록 실패");
                }
                $(".txt04").show();
            })
            .catch(error => {
                console.log('업로드 에러');
            });
    });
});

//tinymce에디터 함수
tinymce.init({
    selector: '#content',
    height: 500,
    width: 1000,
    plugins: 'image',
    toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | image",
    image_title: true,
    file_picker_types: 'image',
    images_upload_handler: function (blobInfo, success) {

        const file = new File([blobInfo.blob()], blobInfo.filename());
        const fileName = blobInfo.filename();
        if (!fileName.includes("blobid")) {
            imageFiles.push(file);
        }
        success(URL.createObjectURL(file));
    }
});


//이미지 업로드함수
function uploadImage() {
    const fileInput = $("#productPicture")[0];
    const file = (fileInput && fileInput.files.length > 0) ? fileInput.files[0] : null;
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            $('#thumbnail').attr('src', e.target.result);
        }
        reader.readAsDataURL(file);
    }
}

//1,000단위 표시 함수
function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}