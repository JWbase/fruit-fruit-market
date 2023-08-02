//이미지 미리보기

$(() => {

    $('.numbers').on('input', function (e) {
        const input = e.target;
        input.value = formatNumberWithCommas(input.value.replace(/\D/g, ''));

        const hiddenField = $(input).next('input[type="hidden"]');
        hiddenField.val(input.value.replace(/,/g, ''));
    });

    $('.numbers').each(function () {
        const input = $(this);
        const hiddenField = input.next('input[type="hidden"]');
        const originalValue = formatNumberWithCommas(hiddenField.val());
        input.val(originalValue);
    });

    $("#productPicture").on("change", (event) => {
        const thumbnail = $('#thumbnail');

        const file = $('#productPicture')[0].files[0];
        const formData = new FormData();
        formData.append("file", file);

        axios({
            method: 'post',
            url: '/upload',
            data: formData
        }).then(response => {
            const url = response.data
            thumbnail.attr("src", url);
        }).catch(err => {
            console.log("업로드 에러 : ", err);
        });
    });
});

function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}
