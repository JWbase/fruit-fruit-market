//비밀번호 입력 후 실시간 확인
$(document).on('focusout', '.password', () => {
    let pw1 = $('#password').val();
    let pw2 = $('#passwordConfirm').val();

    if (pw1 !== pw2) {
        $('#wrong__pw').text('비밀번호가 일치하지 않습니다.');
    } else {
        $('#wrong__pw').text('');
    }
});

$(document).on("click", "#changePasswordBtn", () => {

    let pw1 = $('#password').val();
    let pw2 = $('#passwordConfirm').val();

    if (pw1 !== pw2) {
        $('#wrong__pw').text('비밀번호가 일치하지 않습니다.');
        $('#passwordConfirm').focus();
        return false;
    }

    const password = $('#password').val();
    const email = new URLSearchParams(location.search).get('email');
    const requestData = {
        email: email,
        password: password
    }

    axios({
        method: 'post',
        url: '/user/changePassword',
        data: requestData
    }).then(response => {
        if (response.data) {
            $('.txt04').show();
        } else {
            $('.txt05').show();
        }
    }).catch(err => {
        console.log(err);
    });

    return true;
});