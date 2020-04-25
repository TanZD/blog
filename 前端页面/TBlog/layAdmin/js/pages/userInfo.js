$(function(){
	var form;

	//获取url参数
	function getQueryVariable(variable){
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
           var pair = vars[i].split("=");
           if(pair[0] == variable){return pair[1];}
       }
       return(false);
	}

	function getListYearMonth(){
		$.ajax({
			url:"http://127.0.0.1:8080/Blog/article/listByYearAndMonth/user/"+user_id,
			dataType:"JSON",
			xhrFields:{
				withCredentials:true
			},
			success:function(data){
				if(data["code"]==1){
					show_zhe(data);
				}
			}

		})
	}

	function getCategory(){
		$.ajax({
			url:"http://127.0.0.1:8080/Blog/category/getFromUser/"+user_id,
			dataType:"JSON",
			xhrFields:{
				withCredentials:true
			},
			success:function(data){
				if(data["code"]==1){
					show_bing(data);
				}
			}

		})
	}

	function show_zhe(data){
		var x_data=[];
		var y_data=[];
		data=data["models"];
		for(var i=0;i<data.length;i++){
			x_data.push(data[i]["date"]);
			y_data.push(data[i]["count"]);
		}
		console.log(x_data)
    	var myChart = echarts.init(document.getElementById('zhe'));
		option = {
	        title: {
	            text: '用户发布文章数'
	        },
		    xAxis: {
		        type: 'category',
		        data: x_data
		    },
		    yAxis: {
		        type: 'value',
        	 	minInterval: 1
		    },
		    series: [{
		        data: y_data,
		        type: 'line',
		        itemStyle : { normal: {label : {show: true}}}
		    }]
		};
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	    $(window).resize(function(){
	    	myChart.resize();    
	 	}); 
	}

	function show_bing(data){
		data=data["models"]
		var cate_name=[];
		var cate_times=[];
		var cate=[];
		for(var i=0;i<data.length;i++){
			cate_name.push(data[i]["category_name"]);
			cate_times.push(data[i]["times"]);
			var d={};
			d["value"]=data[i]["times"];
			d["name"]=data[i]["category_name"];
			cate.push(d);
		}
		console.log(cate)
    	var myChart = echarts.init(document.getElementById('bing'));
		var option = {
			title:{
				text:"文章分类"
			},
		    tooltip: {
		        trigger: 'item',
		        formatter: '{a} <br/>{b}: {c} ({d}%)'
		    },
		    legend: {
		        orient: 'vertical',
		        right: 0,
		        data: cate_name
		    },
		    series: [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		                show: false,
		                position: 'center'
		            },
		            emphasis: {
		                label: {
		                    show: true,
		                    fontSize: '30',
		                    fontWeight: 'bold'
		                }
		            },
		            labelLine: {
		                show: false
		            },
		            data: cate
		        }
		    ]
		};

	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	    $(window).resize(function(){
	    	myChart.resize();    
	 	}); 
	}
	

	//展示用户信息
	function show_detail(){
		console.log(user_data)
		$("input[name='id']").empty().val(user_data["id"]);
		$("input[name='username']").empty().val(user_data["username"]);
		$("input[name='user_mail']").empty().val(user_data["user_mail"]);
		$("input[name='register_time']").empty().val(user_data["register_time"]);
		$("textarea[name='profile']").empty().val(user_data["profile"]);
		$("#user_image").attr("src","http://127.0.0.1:8080/"+user_data["image"]);
		$("input[name='gender'][value="+user_data["gender"]+"]").attr('checked','');
		//layui
		console.log($("input[name='file']")[0].files[0])
		//$("input[name='file']")[0].files[0]
		//$("#file")[0].file[0]
		layui.use('form', function(){
		  form = layui.form;
		  
 		 	//监听提交
	  		form.on('submit(formDemo)', function(data){
		    	layer.msg(JSON.stringify(data.field));
			    console.log(data.field)
			    var formData=new FormData();
			    formData.append("id",data.field.id);
			    formData.append("username",data.field.username);
			    formData.append("user_mail",data.field.user_mail);
			    formData.append("gender",data.field.gender);
			    formData.append("profile",data.field.profile);
			    formData.append("register_time",data.field.register_time);
			    formData.append("file",$("input[name='file']")[0].files[0]);
			    console.log($("input[name='file']")[0].files[0])
			    console.log(formData);
			    $.ajax({
			    	url:"http://127.0.0.1:8080/Blog/user/update",
			    	data:formData,
			    	type:"POST",
			    	dataType:"JSON",
					xhrFields: {
						withCredentials: true
					},
			        processData : false, // 使数据不做处理
			        contentType : false, // 不要设置Content-Type请求头
			    	success:function(data){
			    		console.log(data)
			    		if(data["code"]==1){
			    			window.location.reload();
			    		}else{
			    			layer.msg("更新失败")
			    		}
			    	}
			    })
		    	return false;
	  		});
		});
	}

	var user_id=getQueryVariable("user_id");
	console.log(user_id);
	var user_data;

	//获取用户信息
	$.ajax({
		url:"http://127.0.0.1:8080/Blog/user/"+user_id,
		dataType:"JSON",
		success:function(data){
			console.log(data)
			if(data["code"]==1){
				user_data=data["data"];
				show_detail();
				console.log(user_data.article_count);
			$("#article_count").html(user_data.article_count);
			$("#comment_count").html(user_data.comment_count);
			$("#history_count").html(user_data.history_count);
			$("#like_count").html(user_data.like_count);
			$("#follow_count").html(user_data.follow_count);
			}
		}
	})

	//echarts
	getListYearMonth();
	getCategory();

	//设置密码
	$("#updatePass").click(function(){
		window.location.href="resetPass.html?user_id="+user_data["id"];
	})

	//上传图像
	$("#user_image").click(function(){
		$("input[name='file']").click();
	})


	$("input[name='file']").change(function(){
		var objUrl = getObjectURL(this.files[0]) ;
		console.log('objUrl = '+objUrl) ;
		if (objUrl) {
			$('#user_image').attr('src', objUrl) ;
		}
	}) ;

	//建立一個可存取到該file的url
	function getObjectURL(file) {
		var url = null ;
		if (window.createObjectURL!=undefined) { // basic
			url = window.createObjectURL(file) ;
		} else if (window.URL!=undefined) { // mozilla(firefox)
			url = window.URL.createObjectURL(file) ;
		} else if (window.webkitURL!=undefined) { // webkit or chrome
			url = window.webkitURL.createObjectURL(file) ;
		}
		return url ;
	}


	
})