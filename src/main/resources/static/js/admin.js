$(document).ready(function () {
    // add token to header before request
    $("#blogs").on("click",
        function (e) {
            $.ajax({
                url: "/admin/blogs",
                type: "GET",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
                },
                success: function (data) {
                    $("body").html(data)
                }
            })
        }
    ),

    $("#tags").on("click",
        function (e) {
            e.preventDefault();
            $.ajax({
                url: "/admin/tags",
                type: "GET",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
                },
                success: function (data) {
                    $("body").html(data)
                }
            })
        }
    ),

    $("#types").on("click",
        function (e) {
            e.preventDefault();
            $.ajax({
                url: "/admin/types",
                type: "GET",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
                },
                success: function (data) {
                    $("body").html(data)
                }
            })
        }
    ),

    $("#layout").on("click",
        function (e, token) {
            e.preventDefault();
            $.ajax({
                url: "/admin/layout",
                type: "GET",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
                },
                success: function (data) {
                    sessionStorage.clear();
                    $("body").html(data)
                }
            })
        }
    ),

    $("#userName").attr("text", sessionStorage.getItem('userName')),
    $("#userAvatar").attr("src", sessionStorage.getItem('userAvatar'))

})

