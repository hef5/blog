const URL_BLOG        = "/admin/blogs/0";
const URL_TYPE        = "/admin/types";
const URL_TAG         = "/admin/tags";
const URL_LOGOUT      = "/admin/layout";
const URL_BLOG_INPUT  = "/admin/blogs/input";
const URL_BLOG_SEARCH = "/admin/blogs/search/0";

function blogList(element) {
    $("#shortMenu a").removeClass("active");
    $(element).addClass("active");
    ajaxGet(URL_BLOG);
    history.pushState(new StateGet(URL_BLOG), null, URL_BLOG);
}


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

$(function () {
    $("#userName").text(sessionStorage.getItem('userName'));
    $("#userAvatar").attr("src", sessionStorage.getItem('userAvatar'))
})


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

function addBlog(element) {
    $("#shortMenu a").removeClass("active");
    $(element).addClass("active");
    ajaxGet(URL_BLOG_INPUT);
    history.pushState(new StateGet(URL_BLOG_INPUT), null, URL_BLOG_INPUT);
}


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
    let url;
    let data = {
        title: $("[name='title']").val(),
        typeId: $("[name='typeId']").val(),
        recommend: $("[name='recommend']").prop('checked'),
        page: pageNumber
    };
    if (data.title=="" && data.typeId =="" && data.recommend==false){
        url = "/admin/blogs/" + pageNumber;
        ajaxGet(url);
        history.pushState(new StateGet(url, data), null, url);
    } else {
        url = "/admin/search/" + pageNumber;
        ajaxPost(url, data);
        history.pushState(new StatePost(url, data), null, url);
    }


}


/** ------------------- blog post ------------------------ */


function saveBlog() {
    let dataPostBlog = {
        published:      false,
        id:             $("[name='id']").val(),
        flag:           $("[name='flag']").val(),
        title:          $("[name='title']").val(),
        content:        $("[name='content']").val(),
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
        content:        $("[name='content']").val(),
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
/* post tag */
function postTag(){
    let url;
    let tagId = $("[name='id']").val();
    if (tagId == null){
        url = "/admin/tags";
    } else{
        url = "/admin/tags/" + tagId ;
    }
    let data = {
        id:     $("[name='id']").val(),
        name:   $("[name='name']").val()
    }
    ajaxPost(url, data);
    history.pushState(new StateGet(url), null, url);
}

/** ------------------- types ------------------------ */

function previousTypePage (pageNumber) {
    let url = "/admin/types/" + pageNumber-1;
    ajaxGet(url);
    history.pushState(new StateGet(url), null, url);
}

function nextTypePage (pageNumber) {
    let url = "/admin/types/" + pageNumber+1;
    ajaxGet(url);
    history.pushState(new StateGet(url), null, url);
}
function addType () {
    let url = "/admin/types/update";
    ajaxGet(url);
    history.pushState(new StateGet(url), null, url);
}
function editType (typeId) {
    let url = "/admin/types/" + typeId + "/update";
    ajaxGet(url)
    history.pushState(new StateGet(url), null, url);
}

function deleteType (typeId) {
    let url = "/admin/types/" + typeId + "/delete";
    ajaxGet(url)
    history.pushState(new StateGet(url), null, url);
}

/* post type */
function postType(){
    let url;
    let typeId = $("[name='id']").val();
    if (typeId == null){
        url = "/admin/types";
    } else{
        url = "/admin/types/" + typeId ;
    }
    let data = {
        id:     $("[name='id']").val(),
        name:   $("[name='name']").val()
    }
    ajaxPost(url, data);
    history.pushState(new StateGet(url), null, url);
}


/** functions */
function StateGet(url) {
    this.url = url;
}

function StatePost(url, data, selector) {
    this.url = url;
    this.data = data;
    this.selector = selector;
}

function ajaxGet(url) {
    $.ajax({
        url: url,
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
        },
        success: function (data) {
            $("#content-container").html(data);
        }
    })
}

function ajaxPost (url, postData, selector="#content-container") {
    $.ajax({
        url: url,
        type: "POST",
        data: postData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', sessionStorage.getItem('token'));
        },
        success: function (data) {
            $(selector).html(data)
        }
    })
}

window.onpopstate = function (e) {
    if (e.state.data == null){
        ajaxGet(e.state.url);
    } else if(e.state.selector == null){
        ajaxPost(e.state.url, e.state.data);
    } else {
        ajaxPost(e.state.url, e.state.data, e.state.selector);
    }
}
