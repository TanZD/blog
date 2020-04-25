$(function() {
    $(window).scroll(function() {
        // console.log($(this).scrollTop())
        if ($(this).scrollTop() > 0) {
            $('#btp').fadeIn();
            // console.log("a")
        } else {
            $('#btp').fadeOut();
            // console.log("b")
        }
    });

    //回到顶部
    $("#btp_top").click(function() {
        $('html,body').animate({ scrollTop: 0 }, 'slow');
    })

    //飞去评论区
    $("#btp_comment").click(function() {
        // console.log($(".comment_list").offset().top)
        $("html,body").animate({ scrollTop: $(".comment_list").offset().top }, 'slow');
    })
})