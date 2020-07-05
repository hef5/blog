// click delete event function






/* let postData = function () {
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
} */

/*
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

*/






/**
 * 坑1，
 * const 函数不能放在 document.read(function(){ }) 里头， 把js文件链接放在html里，函数不能被引用。
 *
 * 坑2，
 * history.pushState() 里的第一个参数state有要求的，state必须为可以序列化的对象，对象的方法无法被序列化，
 * 如果传入history.pushState()的第一个参数对象，是通过Object.create()创建的对象，并且包含了无法被序列化的方法，
 * 使用history.pushState()方法不会报错，但会把参数对象里的方法自动排除，用JSON.stringify()序列化此对象
 * 会有一样效果，然而，如果对象时通过构造函数创建的，则传入到history.pushState()里会报错
 * 错误为: Failed to execute 'pushState' on 'History': function(){} could not be cloned.
 *
 * 坑3，
 * 原页面加载完ajax返回页面之后，原有click事件监听不到，必须用$(document).on("click", "#add-blog",f)来监听
 * 原来是$("#add-blog").on("click", f)
 *
 * */
