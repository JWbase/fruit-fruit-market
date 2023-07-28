/* 약관 전체선택*/

$(document).on('click', '#allChk', () => {
    $("input[name=termStatus]").prop("checked", $('#allChk').prop("checked"));
});

$(document).on('click', 'input[name=termStatus]', () => {
    $("#allChk").prop("checked", $("input[name=termStatus]").not("#allChk").length === $("input[name=termStatus]:checked").not("#allChk").length);
});

$(document).on('focusout', '.password', () => {
    checkPasswordMatch();
});

/* 비밀번호 확인 */
function checkPasswordMatch() {
    let pw1 = $('#password').val();
    let pw2 = $('#password2').val();

    if (pw1 !== pw2) {
        $('#wrong__pw').text('비밀번호가 일치하지 않습니다.');
        return false
    } else {
        $('#wrong__pw').text('');
    }
}
