	$(document).ready(function(){
		var UserNameOk=0;
		var PasswordOk=0;
		var PasswordOk2=0;
		var mailOk=0;
		var errorMsg;
		var nameVal;
		var mail;
		var passwordVal;
		var verify=0;
		//表单检测
		$("input").blur(function(){
			if($(this).is("#username")){
				nameVal=$.trim(this.value);
				var regName=/[~#^$@%&!*()<>:;'"{}【】  ]/;
				if(nameVal==""||nameVal.length<2||nameVal.length>12){
					UserNameOk=-1;
					errorMsg="用户名不能为空，长度2位以上12位以下";	
					$("#nameError").find(".msg").remove();
					$("#nameError").append("<span class='msg onError'>" + errorMsg + "</span>");
				}else if(regName.test(nameVal)){
					UserNameOk=-1;
					errorMsg="用户名包含非法字符";
					$("#nameError").find(".msg").remove();
					$("#nameError").append("<span class='msg onError'>" + errorMsg + "</span>");
				}else{
					$.ajax({
						url: "http://127.0.0.1:8080/Blog/user/getByUsername",
						type: "GET",
						dataType: "JSON",
						data: {"username": nameVal},
						success: function(data){
							if(data["code"]!=1){
								UserNameOk=-1;
								errorMsg=data["msg"];
								$("#nameError").find(".msg").remove();
								$("#nameError").append("<span class='msg onError'>" + errorMsg + "</span>");
							}else{
								$("#nameError").find(".msg").remove();
								UserNameOk=0;
							}
						}
					});

				}
			}
			if($(this).is("#password")){
				passwordVal=this.value;
				if(passwordVal.indexOf(" ")!=-1){
					PasswordOk=-1;
					errorMsg="密码中不能包含空格";
					$("#passwordError").find(".msg").remove();
					$("#passwordError").append("<span class='msg onError'>" + errorMsg + "</span>");
				}else if(passwordVal==""||passwordVal.length>18||passwordVal.length<6){
					PasswordOk=-1;
					errorMsg="密码不能为空，长度6位以上18位以下";
					$("#passwordError").find(".msg").remove();
					$("#passwordError").append("<span class='msg onError'>" + errorMsg + "</span>");
				}else{
					PasswordOk=0;
					$("#passwordError").find(".msg").remove();
				}
			}
			if($(this).is("#rPassword")){
				var passwordVal2=this.value;
				if(passwordVal2.indexOf(passwordVal)!=0){
					PasswordOk2=-1;
					errorMsg="两次密码不一致";
					$("#rPasswordError").find(".msg").remove();
					$("#rPasswordError").append("<span class='msg onError'>" + errorMsg + "</span>");
				}else{
					PasswordOk2=0;
					$("#rPasswordError").find(".msg").remove();
				}
			}
			if($(this).is("#email")){
				var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
				mail=this.value;
				if((!re.test(mail))&&mail!=""){
					mailOk=-1;
					errorMsg="格式有误";
					$("#mailError").find(".msg").remove();
					$("#mailError").append("<span class='msg onError'>" + errorMsg + "</span>");
				}else{
					$.ajax({
						url: "http://127.0.0.1:8080/Blog/user/getByMail",
						data: {"mail":mail},
						type: "GET",
						dataType: "JSON",
						success:function(data){
							if(data["code"]!=1){
								mailOk=-1;
								errorMsg=data["msg"];
								$("#mailError").find(".msg").remove();
								$("#mailError").append("<span class='msg onError'>" + errorMsg + "</span>");
							}else{
								mailOk=0;
								$("#mailError").find(".msg").remove();
							}
						}
					})
				}
			}
		}).keyup(function(){
			$(this).triggerHandler("blur");
		}).focus(function(){
			$(this).triggerHandler("blur");
		});
		
		$("#submit").click(function(){
			$("input").trigger("blur");
			//var numError=$(".onError").length;
			if(PasswordOk==-1||UserNameOk==-1||PasswordOk2==-1||mailOk==-1||verify==0){
				return false;
			}else{
				register();
			}
		});


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

	    function register_ori(){
	    	$('#loadingModal').modal('show');
	    	$.ajax({
	    		url:"http://127.0.0.1:8080/Blog/user/register",
	    		data:{"username":nameVal,"password":passwordVal,"user_mail":mail},
	    		type:"POST",
	    		dataType:"JSON",
	    		success:function(data){
	    			$('#loadingModal').modal('hide');
	    			console.log(data);
	    			if(data["code"]==1){

	    			}
	    		},
	    		error:function(xhr){
	    			console.log(xhr)
	    			$('#loadingModal').modal('hide');
	    		}
	    	})
	    }

	    function register(){
	    	$.confirm({
	    		title: "提示",
	    		buttons: {
	    			OK:function(){
	    				this.close();
	    			}
	    		},
			    content: function () {
			        var self = this;
			        return $.ajax({
			            url: 'http://127.0.0.1:8080/Blog/user/register',
			    		data:{"username":nameVal,"password":passwordVal,"user_mail":mail},
			    		type:"POST",
			    		async:false,
			    		dataType:"JSON",
			        }).done(function (data) {
			            console.log(data)
			            if(data["code"]==1){
			            	self.close();
			            	$.alert({
			            		title:"提示",
			            		content:'<div style="text-align:center">注册成功</div>',
			            		buttons:{
			            			OK:function(){
			            				window.location.href="index.html";
			            			},
			            			顺便登录了:function(){
			            				loginTheNewAccount();
			            			}
			            		}
			            	})
			            	// self.setContent('<div style="text-align:center;">注册成功</div>');
			            }else{
			            	self.setContent('<div style="text-align:center;">'+data["msg"]+'(￣.￣)+</div>');
			            }
			        }).fail(function(){
			            self.setContent('<div style="text-align:center;">出了点小问题，请刷新后重试(￣口￣)!!</div>');
			        });
			    },
			    contentLoaded: function(data, status, xhr){
			        console.log("Finished")
			        // this.setContentAppend('<br>Status: ' + status);
			    }
			});
	    }

	    function loginTheNewAccount(){
	    	$.ajax({
	    		url:"http://127.0.0.1:8080/Blog/user/login",
	    		dataType:"JSON",
	    		type:"POST",
	    		data:{"username":nameVal,"password":passwordVal},
	    		xhrFields:{
	    			withCredentials:true
	    		},
	    		success:function(data){
	    			if(data["code"]==1){
	    				window.location.href="index.html";
	    			}else{
	    				$.alert({
	    					title: "提示",
	    					content:"<div style='text-align:center;'>登陆出了点问题，请重试⊙ˍ⊙</div>",
	    					buttons:{
	    						OK:function(){
	    							window.location.href="login.html";
	    						}
	    					}
	    				})
	    			}
	    		},
	    		error:function(xhr){
	    			$.alert({
	    				title: "提示",
	    				content:"<div style='text-align:center;'>登陆出了点问题，请重试⊙ˍ⊙</div>",
    					buttons:{
    						OK:function(){
    							window.location.href="login.html";
    						}
    					}
	    			})
	    		}
	    	})
	    }

	});