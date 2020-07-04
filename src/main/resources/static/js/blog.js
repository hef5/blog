$(document).ready(function () {

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

        function editBlog(blogId) {
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
            }
        ),

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
            }
        ),

        $("#search-btn").on("click",
            function (e) {
                e.preventDefault();
                $("input[name='page']").val(0);
                postData()
            }
        ),

        function previousPage(pageNumber) {
            $("input[name='page']").val(pageNumber);
            postData()
        },

        function nextPage(pageNumber) {
            $("input[name='page']").val(pageNumber);
            postData()
        },

        function postData() {
            $.post(
                "/admin/blogs/search",
                {
                    title: $("[name='title']").val(),
                    typeId: $("[name='typeId']").val(),
                    recommend: $("[name='recommend']").prop('checked'),
                    page: $("[name='page']").val()
                },
                function (data) {
                    $("#table-container").html(data)
                }
            )
        }

    }
)