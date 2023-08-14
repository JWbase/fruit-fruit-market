$(document).ready(function () {
    const selectedCategoryIds = new Set();
    const selectedStatusValues = new Set();

    const urlParams = new URLSearchParams(window.location.search);
    const categoryValue = urlParams.get("categoryId");
    const statusValue = urlParams.get("status");
    const searchValue = urlParams.get("name");

    if (categoryValue) {
        const categoryList = categoryValue.split(",");
        categoryList.forEach((categoryId) => {
            $(`.category-btn[value='${categoryId}']`).addClass("selected");
            selectedCategoryIds.add(categoryId);
        });
        $("#category").val(categoryValue);
    }

    if (statusValue) {
        const statusList = statusValue.split(",");
        statusList.forEach((status) => {
            $(`.status-btn[value='${status}']`).addClass("selected");
            selectedStatusValues.add(status);
        });
        $("#status").val(statusValue);
    }

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
    });

    // 검색 이벤트 핸들러
    $(".search button").click(function () {
        $("form").submit();
    });

    $('#pageSize').on("change", function () {
        const pageSize = $('#pageSize').val();

        const currentUrl = new URL(window.location.href);
        currentUrl.searchParams.set('pageSize', pageSize);
        window.location.href = currentUrl;
    });

    // //페이징 처리
    // $('.prev, .next, .numbers span').on('click', function () {
    //     moveToPage($(this).data('pageNum'));
    // });
    //
    // function moveToPage(pageNum) {
    //     const categoryIdStr = encodeURIComponent(categoryIds.join(','));
    //     const statusStr = encodeURIComponent(statuses.join(','));
    //
    //     const url = `/admin/product?pageNum=${pageNum}&name=${searchValue}&categoryId=${searchValue}&status=${statusValue}`;
    //     window.location.href = url;
    // }

    function buildURLWithPageNum(pageNum) {
        const url = new URL(window.location.href);
        url.searchParams.set('pageNum', pageNum);

        return url.href;
    }
});

