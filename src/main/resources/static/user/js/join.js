/* 약관 전체선택*/
$(document).on('click', '#allChk', () => {
    $("input[name=termTitle]").prop("checked", $('#allChk').prop("checked"));
});

$(document).on('click', 'input[name=termTitle]', () => {
    $("#allChk").prop("checked", $("input[name=termTitle]").not("#allChk").length === $("input[name=termTitle]:checked").not("#allChk").length);
});

//비밀번호 입력 후 실시간 확인
$(document).on('focusout', '.password', () => {
    checkPasswordMatch();
});

//회원가입 버튼 눌렀을 때 확인
$(document).on('click', '#submitBtn', () => {
    checkPasswordMatch();
    checkedRequiredTerms();
});

/* 필수 약관 확인 */
function checkedRequiredTerms() {
    let requiredCheckboxes = $('.required_term');
    let uncheckedRequiredTerms = requiredCheckboxes.filter(function() {
        return !$(this).prop('checked');
    });

    if (uncheckedRequiredTerms.length > 0) {
        alert('필수 약관에 동의해주세요.');
        return false;
    } else {
        return true;
    }
}


/* 비밀번호 확인 */
function checkPasswordMatch() {
    let pw1 = $('#password').val();
    let pw2 = $('#password2').val();

    if (pw1 !== pw2) {
        $('#wrong__pw').text('비밀번호가 일치하지 않습니다.');
        $('#password2').focus();
        return false;
    } else {
        $('#wrong__pw').text('');
    }
}
