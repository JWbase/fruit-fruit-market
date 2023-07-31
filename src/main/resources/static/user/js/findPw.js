$(document).on("click", "#findPasswordBtn", () => {
    const email = $('#emailInput').val();

    axios({
        method: 'post',
        url: '/user/findPassword',
        data: {
            email: email
        }
    }).then(response => {
        if (!response.data.exists) {
            $('.txt05').show();
        } else {
            const changePassword = $('#changePassword');
            changePassword.attr('href', '/user/changePassword?email=' + email);

            $('.txt04').show();
        }
    }).catch(err => {
        console.log(err);
    });
});

$(document).on('click', '#closeBtn', () => {
    $(".txt05").hide();
});