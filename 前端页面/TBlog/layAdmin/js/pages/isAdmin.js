$(function(){
	$.ajax({
		url:"http://127.0.0.1:8080/Blog/user/islogin",
		dataType:"JSON",
		type:"POST",
		async:false,
		xhrFields:{
			withCredentials:true
		},
		success:function(data){
			if(data["code"]!=1){
				alert("请先登录",window.location.href="/Tblog/layAdmin/adminlogin.html")
			}else if(data["data"]["isAdmin"]!=1){
				alert("您不是管理员",window.location.href="/Tblog/layAdmin/adminlogin.html")
			}
		}
	})
})