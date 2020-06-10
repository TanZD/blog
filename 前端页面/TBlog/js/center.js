$(function() {

    //先加载导航栏
    $("#header").load("page/header.html");
    $("#footer").load("page/footer.html");

    //检测是否有登录
    // if (!islogin()) {
    //     window.location.href = "login.html";
    //     return;
    // }

    //先检测是否登录
    var isOk = 1;
    var user_id = sessionStorage.getItem("user_id");
    if (user_id == "" || user_id == null) {
        if (!islogin()) {
            isOk = -1;
            not_login();
            window.location.href = "login.html";
            return;
        }
    }

    // var user_id = sessionStorage.getItem("user_id");
    // if (user_id == "" || user_id == null) {
    //     if (!islogin()) {
    //         isOK = -1;
    //         window.location.href = "login.html";
    //     }
    // }

    //根据锚点选择要显示的页面
    select_page();

    //显示隐藏LeftGuide
    $("#btp_left").click(function() {
        // $(".sidebarheader").toggle("fast");
        var w = $(".sidebarheader").width() <= 0 ? 220 : 0;
        // console.log(w);
        if (w == 0) {
            $(".sidebarheader span").hide();
            // $(".sidebarheader li").width(w);
            $(".sidebarheader").width(w);
        } else {
            $(".sidebarheader").width(w);
            // $(".sidebarheader li").width(w);
            $(".sidebarheader span").show(function() {});
        }
    })

    //设置侧边栏
    $(window).resize(function() {
        if ($(window).width() > 768) {
            // console.log("RESIZE SIDEBAR");
            $(".sidebarheader").width("");
            $(".sidebarheader span").attr("style", "");
            $(".sidebarheader .badge").addClass("style", "iconf");
        }
    });

    //监听URL 实现SPA
    window.addEventListener('hashchange', function() {
        // console.log(hash);
        select_page();
    });

    //侧边栏选择改变css
    $(".sidebar_ul li").click(function() {
        $(".sidebar_ul li").each(function() {
            $(this).removeClass("actived");
        })
        $(this).addClass("actived");
    });

    //检测用户是否有未读消息
    isToast();

    //检测用户是否有未读消息
    function isToast() {
        $.ajax({
            url: "http://127.0.0.1:8080/Blog/toast/isToast/" + user_id,
            type: "GET",
            dataType: "JSON",
            xhrFields: {
                withCredentials: true
            },
            success: function(data) {
                console.log(data);
                if (data["data"]["num"] > 0) {
                    $("#my_toast").append('<span class="badge iconf">' + data["data"]["num"] + '</span>');
                }
            }
        })
    }

    //检测是否有登录
    function islogin() {
        var isOk = false;
        $.ajax({
            url: "http://127.0.0.1:8080/Blog/user/islogin",
            type: "POST",
            dataType: "JSON",
            async: false,
            xhrFields: {
                withCredentials: true
            },
            success: function(data) {
                // console.log(data)
                if (data["code"] == 1) {
                    sessionStorage.setItem("user_id", data["data"]["id"]);
                    isOk = true;
                }
            }
        })
        return isOk;
    }

    //显示没有登陆
    function not_login() {
        $.alert({
            title: '提示',
            content: '你还没有登录呢(„ಡωಡ„)',
            buttons: {
                去登录: function() {
                    window.location.href = "login.html";
                },
                罢了: function() {}
            }
        });
    }

    //根据锚点返回页面
    function select_page() {
        $(".sidebar_ul li").each(function() {
            $(this).removeClass("actived");
        })
        var hash = window.location.hash;
        $(".sidebar_ul li a[href='" + hash + "']").parent().addClass("actived");
        switch (hash) {
            case "#article":
                $.ajax({
                    url: "page/article_list.html",
                    dataType: "HTML",
                    success: function(data) {
                        $("#content_bar").empty().html(data);
                    }
                })
                break;
            case "#userInfo":
                $.ajax({
                    url: "page/userInfo.html",
                    dataType: "HTML",
                    success: function(data) {
                        // console.log(data);
                        $("#content_bar").empty().html(data);
                    }
                })
                break;
            case "#comment":
                $.ajax({
                    url: "page/comment_list.html",
                    dataType: "HTML",
                    success: function(data) {
                        $("#content_bar").empty().html(data);
                    }
                })
                break;
            case "#history":
                $.ajax({
                    url: "page/history_list.html",
                    dataType: "HTML",
                    success: function(data) {
                        $("#content_bar").empty().html(data);
                    }
                })
                break;
            case "#collect":
                $.ajax({
                    url: "page/collect_list.html",
                    dataType: "HTML",
                    success: function(data) {
                        $("#content_bar").empty().html(data);
                    }
                })
                break;
            case "#follow":
                $.ajax({
                    url: "page/follow_list.html",
                    dataType: "HTML",
                    success: function(data) {
                        $("#content_bar").empty().html(data);
                    }
                })
                break;
            case "#toast":
                $.ajax({
                    url: "page/toast_list.html",
                    dataType: "HTML",
                    success: function(data) {
                        $("#content_bar").empty().html(data);
                    }
                })
                break;
            case "#message":
                window.location.href = "message.html";
                break;
            case '':
                $.ajax({
                    url: "page/userInfo.html",
                    dataType: "HTML",
                    success: function(data) {
                        // console.log(data);
                        $("#content_bar").empty().html(data);
                    }
                })
                $(".sidebar_ul li a[href='#a']").parent().addClass("actived");
                break;
            default:
                break;
        }
    }

    //根据锚点的参数切换侧边栏的css


})