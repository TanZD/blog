$(function(){
	//加载用户文章数排行榜
	$.ajax({
		url:"http://127.0.0.1:8080/Blog/user/orderByArticle/5",
		dataType:"JSON",
		success:function(data){
			if(data["code"]==1){
				$("#user_list").empty();
				for(var i=0;i<data["models"].length;i++){
					var m=data["models"][i];
					$("#user_list").append("<tr><td>"+(i+1)+"</td><td>"+m["username"]+"</td><td style='text-align:center'><span class='layui-badge layui-bg-green'>"+m["count"]+"</span></td></tr>")
				}
			}
		}
	})

	//加载最新文章列表
	$.ajax({
		url:"http://127.0.0.1:8080/Blog/article/a/page/1/limit/5/order/3",
		dataType:"JSON",
		success:function(data){
			// console.log(data)
			if(data["code"]==1){
				$("#article_list").empty();
				for(var i=0;i<data["models"].length;i++){
					var m=data["models"][i];
					if(m["article"]["is_article"]!=0){
						$("#article_list").append("<tr><td>"+(i+1)+"</td><td><a target='_blank' href='/blog/index/articlepage.html?id="+m["article"]["id"]+"'>"+m["article"]["title"]+"</a></td><td style='text-align:center'>"+m["article"]["create_time"]+"</td></tr>")				
					}
				}
			}
		}
	})

	//加载用户注册时间柱形图
	var time_sq=[];
	var time_num=[];
	$.ajax({
		url:"http://127.0.0.1:8080/Blog/user/orderByRegisterTime/10/1",
		dataType:"JSON",
		success:function(data){
			// console.log(data)
			if(data["code"]==1){
				for(var i=0;i<data["models"].length;i++){
					time_sq.push(data["models"][i]["register_time"])
					time_num.push(data["models"][i]["count"])
					pic();
				}
			}
		}
	})

    function pic(){
    	var myChart = echarts.init(document.getElementById('main'));
	    // 指定图表的配置项和数据
	    var option = {
	    	baseOption:{
		        title: {
		            text: '近期注册人数'
		        },
		        tooltip: {},
		        legend: {
		            data:['注册人数']
		        },
		        xAxis: {
		            data: time_sq
		        },
		        yAxis: {
	        	 	minInterval: 1
		        },
		        series: [{
		            name: '注册人数',
		            type: 'bar',
		            data: time_num
		        }]
	    	}
	    };
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	    $(window).resize(function(){
	    	myChart.resize();    
	 	}); 
    }


})