$(function(){
    var verify=0;
    var username;
    var password;
    var layer;

    layui.use('layer',function(){
        layer=layui.layer;
    })

    //验证码
    $('#mpanel1').slideVerify({
        type : 1,       //类型
        vOffset : 5,    //误差量，根据需求自行调整
        barSize : {
            width : '80%',
            height : '40px',
        },
        ready : function() {
            verify=0;
        },
        success : function() {
            verify=1;
        },
        error : function() {
            verify=0;
        }    
    });

    $("#adminlogin").click(function(){
        username=$("input[name='username']").val();
        password=$("input[name='password']").val();
        if(username==""){
            $("input[name='username']").focus();
            layer.msg("用户名不能为空");
            return;
        }else if(password==""){
            $("input[name='password']").focus();
            layer.msg("密码不能为空");
            return;
        }else if(verify!=1){
            layer.msg("请先通过验证");
            return;
        }else{
            var data;
            //管理员登录
            $.ajax({
                url:"http://127.0.0.1:8080/Blog/user/adminlogin",
                type:"POST",
                dataType:"JSON",
                data:{"username":username,"password":password},
                xhrFields:{
                    withCredentials:true
                },
                success:function(data){
                    console.log(data)
                    if(data["code"]==1){
                        window.location.href="index.html";
                    }else{
                        layer.msg(data["msg"])
                    }
                },
                error:function(xhr){
                    console.log(xhr);
                }
            })
        }
    })

})