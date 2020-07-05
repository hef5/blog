const URL_BLOG        = "/admin/blogs";
const URL_TYPE        = "/admin/types";
const URL_TAG         = "/admin/tags";
const URL_LOGOUT      = "/admin/layout";
const URL_BLOG_INPUT  = "/admin/blogs/input";
const URL_BLOG_SEARCH = "/admin/blogs/search";


function StateGet(url) {
    this.url = url;
}

function ajaxGet(url) {
    $.ajax({
        url: url,
        type: "GET",
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
        ajaxGet(URL_BLOG);
        history.pushState(new StateGet(URL_BLOG), null, "/admin/blogs.html");
    }
),

$("#tags").on("click",
    function (e) {
        ajaxGet(URL_TAG);
        history.pushState(new StateGet(URL_TAG), null, "/admin/tags.html");
    }
),

$("#types").on("click",
    function (e) {
        ajaxGet(URL_TYPE)
        history.pushState(new StateGet(URL_TYPE), null, "/admin/types.html");
    }
),

$("#layout").on("click",
    function (e, token) {
        e.preventDefault();
        ajaxGet(URL_LOGOUT)
        history.pushState(new StateGet(URL_LOGOUT), null, "/admin/blogs.html");
    }
),

$("#userName").text(sessionStorage.getItem('userName')),
$("#userAvatar").attr("src", sessionStorage.getItem('userAvatar'))


/** ------------------- blog list Module ------------------------ */
function confirmDelete(blogId) {
    let url = "/admin/blogs/" + blogId + "/delete";
    // noinspection JSUnresolvedFunction
    $('.ui.modal')
        .modal('show');
    $("#confirm-btn").on("click",
        function (e) {
            e.preventDefault();
            ajaxGet(url);
        }
    )
}

$(document).on("click", "#add-blog",
    function (e) {
        e.preventDefault();
        ajaxGet(URL_BLOG_INPUT);
        history.pushState(new StateGet(URL_BLOG_INPUT), null, URL_BLOG_INPUT);
    }
)

$(document).on("click", "#search-btn",
    function (e) {
        e.preventDefault();
        let data = {
            title: $("[name='title']").val(),
            typeId: $("[name='typeId']").val(),
            recommend: $("[name='recommend']").prop('checked'),
            page: "0"
        }
        ajaxPost(URL_BLOG_SEARCH, data);
        history.pushState(new StatePost(URL_BLOG_SEARCH, data), null, URL_BLOG_SEARCH);
    }
)

function editBlog (blogId) {
    let url = "/admin/blogs/" + blogId + "/update";
    ajaxGet(url)
    history.pushState(new StateGet(url), null, url);
}

function otherPage (pageNumber) {
    let data = {
        title: $("[name='title']").val(),
        typeId: $("[name='typeId']").val(),
        recommend: $("[name='recommend']").prop('checked'),
        page: pageNumber
    };
    ajaxPost(URL_BLOG_SEARCH, data);
    history.pushState(new StatePost(URL_BLOG_SEARCH, data), null, URL_BLOG_SEARCH);
}

function ajaxPost (url, postData) {
    $.ajax({
        url: url,
        type: "POST",
        data: postData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
        },
        success: function (data) {
            $("#table-container").html(data)
        }
    })
}

function StatePost(url, data) {
    this.url = url;
    this.data = data;
}

window.onpopstate = function (e) {
    if (e.state.data == null){
        ajaxGet(e.state.url);
    } else {
        ajaxPost(e.state.url, e.state.data)
    }
}
