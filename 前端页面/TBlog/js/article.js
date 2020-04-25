$(function() {

    //文章信息
    var article;
    //评论列表页数
    var comment_page = 1;
    //评论列表每页显示数量
    var comment_limit = 15;
    //评论列表数据
    var comment_data;
    //评论字数限制
    var comment_num_limit = 500;
    //评论字数
    var comment_post_num = 0;
    //评论的父id
    var comment_pid = 0;
    //标签颜色数组
    var color = ['#578fff', '#8c9ffd', '#ff7ea2', '#ffbf43', '#74dde3', '#8A2BE2', '#32CD32', '#F08080'];

    //先加载导航栏
    $("#header").load("page/header.html");
    $("#footer").load("page/footer.html");

    //获取文章id
    var article_id = getQueryVariable("article");
    //获取文章信息 同步
    $.ajax({
        url: "http://127.0.0.1:8080/Blog/article/" + article_id,
        type: "GET",
        dataType: "JSON",
        async: false,
        xhrFields: {
            withCredentials: true
        },
        success: function(data) {
            console.log(data)
            if (data["code"] == 1) {
                article = data;
                //显示文章内容
                show_article();
                //显示作者信息
                show_UserMessage();
            }
        },
        error: function(err) {
            console.log(err)
            $.alert({
                title: '提示',
                content: '请求出错了(￣▽￣")',
            });
        }
    })

    //获取评论列表
    getCommentList();

    //获取是否关注
    $.ajax({
        url: "http://127.0.0.1:8080/Blog/follow/isFollowed/" + article["data"]["user"]["id"],
        type: "GET",
        dataType: "JSON",
        xhrFields: {
            withCredentials: true
        },
        success: function(data) {
            if (data["code"] == 1) {
                $("#follow_user").empty().append("已关注").removeClass("btn-default").addClass("btn-danger");
            }
        },
        error: function(err) {
            console.log(err);
        }
    })

    //关注操作
    $("#follow_user").click(function() {
        var isOK = 1;
        var user_id = sessionStorage.getItem("user_id");
        if (user_id == "" || user_id == null) {
            if (!islogin()) {
                isOK = -1;
                not_login();
            }
        }
        if (isOK == 1 && article != null) {
            $.ajax({
                url: "http://127.0.0.1:8080/Blog/follow/save",
                type: "POST",
                dataType: "JSON",
                data: { "follow_user_id": user_id, "follow_pid": article["data"]["user"]["id"] },
                xhrFields: {
                    withCredentials: true
                },
                success: function(data) {
                    if (data["msg"] == "成功") {
                        $.alert({
                            title: '提示',
                            content: '关注成功(｡･ω･｡)ﾉ♡',
                            autoClose: 'OK|500',
                            backgroundDismiss: true,
                            type: 'green',
                            typeAnimated: 'true',
                            buttons: {
                                OK: {
                                    btnClass: 'btn-green',
                                }
                            }
                        });
                        $("#follow_user").empty().append("已关注").removeClass("btn-default").addClass("btn-danger");
                    } else {
                        $("#follow_user").empty().append("关注").removeClass("btn-danger").addClass("btn-default");
                    }
                },
                error: function(err) {
                    console.log(err);
                    $.alert({
                        title: '提示',
                        content: '请求出错了(￣▽￣")',
                    })
                }
            })
        }
    })

    //私信操作
    $("#message_user").click(function() {
        var isOK = 1;
        var user_id = sessionStorage.getItem("user_id");
        if (user_id == "" || user_id == null) {
            if (!islogin()) {
                isOK = -1;
                not_login();
            }
        }
        if (isOK == 1 && article != null) {
            var receiver_id = article["data"]["user"]["id"];
            var max_num = 200;
            $.confirm({
                title: '发送私信',
                content: '' +
                    '<div class="form-group">' +
                    '<textarea class="comment_post" id="post_message" placeholder="Message"></textarea>' +
                    '<span id="post_message_still" style="float:right;font-size:12px;"></span>' +
                    '</div>',
                buttons: {
                    发送: {
                        btnClass: 'btn-blue',
                        action: function() {
                            var content = this.$content.find('#post_message').val();
                            if (content == null || content == "") {
                                $.alert('内容不能为空(,,#ﾟДﾟ)');
                            } else {
                                //发送私信
                                $.ajax({
                                    url: "http://127.0.0.1:8080/Blog/message/save",
                                    type: "POST",
                                    dataType: "JSON",
                                    data: { "sender_id": user_id, "receiver_id": receiver_id, "content": content },
                                    xhrFields: {
                                        withCredentials: true
                                    },
                                    success: function(e) {
                                        if (e["code"] == 1) {
                                            $.alert({
                                                title: "提示",
                                                content: "发送成功",
                                                backgroundDismiss: true,
                                                type: 'green',
                                                typeAnimated: 'true',
                                                buttons: {
                                                    OK: {
                                                        btnClass: 'btn-green',
                                                    }
                                                }
                                            })
                                        }
                                    },
                                    error: function(err) {
                                        console.log(err);
                                        $.alert({
                                            title: '提示',
                                            content: '请求出错了(￣▽￣")',
                                        })
                                    }
                                })
                            }
                        }
                    },
                    取消: function() {
                        //close
                    },
                },
                onContentReady: function() {
                    //限制私信字数
                    var now_num = $("#post_message").val().length;
                    var still_num = (max_num - now_num) >= 0 ? (max_num - now_num) : 0;
                    $("#post_message_still").empty().append("还可以输入" + still_num + "字");
                    //检测私信字数
                    $("#post_message").blur(function() {
                        now_num = $("#post_message").val().length;
                        still_num = (max_num - now_num) >= 0 ? (max_num - now_num) : 0;
                        if (still_num == 0) {
                            $("#post_message").val($("#post_message").val().substring(0, max_num));
                        }
                        $("#post_message_still").empty().append("还可以输入<span style='color:purple;font-size:20px;'>" + still_num + "</span>字");
                    }).keyup(function() {
                        $(this).triggerHandler("blur");
                    }).focus(function() {
                        $(this).triggerHandler("blur");
                    })
                    $("#post_message").focus();
                }
            });
        }
    })

    //点赞操作
    $("#btp_like,#other_like").click(function() {
        var isOk = 1;
        var user_id = sessionStorage.getItem("user_id");
        if (user_id == "" || user_id == null) {
            if (!islogin()) {
                isOk = -1;
                not_login();
            }
        }
        if (isOk == 1 && article != null) {
            var _this = $("#btp_like,#other_like");
            // if (_this.hasClass("liked")) {
            //     _this.removeClass("liked icon-dianzan1").addClass("icon-dianzan");
            // } else {
            //     _this.removeClass("icon-dianzan").addClass("icon-dianzan1 liked");
            // }
            var article_id = article["data"]["article"]["id"];
            $.ajax({
                url: "http://127.0.0.1:8080/Blog/like/add/user/" + user_id + "/article/" + article_id,
                type: "POST",
                dataType: "JSON",
                xhrFields: {
                    withCredentials: true
                },
                success: function(data) {
                    var ori_num = $("#other_like span").data("likes_count");
                    if (data["code"] == 1) {
                        _this.removeClass("icon-dianzan").addClass("icon-dianzan1 liked");
                        $("#other_like span").empty().append(++ori_num).data("likes_count", ori_num);
                    } else {
                        _this.removeClass("icon-dianzan1 liked").addClass("icon-dianzan");
                        $("#other_like span").empty().append(--ori_num).data("likes_count", ori_num);
                    }
                }
            })
        }
    })

    //收藏操作
    $("#other_collect,#mess_collect,#btp_collect").click(function() {
        var isOk = 1;
        var user_id = sessionStorage.getItem("user_id");
        if (user_id == "" || user_id == null) {
            if (!islogin()) {
                isOk = -1;
                not_login();
            }
        }
        if (isOk == 1) {
            var article_id = article["data"]["article"]["id"];
            var _this = $("#other_collect,#mess_collect,#btp_collect");
            // if (_this.hasClass("collected")) {
            //     _this.removeClass("collected icon-shoucang1").addClass("icon-shoucang");
            // } else {
            //     _this.removeClass("icon-shoucang").addClass("icon-shoucang1 collected");
            // }
            $.ajax({
                url: "http://127.0.0.1:8080/Blog/collect/save",
                type: "POST",
                dataType: "JSON",
                data: { "collect_user_id": user_id, "collect_article_id": article_id },
                xhrFields: {
                    withCredentials: true
                },
                success: function(data) {
                    if (data["msg"] == "成功") {
                        _this.removeClass("icon-shoucang").addClass("collected icon-shoucang1");
                    } else {
                        _this.removeClass("collected icon-shoucang1").addClass("icon-shoucang");
                    }
                },
                error: function(err) {
                    console.log(err);
                }
            })
        }
    })

    //发表评论
    $("#submit_comment").click(function() {
        console.log($("#post_comment").val());
        var isOk = 1;
        var user_id = sessionStorage.getItem("user_id");
        if (user_id == null || user_id == "") {
            if (!islogin()) {
                isOk = -1;
                not_login();
            }
        }
        if (isOk == 1) {
            $.ajax({
                url: "http://127.0.0.1:8080/Blog/comment/save",
                type: "POST",
                dataType: "JSON",
                data: { "comment_content": $("#post_comment").val(), "comment_user_id": user_id, "comment_article_id": article_id, "comment_pid": comment_pid },
                xhrFields: {
                    withCredentials: true
                },
                success: function(data) {
                    console.log(data);
                    if (data["code"] == 1) {
                        $.alert({
                            title: '提示',
                            content: '评论成功(｡･ω･｡)ﾉ♡',
                            autoClose: 'OK|1000',
                            type: 'green',
                            typeAnimated: 'true',
                            buttons: {
                                OK: {
                                    btnClass: 'btn-green',
                                    action: function() {
                                        getCommentList();
                                        $("#post_comment").val("");
                                        $("#post_comment").triggerHandler("blur");
                                    }
                                }
                            }
                        });
                    }
                },
                error: function(err) {
                    console.log(err);
                }
            })
        }
    })

    //检测评论限制字数        
    $(".comment_limit_num").empty().append("还可以输入<span style='color:purple;font-size:20px;font-weight:blod;'>" + comment_num_limit + "</span>字");
    $("#post_comment").blur(function() {
        // 评论字数
        comment_post_num = $("#post_comment").val().length;
        //还可以写多少字
        var still = (comment_num_limit - comment_post_num) >= 0 ? (comment_num_limit - comment_post_num) : 0;
        if (still == 0) {
            var real_val = ($("#post_comment").val()).substring(0, comment_num_limit);
            $("#post_comment").val(real_val);
        }
        $(".comment_limit_num").empty().append("还可以输入<span style='color:purple;font-size:20px;font-weight:blod;'>" + still + "</span>字");
    }).keyup(function() {
        $(this).triggerHandler("blur");
    }).focus(function() {
        $(this).triggerHandler("blur");
    });

    //显示用户其他文章信息
    //order 0：阅读量倒序 1：点赞数倒序 2：评论数倒序 3：发布时间倒序 4：阅读量正序 5：点赞数正序 6：评论数正序 7：发布时间正序
    $.ajax({
        url: "http://127.0.0.1:8080/Blog/article/a/user/" + article["data"]["user"]["id"] + "/page/1/limit/5/order/0",
        dataType: "JSON",
        type: "GET",
        success: function(data) {
            console.log(data);
            if (data["code"] == 1) {
                $(".other_article_ul").empty();
                $.each(data["models"], function(index, item) {
                    $(".other_article_ul").append("<li class='line_text_hidden' data-article_id='" + item["article"]["id"] + "' title=" + item["article"]["title"] + ">" + item["article"]["title"] + "</li>")
                });
                // setOtherArticleClick();
                $(".other_article_ul li").click(function() {
                    $.alert($(this).data("article_id") + "");
                })
            }
        },
        error: function(err) {
            console.log(err);
        }
    })

    //显示用户文章归档
    $.ajax({
        url: "http://127.0.0.1:8080/Blog/article/listByYearAndMonth/user/" + article["data"]["user"]["id"],
        type: "GET",
        dataType: "JSON",
        success: function(data) {
            // console.log(data);
            if (data["code"] == 1) {
                var models = data["models"];
                $.each(models, function(index, item) {
                    $(".other_timeline_ul").append("<li class='line_text_hidden' data-date=" + item["date"] + "><span>" + item["date"] + "</span><span style='float:right;'>" + (item["count"] > 1000 ? "999 +" : item["count"]) + "篇</span></li>");
                });
                $(".other_timeline_ul li").click(function() {
                    $.alert($(this).data("date") + "");
                });
            }
        },
        error: function(err) {
            console.log(err);
        }

    })

    //显示用户文章分类
    $.ajax({
        url: "http://127.0.0.1:8080/Blog/category/getFromUser/" + article["data"]["user"]["id"],
        type: "GET",
        dataType: "JSON",
        success: function(data) {
            console.log(data);
            if (data["code"] == 1) {
                var models = data["models"];
                $.each(models, function(index, item) {
                    $(".other_category_ul").append("<li class='line_text_hidden' data-other_category=" + item["id"] + "><span>" + item["category_name"] + "</span><span style='float:right;'>" + (item["times"] > 1000 ? "999 +" : item["times"]) + "篇</span></li>");
                });
                $(".other_category_ul li").click(function() {
                    $.alert($(this).data("other_category") + "");
                })
            }
        },
        error: function(err) {
            console.log(err);
        }
    })

    //显示用户文章标签

    //其它文章
    $.ajax({
        url: "http://127.0.0.1:8080/Blog/article/a/page/1/limit/15/order/0",
        type: "GET",
        dataType: "JSON",
        success: function(data) {
            console.log(data);
            if (data["code"] == 1) {
                $.each(data["models"], function(index, item) {
                    $(".other_otherarticle_ul").append("<li class='line_text_hidden' data-article_id='" + item["article"]["id"] + "' title=" + item["article"]["title"] + ">" + item["article"]["title"] + "</li>")
                });
                $(".other_otherarticle_ul li").click(function() {
                    $.alert($(this).data("article_id") + "");
                })
            }
        },
        error: function(err) {
            console.log(err);
        }
    })

    //显示文章信息
    function show_article() {
        var data = article["data"];
        //修改Title
        $("title").empty().append(data["article"]["title"]);
        //显示文章内容
        $(".article_content").empty().append(data["article"]["content"]);
        //显示文章相关信息
        $("#mess_username").empty().append(data["user"]["username"]);
        $("#mess_create_time").empty().append("发布于:" + data["article"]["create_time"]);
        $("#mess_views").empty().append("阅读量:" + data["article"]["view_count"]);
        //显示文章标题
        $(".article_title").empty().append(data["article"]["title"]);
        //显示文章点赞数
        $("#other_like span").empty().append(data["article"]["likes_count"]).data("likes_count", data["article"]["likes_count"]);

        //显示分类信息
        $("#cate_list").empty();
        $.each(data["category"], function(index, item) {
            $("#cate_list").append('<li><a href="javascript:;">' + item["category_name"] + '</a></li>');
        });
        //显示标签信息
        $("#tag_list").empty();
        $.each(data["tag"], function(index, item) {
            const num = getRandomColor();
            $("#tag_list").append('<li style="background-color: ' + color[num] + '" data-tag_id=' + item["id"] + ' class="tag_list">' + item["tag_name"] + '</li>');
        });
        //获取用户点赞状态
        getLikeStatus();
        //获取用户收藏状态
        getCollectStatus();
    }

    //获取URL参数
    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) { return pair[1]; }
        }
        return (false);
    }

    //获取标签随机颜色值
    function getRandomColor() {
        const num = Math.floor(Math.random() * color.length);
        return num;
    }

    //获取评论
    function getCommentList() {
        $.ajax({
            url: "http://127.0.0.1:8080/Blog/comment/get/article/" + article_id + "/page/" + comment_page + "/limit/" + comment_limit + "/order/1",
            type: "GET",
            dataType: "JSON",
            xhrFields: {
                withCredentials: true
            },
            success: function(data) {
                if (data["code"] == 1) {
                    comment_data = data;
                    showComment();
                }
                // console.log(comment_data);
            },
            error: function(e) {
                console.log(e);
                $.alert({
                    title: '提示',
                    content: '评论请求出错了(￣▽￣")',
                })
            }
        })
    }

    //设置分页
    function setPage() {
        //分页
        $('#page_list').jqPaginator({
            totalPages: comment_data["pageInfo"]["totalpage"],
            visiblePages: 5,
            currentPage: comment_data["pageInfo"]["page"],
            first: '<li class="first"><a href="javascript:void(0);"><<<\/a><\/li>',
            prev: '<li class="prev"><a href="javascript:void(0);"><<\/a><\/li>',
            next: '<li class="next"><a href="javascript:void(0);">><\/a><\/li>',
            last: '<li class="last"><a href="javascript:void(0);">>><\/a><\/li>',
            page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
            onPageChange: function(num, type) {
                if (type == "change") {
                    page = num;

                }
            }
        });
    }

    //显示评论列表
    function showComment() {
        $("#comment_list").empty();
        $.each(comment_data["models"], function(index, item) {
            var content = item["comment_pid"] == 0 ? item["comment_content"] : ("<span style='color:#7e7e7e;'>回复" + item["p_username"] + ": </span>" + item["comment_content"]);
            $("#comment_list").append('<li data-comment_id=' + item["id"] + '><div class="comment_box"><div class="comment_box_title"><strong style="float: left;" class="comment_box_username">' + item["username"] + '</strong><span style="float: right;"><span style="margin-right:15px;"><a data-p_id=' + item["id"] + ' data-p_username=' + item["username"] + ' href="javascript:;">回复</a></span>' + item["create_time"] + '</span><div style="clear: both;"></div></div><div class="comment_box_content">' + content + '</div></div></li>');
        });
        //设置分页
        setPage();
        setReplyComment();
    }

    //设置评论分页
    function setPage() {
        //分页
        $('#page_list').jqPaginator({
            totalPages: comment_data["pageInfo"]["totalpage"],
            visiblePages: 5,
            currentPage: comment_data["pageInfo"]["page"],
            first: '<li class="first"><a href="javascript:void(0);"><<<\/a><\/li>',
            prev: '<li class="prev"><a href="javascript:void(0);"><<\/a><\/li>',
            next: '<li class="next"><a href="javascript:void(0);">><\/a><\/li>',
            last: '<li class="last"><a href="javascript:void(0);">>><\/a><\/li>',
            page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
            onPageChange: function(num, type) {
                if (type == "change") {
                    comment_page = num;
                    getCommentList();
                }
            }
        });
    }

    //检测是否有登录
    function islogin() {
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
                    sessionStorage.setItem("user_id", data["data"]["id"]);
                }
            }
        })
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

    //获取点赞状态
    function getLikeStatus() {
        var a = [];
        a.push(article["data"]["article"]["id"]);
        // console.log(article)
        $.ajax({
            url: "http://127.0.0.1:8080/Blog/like/isLikeList",
            type: "POST",
            dataType: "JSON",
            traditional: true,
            data: { "article_id": a },
            xhrFields: {
                withCredentials: true
            },
            success: function(data) {
                // console.log(data);
                if (data["code"] == 1) {
                    //根据是否已经点赞改变css
                    if (data["models"][0]["status"] == 1) {
                        $("#btp_like,#other_like").addClass("liked icon-dianzan1");
                    }
                }
            },
            error: function(err) {
                console.log(err);
            }
        })
    }

    //获取收藏状态
    function getCollectStatus() {
        $.ajax({
            url: "http://127.0.0.1:8080/Blog/collect/isCollected/" + article_id,
            type: "GET",
            dataType: "JSON",
            xhrFields: {
                withCredentials: true
            },
            success: function(data) {
                if (data["code"] == 1) {
                    $("#other_collect,#mess_collect,#btp_collect").removeClass("icon-shoucang").addClass("collected icon-shoucang1");
                }
            },
            error: function(err) {
                console.log(err);
            }
        })
    }

    //获取作者信息
    function show_UserMessage() {
        var user = article["data"]["user"];
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
            '<button class="btn btn-default btn-xs d_inline" id="follow_user">关注</button> ' +
            '<button class="btn btn-primary btn-xs d_inline" id="message_user">私信</button>' +
            '</div>';
        $("#user_mess").empty().append(m).fadeIn("slow");
    }

    //设置回复评论
    function setReplyComment() {
        //回复
        $("#comment_list a").click(function() {
            // console.log($(this).data("p_id"));
            var p_id = $(this).data("p_id");
            // $(".comment_limit_num").before("<span style=''>回复:" + p_id + "</span>");
            var p_username = $(this).data("p_username");
            comment_pid = p_id
            $(".comment_reply_user").fadeIn().empty().append("回复" + p_username + " p_id:" + comment_pid);
            $(".comment_reply_cancel").fadeIn().empty().append('<a href="javascript:;">取消回复</a>');
            $(".comment_reply_cancel").click(function() {
                comment_pid = 0;
                $(".comment_reply_user").empty().fadeOut();
                $(".comment_reply_cancel").empty().fadeOut();
            });
            $("#post_comment").focus();
            // $("#comment_list li[data-comment_id=" + p_id + "] .comment_box").append('<textarea class="comment_post" id="post_comment" placeholder="评论下呗"></textarea>');
        })
    }

    function show_other_article() {

    }

})