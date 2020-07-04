
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
            history.pushState(null, null, "/admin/blogs.html");
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
            history.pushState(null, null, "/admin/tags.html");
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
            history.pushState(null, null, "/admin/types.html");
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
            history.pushState(null, null, "/admin/blogs.html");
        }
    ),

    $("#userName").text(sessionStorage.getItem('userName')),
    $("#userAvatar").attr("src", sessionStorage.getItem('userAvatar'))

})

window.addEventListener('popstate', function(e){
    if(e.state)
        openURL(e.state.href);
});

