$(function(){
	var verify=0;
	var username;
	var password;

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

    $("#submit").click(function(){
    	username=$("#username").val();
    	password=$("#password").val();
    	if(username==""){
    		$("#username").attr("placeholder","不能为空哦~").focus();
    		return;
    	}else if(password==""){
    		$("#password").focus();
    		return;
    	}else if(verify==0){
    		$("#mpanel1").focus();
    		return;
    	}else{
    		$.ajax({
    			url:"http://127.0.0.1:8080/Blog/user/login",
    			data:{"username":username,"password":password},
    			dataType:"JSON",
    			type:"POST",
    			xhrFields:{
    				withCredentials:true
    			},
    			success:function(data){
    				if(data["code"]!=1){
    					$.alert({
    						title: '提示',
    						content: '<div style="text-align:center">'+data["msg"]+'( ¬､¬)</div>',
    						buttons:{
    							OK:function(){
    								$("#password").val("");
    							}
    						}
    					})
    				}else{
    					window.location.href="index.html";
    				}
    			},
    			error:function(xhr){
    				console.log(xhr)
    				$.alert({
					    title: '提示',
					    content: '<div style="text-align:center;">登陆出了点问题，请刷新重试⊙ˍ⊙</div>'
					});
    			}
    		})
    	}
    })

})