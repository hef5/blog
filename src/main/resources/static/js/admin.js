const URL_BLOG = "/admin/blogs";
const URL_TYPE = "/admin/types";
const URL_TAG = "/admin/tags";
const URL_LOGOUT = "/admin/layout";
const GET = "GET";
const POST = "POST";

function StateObj(url, type) {
    this.url = url;
    this.type = type;
}

function ajax(url, type) {
    $.ajax({
        url: url,
        type: type,
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
        },
        success: function (data) {
            $("#ajax-response").html(data);
        }
    })
}

$("#blogs").on("click",
    function (e) {
        ajax(URL_BLOG, GET);
        history.pushState(new StateObj(URL_BLOG, GET), null, "/admin/blogs.html");
    }
),

$("#tags").on("click",
    function (e) {
        ajax(URL_TAG, GET);
        history.pushState(new StateObj(URL_TAG, GET), null, "/admin/tags.html");
    }
),

$("#types").on("click",
    function (e) {
        ajax(URL_TYPE, GET)
        history.pushState(new StateObj(URL_TYPE, GET), null, "/admin/types.html");
    }
),

$("#layout").on("click",
    function (e, token) {
        e.preventDefault();
        ajax(URL_LOGOUT, GET)
        history.pushState(new StateObj(URL_LOGOUT, GET), null, "/admin/blogs.html");
    }
),

$("#userName").text(sessionStorage.getItem('userName')),
$("#userAvatar").attr("src", sessionStorage.getItem('userAvatar'))

window.onpopstate = function (e) {
    let stateObj = history.state;
    ajax(stateObj.url, stateObj.type);
}

/** blog list Module */

function confirmDelete(blogId) {
    let url = "/admin/blogs/" + blogId + "/delete";
    $('.ui.modal')
        .modal('show');
    $("#confirm-btn").on("click",
        function (e) {
            e.preventDefault();
            ajax(url, GET);
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