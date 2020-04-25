$(function() {
    var header = "";

    function conHeader(data) {
        header = '<li>' +
            '<div class="searchbar">' +
            '<a href="#" class="search_a" id="search_btn">' +
            '<i class="fa fa-search fa-lg search_i"></i>' +
            '</a>' +
            '<input type="text" placeholder="搜你所想" class="search_input" name="search">' +
            '</div>' +
            '</li>' +
            '<li>' +
            '<a href="" class="header_image">' +
            '<img src="http://127.0.0.1:8080/' + data["data"]["image"] + '">' +
            '</a>' +
            '</li>' +
            '<li>' +
            '<a href="center.html">个人中心</a>' +
            '</li>' +
            '<li>' +
            '<a href="../components/">消息</a>' +
            '</li>' +
            '<li>' +
            '<a href="../javascript/">私信<span class="badge pos_ab">4</span></a>' +
            '</li>' +
            '<li>' +
            '<a href="../customize/">注册</a>' +
            '</li>' +
            '<li>' +
            '<a href="javascript:;" id="logout">登出</a>' +
            '</li>';
    }


    //检测登录
    $.ajax({
        url: "http://127.0.0.1:8080/Blog/user/islogin",
        type: "POST",
        dataType: "JSON",
        async: false,
        xhrFields: {
            withCredentials: true
        },
        success: function(data) {
            console.log(data)
            if (data["code"] == 1) {
                conHeader(data);
                $("#headerbar").empty().append(header);
                sessionStorage.setItem("user_id", data["data"]["id"]);
            }
        }
    })

    //搜索
    $("#search_btn").click(function() {
        alert($("input[name='search']").val())
    })
    $("input[name='search']").keydown(function(event) {
        if (event.keyCode == '13') {
            $("#search_btn").click();
        }
    })

    //登出
    $("#logout").click(function() {
        $.ajax({
            url: "http://127.0.0.1:8080/Blog/user/logout",
            type: "POST",
            dataType: "JSON",
            xhrFields: {
                withCredentials: true
            },
            success: function(e) {
                sessionStorage.removeItem("user_id")
                $.alert({
                    title: '提示',
                    content: '登出成功(　ﾟ∀ﾟ) ﾉ♡',
                    autoClose: 'OK|3000',
                    type: 'purple',
                    typeAnimated: 'true',
                    buttons: {
                        OK: {
                            btnClass: 'btn-purple',
                            action: function() {
                                window.location.reload();
                            }
                        }
                    }
                });
            }
        })
    })

})