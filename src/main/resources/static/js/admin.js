$(function () {

    const urlBlog = "/admin/blogs";
    const urlType = "/admin/types";
    const urlTag = "/admin/tags";
    const urlLogout = "/admin/layout";

    let AjaxArgs = {
        url: "/admin/blogs",
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
        },
        success: function (data) {
            $("body").html(data)
        }
    }
    function createAjaxArgs(url, type){
        let s = Object.create(AjaxArgs);
        s.url = url;
        s.type = type;
        return s;
    }

    let clickBlog = createAjaxArgs(urlBlog, "GET");
    let clickType = createAjaxArgs(urlType, "GET");
    let clickTag = createAjaxArgs(urlTag, "GET");
    let clickLogout = createAjaxArgs(urlLogout, "GET");

    $(document).ready(function () {
        // add token to header before request
        $("#blogs").on("click",
            function (e) {
                $.ajax(
                    clickBlog
                )
                history.pushState(clickBlog, null, "/admin/blogs.html");
            }
        ),

            $("#tags").on("click",
                function (e) {
                    e.preventDefault();
                    $.ajax(clickTag);
                    history.pushState(clickTag, null, "/admin/tags.html");
                }
            ),

            $("#types").on("click",
                function (e) {
                    e.preventDefault();
                    $.ajax(clickType)
                    history.pushState(clickType, null, "/admin/types.html");
                }
            ),

            $("#layout").on("click",
                function (e, token) {
                    e.preventDefault();
                    $.ajax(clickLogout)
                    history.pushState(clickLogout, null, "/admin/blogs.html");
                }
            ),

            $("#userName").text(sessionStorage.getItem('userName')),
            $("#userAvatar").attr("src", sessionStorage.getItem('userAvatar'))


        window.addEventListener('popstate', function(e){
            if(e.state)  $.ajax(history.state);
        });

    })







})