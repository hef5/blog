// click delete event function
function confirmDelete(blogId) {
    $('.ui.modal')
        .modal('show');
    $("#confirm-btn").on("click",
        function (e) {
            e.preventDefault();
            $.ajax({
                url: "/admin/blogs/" + blogId + "/delete",
                type: "GET",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
                },
                success: function (data) {
                    $("body").html(data)
                }
            })
        }
    )
}

$("#add-blog").on("click",
    function (e) {
        e.preventDefault();
        $.ajax({
            url: "/admin/blogs/input",
            type: "GET",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
            },
            success: function (data) {
                $("body").html(data)
            }
        })
        history.pushState(null, null, "/admin/blogs/input");
    }
)

$("#search-btn").on("click",
    function (e) {
        e.preventDefault();
        $("input[name='page']").val(0);
        postData()
        history.pushState(null, null, "/admin/blogs/search");
    }
)

    let postData = function () {
        $.ajax({
            url: "/admin/blogs/search",
            type: "POST",
            data: {
                title: $("[name='title']").val(),
                typeId: $("[name='typeId']").val(),
                recommend: $("[name='recommend']").prop('checked'),
                page: $("[name='page']").val()
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
            },
            success: function (data) {
                $("#table-container").html(data)
            }
        })
        history.pushState(null, null, "/admin/blogs/search");
    }

    const editBlog = function (blogId) {
        $.ajax({
            url: "/admin/blogs/" + blogId + "/update",
            type: "GET",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
            },
            success: function (data) {
                $("body").html(data)
            }
        })
        history.pushState(null, null, "/admin/blogs/" + blogId + "/update");
        return true;
    }

    const otherPage = function (pageNumber) {
        $("input[name='page']").val(pageNumber);
        postData()
        history.pushState(null, null, "/admin/blogs/page=" + pageNumber);
        return true;
    }







/**
 * const 函数不能放在 document.read(function(){ }) 里头， 把js文件链接放在html里，函数不能被引用。
 *
 * */
