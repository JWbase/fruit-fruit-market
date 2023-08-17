function productInfo(status) {
    const ids = [];
    $('input[type="checkbox"]:checked').each(function () {
        ids.push($(this).val());
    });
    return {status, ids};
}

$(document).ready(function () {
    const selectedCategoryIds = new Set();
    const selectedStatusValues = new Set();

    const urlParams = new URLSearchParams(window.location.search);

    // categoryId
    urlParams.getAll("categoryId").forEach((categoryId) => {
        $(`.category-btn[value='${categoryId}']`).addClass("selected");
        selectedCategoryIds.add(categoryId);
    });
    $("#category").val(Array.from(selectedCategoryIds).join(","));

    // status
    urlParams.getAll("status").forEach((statusValue) => {
        $(`.status-btn[value='${statusValue}']`).addClass("selected");
        selectedStatusValues.add(statusValue);
    });
    $("#status").val(Array.from(selectedStatusValues).join(","));

    // name
    const searchValue = urlParams.get("name");
    if (searchValue) {
        $("input[type='text']").val(searchValue);
    }

    $(".category-btn").click(function () {
        const categoryId = $(this).val();

        if (selectedCategoryIds.has(categoryId)) {
            selectedCategoryIds.delete(categoryId);
            $(this).removeClass("selected");
        } else {
            selectedCategoryIds.add(categoryId);
            $(this).addClass("selected");
        }
        $("#category").val(Array.from(selectedCategoryIds).join(","));
        updateUrl();
        $("form").submit();
    });

    $(".status-btn").click(function () {
        const statusValue = $(this).val();

        if (selectedStatusValues.has(statusValue)) {
            selectedStatusValues.delete(statusValue);
            $(this).removeClass("selected");
        } else {
            selectedStatusValues.add(statusValue);
            $(this).addClass("selected");
        }
        $("#status").val(Array.from(selectedStatusValues).join(","));
        updateUrl();
        $("form").submit();
    });

    $(".search button").click(function () {
        $("form").submit();
    });

    const url = new URL(window.location.href);
    const selectedPageSize = url.searchParams.get('pageSize');
    if (selectedPageSize) {
        $('#pageSize').val(selectedPageSize);
    }

    $('#pageSize').on("change", function () {
        const pageSize = $('#pageSize').val();

        const currentUrl = new URL(window.location.href);
        currentUrl.searchParams.set('pageSize', pageSize);
        window.location.href = currentUrl;
    });

    function buildURLWithPageNum(pageNum) {
        const url = new URL(window.location.href);
        url.searchParams.set('pageNum', pageNum);
        return url.href;
    }

    $("form").submit(function (event) {
        event.preventDefault();

        const form = $(this);
        const action = form.attr('action') || '';
        const searchValue = $("input[type='text']").val();

        const categoryParams = Array.from(selectedCategoryIds).map(categoryId => `categoryId=${categoryId}`);
        const statusParams = Array.from(selectedStatusValues).map(statusValue => `status=${statusValue}`);

        const queryParams = [...categoryParams, ...statusParams];
        if (searchValue) {
            queryParams.push(`name=${searchValue}`);
        }

        const queryString = queryParams.join('&');
        const url = action + '?' + queryString;

        window.location.href = url;
    });

    function updateUrl() {
        const action = $('form').attr('action') || '';

        const categoryParams = Array.from(selectedCategoryIds).map(categoryId => `categoryId=${categoryId}`);
        const statusParams = Array.from(selectedStatusValues).map(statusValue => `status=${statusValue}`);

        const searchValue = $("input[type='text']").val();
        const queryParams = [...categoryParams, ...statusParams];
        if (searchValue) {
            queryParams.push(`name=${searchValue}`);
        }

        const queryString = queryParams.join('&');
        const url = action + '?' + queryString;

        window.history.replaceState({}, '', url);
    }

    // 상위 체크박스 선택
    $("#selectProduct").click(function () {
        const isChecked = $(this).prop("checked");
        $(".product-table input[type='checkbox']").prop("checked", isChecked);
    });

    // 하위 체크박스 선택
    $(".product-table").on("click", "input[type='checkbox']", function () {
        if (!$(this).is("#selectProduct")) {
            const totalCheckboxes = $(".product-table input[type='checkbox']:not(#selectProduct)").length;
            const checkedCheckboxes = $(".product-table input[type='checkbox']:not(#selectProduct):checked").length;

            if (totalCheckboxes === checkedCheckboxes) {
                $("#selectProduct").prop("checked", true);
            } else {
                $("#selectProduct").prop("checked", false);
            }
        }
    });

    $(this).on("click", "#selectedStopSale", () => {
        const {status, ids} = productInfo(2);
        changeStatusProduct(ids, status);
    });

    $(this).on("click", "#selectedDelete", () => {
        const {status, ids} = productInfo(4);
        changeStatusProduct(ids, status);
    });

});

function changeStatusProduct(ids, status) {
    axios({
        url: '/admin/changeStatus',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            ids: ids,
            status: status
        }
    }).then(response => {
        if (response.data) {
            alert("정보가 변경되었습니다.");
            location.reload();
        } else {
            alert("변경에 실패했습니다.");
        }
    }).catch(err => {
        console.log(err);
    });
}

