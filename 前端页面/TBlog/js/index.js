$(function() {
    //文章数据
    var data;
    //文章获取排列顺序
    //3时间倒序
    var order = 3;
    //每页显示数
    var limit = 8;
    //页数
    var page = 1;
    //标签颜色数组
    var color = ['#578fff', '#8c9ffd', '#ff7ea2', '#ffbf43', '#74dde3', '#8A2BE2', '#32CD32', '#F08080'];

    //查看是否保存上次的页数 否则显示第一页
    var s_page = sessionStorage.getItem("curPage");
    // console.log(s_page)
    if (s_page == "" || s_page == null) {
        page = 1;
    } else {
        page = s_page;
    }


    //加载导航栏
    $("#header").load("page/header.html");
    //加载页尾
    $("#footer").load("page/footer.html");
    // $.ajax({
    // 	url:"page/header.html",
    // 	dataType:"html",
    // 	xhrFields:{
    // 		withCredentials: true
    // 	},
    // 	success:function(data){
    // 		$("#header").html(data)
    // 	},
    // 	error:function(xhr){
    // 		console.log("导航栏加载出错: ")
    // 		console.log(xhr)
    // 	}
    // })

    //选项卡
    $(".sortbar span").click(function() {
        // alert($(this).index())
        var t = $(this);
        if ($(this).index() == 0) {

        } else if ($(this).index() == 1) {

        } else if ($(this).index() == 2) {

        }
        $(".sortbar span").each(function() {
            $(this).removeClass("active_select")
        })
        t.addClass("active_select")
    })

    // $(".orderbar .btn-group").hover(function(){
    // 	$(".orderbar .btn-group .dropdown-menu").toggle();
    // })
    // $(".orderbar .btn-group").click(function(){
    // 	$(".orderbar .btn-group .dropdown-menu").toggle();
    // })

    //获取文章列表
    getArticleList();

    //获取热门分类和标签
    $.ajax({
        url: "http://127.0.0.1:8080/Blog/category/getByWeight",
        type: "GET",
        dataType: "JSON",
        success: function(data) {
            if (data["code"] == 1) {
                console.log(data)
                $("#cate_ul").empty();
                $.each(data["models"], function(index, item) {
                        $("#cate_ul").append('<li class="cate_list list_text" data-cate_id="' + item["id"] + '">' + item["category_name"] + '</li>');
                    })
                    //设置分类点击事件
                    // setCateListener();
                $("#cate_ul li").click(function() {
                    alert($(this).data("cate_id"));
                })
            }
        }
    })

    $.ajax({
        url: "http://127.0.0.1:8080/Blog/tag/getHotTags/10",
        type: "GET",
        dataType: "JSON",
        success: function(data) {
            if (data["code"] == 1) {
                console.log(data)
                $("#tag_ul").empty();
                $.each(data["models"], function(index, item) {
                        const num = getRandomColor();
                        $("#tag_ul").append('<li class="tag_list" data-tag_id="' + item["id"] + '" style="background-color:' + color[num] + '">' + item["tag_name"] + '</li>');
                    })
                    //设置分类点击事件
                    // setCateListener();
                $("#tag_ul li").click(function() {
                    alert($(this).data("tag_id"));
                })
            }
        }
    })

    //获取当前登录用户的信息
    //先查看有没有登录
    var user_id = sessionStorage.getItem("user_id");
    console.log(user_id)
    if (user_id == "" || user_id == null) {
        // console.log(islogin());
        if (islogin() != false) {
            getUserMessage();
        }
    } else {
        getUserMessage();
    }

    //排序选择
    $("#selectorder li").click(function() {
        // console.log($(this).index())
        var index = $(this).index();
        if (index == 0) {
            $("#selectbtn").empty().append("时间排序<span class='caret'></span>")
            order = 3;
            getArticleList();
        } else if (index == 1) {
            $("#selectbtn").empty().append("点击量排序<span class='caret'></span>")
            order = 2;
            getArticleList();
        } else if (index == 2) {
            $("#selectbtn").empty().append("点赞数排序<span class='caret'></span>")
            order = 1;
            getArticleList();
        }
    })


    //点赞
    $(".b_status .like_btn").click(function() {
        var isOk = 1;
        // alert($(this).data("id"))
        var _this = $(this);
        //先查看有没有登录
        var user_id = sessionStorage.getItem("user_id");
        if (user_id == "" || user_id == null) {
            // console.log(islogin())
            if (islogin() == false) {
                not_login();
                isOk = -1;
            }
        }
        //如果用户已经登录
        if (isOk == 1) {
            var article_id = $(this).data("id")
            $.ajax({
                url: "http://127.0.0.1:8080/Blog/like/add/user/" + user_id + "/article/" + article_id,
                type: "POST",
                dataType: "JSON",
                xhrFields: {
                    withCredentials: true
                },
                success: function(e) {
                    // console.log(e)
                    if (e["code"] == -1) {
                        not_login();
                    } else if (e["code"] == 1) {
                        //获取原来的点赞数
                        var ori_num = _this.data("num");
                        //更新操作后显示的点赞数和css
                        _this.empty().append(++ori_num);
                        _this.removeClass("icon-dianzan").addClass("icon-dianzan1 like_red");
                        //更新点赞数
                        _this.data("num", ori_num);
                    } else {
                        var ori_num = _this.data("num");
                        _this.empty().append(--ori_num);
                        _this.removeClass("icon-dianzan1 like_red").addClass("icon-dianzan");
                        _this.data("num", ori_num);
                    }
                }

            })
        }
        // if($(this).hasClass("like_red")){
        // 	$(this).removeClass("like_red")
        // }else{
        // 	$(this).addClass("like_red")
        // }
    })

    //查看用户信息
    $(".b_user").click(function() {
        alert($(this).data("user_id"))
            //
    })

    //分类选择
    $(".cate_list_ul .cate_list").click(function() {
        alert($(this).data("id"))
    })

    //标签显示
    //获取标签数组随机数
    function getRandomColor() {
        const num = Math.floor(Math.random() * color.length);
        return num;
    }

    $(".tag_class .tag_list").each(function() {
        const num = getRandomColor();
        $(this).css("backgroundColor", color[num]);
    })

    //标签选择
    $(".tag_class .tag_list").click(function() {
        // alert($(".tag_class .tag_list").index(this));
        alert($(this).data("id"))
    })

    //获取文章列表
    function getArticleList() {
        //显示加载中
        $("#s_article_list").empty().append('<div class="loading_anim"><span class="iconfont icon-shuaxin"></div>');
        $.ajax({
            url: "http://127.0.0.1:8080/Blog/article/a/page/" + page + "/limit/" + limit + "/order/" + order,
            type: "GET",
            dataType: "JSON",
            async: false,
            xhrFields: {
                withCredentials: true
            },
            success: function(e) {
                console.log(e);
                if (e["code"] == 1) {
                    data = e;
                    show_article_list();
                    setPage();
                    //保存下当前页数 在页面刷新时不用从第一页开始
                    sessionStorage.setItem("curPage", page);
                } else {
                    $.alert({
                        title: '提示',
                        content: '没有文章呢!▌°Д °;)っ',
                    });
                    data = "";
                }
            },
            error: function(e) {
                console.log(e)
                $.alert({
                    title: '提示',
                    content: '请求出错了(￣▽￣")',
                });
            }
        })
    }

    //显示文章列表
    function show_article_list() {
        var article_list = data["models"];
        $("#s_article_list").empty();
        for (var i = 0; i < article_list.length; i++) {
            var d = article_list[i];
            var m = '<li class="nav article_list">' +
                '<div class="list_title list_text" data-article_id="' + d["article"]["id"] + '">' + d["article"]["title"] + '</div>' +
                '<div class="list_summary list_text">' + d["article"]["summary"] + '</div>' +
                '<div class="bottom_list">' +
                '<div class="b_user" style="display: inline-block;" data-user_id="' + d["user"]["id"] + '">' +
                '<img class="b_user_img" src="http://127.0.0.1:8080/' + d["user"]["image"] + '"> ' +
                '<span class="b_user_name col_text" title="' + d["user"]["username"] + '">' + d["user"]["username"] + '</span>' +
                '</div>' +
                '<div class="b_time" style="display: inline-block;">发布于: ' + d["article"]["create_time"] +
                '</div>' +
                '<div class="b_status">' +
                '<span class="col_text iconfont icon-yanjing">' + d["article"]["view_count"] + '</span>' +
                '<span class="col_text iconfont icon-pinglun">' + d["article"]["comment_count"] + '</span>' +
                '<span data-num="' + d["article"]["likes_count"] + '" data-id="' + d["article"]["id"] + '" class="col_text iconfont icon-dianzan like_btn">' + d["article"]["likes_count"] + '</span>' +
                '</div>' +
                '<div style="clear: both;"></div>' +
                '</div>' +
                '</li>';
            $("#s_article_list").append(m).fadeIn();
        }
        //文章选择监听
        article_select();
        //显示文章点赞状态
        getLikeStatus();
    }

    //文章选择
    function article_select() {
        $("#s_article_list .list_title").click(function() {
            // alert($(this).data("article_id"))
            var article_id = $(this).data("article_id");
            window.open("article?article=" + article_id);
        })
    }

    //获取文章点赞状态
    function getLikeStatus() {
        var article_id = [];
        // console.log(data)
        if (data != "") {
            for (var i = 0; i < data["models"].length; i++) {
                article_id.push(data["models"][i]["article"]["id"]);
            }
        }
        console.log(article_id)
        $.ajax({
            url: "http://127.0.0.1:8080/Blog/like/isLikeList",
            type: "POST",
            dataType: "JSON",
            traditional: true,
            xhrFields: {
                withCredentials: true
            },
            data: { "article_id": article_id },
            success: function(e) {
                console.log(e)
                if (e["code"] == 1) {
                    //根据点赞情况改变点赞图标的css
                    $.each(e["models"], function(index, item) {
                        if (item["status"] == 1) {
                            $(".b_status .like_btn[data-id='" + item["likes_article_id"] + "']").addClass("icon-dianzan1 like_red");
                        }
                    })
                }
            },
            error: function(err) {
                console.log(err)
            }
        })
    }

    //检测是否登录
    function islogin() {
        var result = false;
        $.ajax({
            url: "http://127.0.0.1:8080/Blog/user/islogin",
            type: "POST",
            dataType: "JSON",
            async: false,
            xhrFields: {
                withCredentials: true
            },
            success: function(data) {
                if (data["code"] == 1) {
                    sessionStorage.setItem("user_id", data["data"]["id"]);
                    user_id = data["data"]["id"];
                    result = true;
                }
            }
        })
        return result;
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

    //设置分页
    function setPage() {
        //分页
        $('#page_list').jqPaginator({
            totalPages: data["pageInfo"]["totalpage"],
            visiblePages: 5,
            currentPage: data["pageInfo"]["page"],
            first: '<li class="first"><a href="javascript:void(0);"><<<\/a><\/li>',
            prev: '<li class="prev"><a href="javascript:void(0);"><<\/a><\/li>',
            next: '<li class="next"><a href="javascript:void(0);">><\/a><\/li>',
            last: '<li class="last"><a href="javascript:void(0);">>><\/a><\/li>',
            page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
            onPageChange: function(num, type) {
                if (type == "change") {
                    page = num;
                    getArticleList();
                }
            }
        });
    }

    //查询并显示登录用户信息
    function getUserMessage() {
        $.ajax({
            url: "http://127.0.0.1:8080/Blog/user/" + user_id,
            dataType: "JSON",
            type: "GET",
            xhrFields: {
                withCredentials: true
            },
            success: function(data) {
                console.log(data);
                if (data["code"] == 1) {
                    var user = data["data"];
                    var m = '<div>' +
                        '<img class="top_img" src="http://127.0.0.1:8080/' + user["image"] + '">' +
                        '</div>' +
                        '<div class="col_text top_user">' + user["username"] + '</div>' +
                        '<div class="top_user" style="word-wrap: break-word;"></div>' +
                        '<div class="top_user_m">' +
                        '<div>' +
                        '<span>文章:' + user["article_count"] + '</span><span>评论:' + user["comment_count"] + '</span>' +
                        '</div>' +
                        '<div>' +
                        '<span>关注:' + user["follow_count"] + '</span><span>点赞:' + user["like_count"] + '</span>' +
                        '</div>' +
                        '</div>' +
                        '<div class="top_btn">' +
                        '<button class="btn btn-default btn-xs btn_p d_inline">发布</button> ' +
                        '<button class="btn btn-default btn-xs btn_p d_inline">动态</button>' +
                        '</div>';
                    $("#user_mess").empty().append(m).fadeIn("slow");
                }
            },
            error: function(err) {
                console.log(err)
            }
        })
    }

})