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


/** ------------------- blog get ------------------------ */
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
            $("#ajax-response").html(data)
        }
    })
}

function StatePost(url, data) {
    this.url = url;
    this.data = data;
}
/** ------------------- blog post ------------------------ */


function saveBlog() {
    let dataPostBlog = {
        published:      false,
        id:             $("[name='id']").val(),
        flag:           $("[name='flag']").val(),
        title:          $("[name='title']").val(),
        description:    $("[name='description']").val(),
        type:           $("[name='type.id']").val(),
        tagIds:         $("[name='tagIds']").val(),
        firstPicture:   $("[name='firstPicture']").val(),
        recommend:      $("[name='recommend']").prop('checked'),
        shareStatement: $("[name='shareStatement']").prop('checked'),
        appreciation:   $("[name='appreciation']").prop('checked'),
        commentAllowed: $("[name='commentAllowed']").prop('checked')
    };

    ajaxPost(URL_BLOG, dataPostBlog)
    history.pushState(new StateGet(URL_BLOG_INPUT), null, URL_BLOG_INPUT);
}


function publishBlog() {
    let dataPostBlog = {
        published:      true,
        id:             $("[name='id']").val(),
        flag:           $("[name='flag']").val(),
        title:          $("[name='title']").val(),
        description:    $("[name='description']").val(),
        type:           $("[name='type.id']").val(),
        tagIds:         $("[name='tagIds']").val(),
        firstPicture:   $("[name='firstPicture']").val(),
        recommend:      $("[name='recommend']").prop('checked'),
        shareStatement: $("[name='shareStatement']").prop('checked'),
        appreciation:   $("[name='appreciation']").prop('checked'),
        commentAllowed: $("[name='commentAllowed']").prop('checked')
    };

    ajaxPost(URL_BLOG, dataPostBlog)
    history.pushState(new StateGet(URL_BLOG_INPUT), null, URL_BLOG_INPUT);
}

/** ------------------- tags ------------------------ */
function editTags (tagId) {
    let url = "/admin/tags/" + tagId + "/update";
    ajaxGet(url)
    history.pushState(new StateGet(url), null, url);
}

function deleteTags (tagId) {
    let url = "/admin/tags/" + tagId + "/delete";
    ajaxGet(url)
    history.pushState(new StateGet(url), null, url);
}
function addTags () {
    let url = "/admin/tags/update";
    ajaxGet(url)
    history.pushState(new StateGet(url), null, url);
}

function previousTagPage (pageNumber) {
    let url = "/admin/tags/" + pageNumber-1;
    ajaxGet(url);
    history.pushState(new StateGet(url), null, url);
}

function nextTagPage (pageNumber) {
    let url = "/admin/tags/" + pageNumber+1;
    ajaxGet(url);
    history.pushState(new StateGet(url), null, url);
}

function postTag(tagId){
    let url;
    tagId = $("[name='id']").val();
    if (tagId == null){
        url = "/admin/tags";
    } else{
        url = "/admin/tags/" + tagId ;
    }
    let data = {
        id:     $("[name='id']").val(),
        name:   $("[name='name']").val()
    }
    $.ajax({
        url: url,
        type: "POST",
        data: data,
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
        },
        success: function (data) {
            $("#ajax-response").html(data)
        }
    })
    history.pushState(new StateGet(url), null, url);
}

/** ------------------- types ------------------------ */


window.onpopstate = function (e) {
    if (e.state.data == null){
        ajaxGet(e.state.url);
    } else {
        ajaxPost(e.state.url, e.state.data)
    }
}
