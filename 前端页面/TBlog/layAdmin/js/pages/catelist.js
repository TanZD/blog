$(function(){
	var data;
	var page=1;
	var limit=6;
	var order=0;
	var hot_limit=10;
	var hots_cate;
	var word;


	var table;
	var laypage;

	//获取分类列表
	getList();
	//获取分页
	getPage();
	//获取热门分类
	get_pic();


	layui.use('layer',function(){
		var layer=layui.layer;
	})

	//添加按钮
	$("#add").click(function(){
		add();
	})

	//下拉监听
	$("#sort_show").on("change",function(){
		console.log($("#sort_show").val())
	})

	layui.use(['form','layer'],function(){
		var form=layui.form;
		var layer=layui.layer;
		form.on('select(search_type)',function(){
			order=$("#sort_show").val();
			page=1;
			getList();
			getPage();
		})
	})


	// $.ajax({
	// 	url:"http://127.0.0.1:8080/Blog/user/login",
	// 	dataType:"JSON",
	// 	type:"POST",
	// 	data:{"username":"850222009@qq.com","password":"123123"},
	// 	async:false,
	//        xhrFields:{
	//        	withCredentials:true
	//        },
	// 	success:function(e){
	// 		console.log(e)
	// 	}
	// })

	function get_pic(){
		$.ajax({
			url:"http://127.0.0.1:8080/Blog/category/getHotCategory/"+hot_limit,
			dataType:"JSON",
			type:"GET",
			xhrFields:{
				withCredentials:true
			},
			success:function(data){
				console.log(data)
				if(data["models"]!=""){
					hots_cate=data["models"];
					show_pic();
				}
			}
		})
	}
	
	function show_pic(){
		var hot_cate_name=[];
		var hot_cate_num=[];
		for(var i=hots_cate.length-1;i>=0;i--){
			hot_cate_name.push(hots_cate[i]["category_name"]);
			hot_cate_num.push(hots_cate[i]["times"]);
		}
		var myChart = echarts.init(document.getElementById('pic'));
		var option = {
			    title: {
			        text: '热门分类',
			    },
			    legend: {
			        data: ['分类']
			    },
			    tooltip: {
			        trigger: 'axis'
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis: {
			        type: 'value',
			        boundaryGap: [0, 0.01],
        	 		minInterval: 1
			    },
			    yAxis: {
			        type: 'category',
			        data: hot_cate_name
			    },
			    series: [
			        {
			            name: '使用次数',
			            type: 'bar',
			            data: hot_cate_num,
			            itemStyle: {
				            normal: {
				                color: function(params) {
				                	//注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
				                    var colorList = ['#C1232B','#B5C334','#FCCE10','#E87C25','#27727B','#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD','#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'];
				                    return colorList[params.dataIndex]
				                }
				            }
		       		 	}
			        }
			    ]
			};
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	    $(window).resize(function(){
	    	myChart.resize();    
	 	}); 
	}

	function getPage(){
		layui.use(['table','laypage'], function(){
			var table = layui.table;
	  		laypage = layui.laypage;
			//分页
			laypage.render({
			    elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
			    ,count: data.pageInfo.totalNum //数据总数，从服务端得到
		  		,limit: data.pageInfo.limit
		  		,jump: function(obj, first){
				    //obj包含了当前分页的所有参数，比如：
				    // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
				    // console.log(obj.limit); //得到每页显示的条数
				    //首次不执行
				    if(!first){
				    	page=obj.curr;
				    	limit=obj.limit;
				    	getList();
				    }
			  	}
		  	});
		});
	}

	function add(word){
		word=$("#addCate").val();
		if(word!=""){
			$.ajax({
				url:"http://127.0.0.1:8080/Blog/category/save",
				type:"post",
				dataType:"JSON",
				async:false,
				data:{"category_name":word},
				xhrFields:{
					withCredentials:true
				},
				success:function(e){
					data=e;
					console.log(data)
					if(data.code == 1){
						layer.alert(data.msg)
					}else{
						layer.alert(data.msg)
					}
					getList();
				}
			})
		}else{
			layer.msg("不能为空")
		}
		word="";
		getPage();
	}

	function getList(){
		$.ajax({
			url:"http://127.0.0.1:8080/Blog/category/get/page/"+page+"/limit/"+limit+"/order/"+order,
			dataType:"JSON",
			type:"GET",
			async:false,
	        xhrFields:{
	        	withCredentials:true
	        },
			success:function(e){
				data=e;
				// console.log(data)
				getTable();
			}
		})
	}

	function del(){
		window.location.reload();
	}

	function getTable(){
		layui.use('table',function(){
			var table=layui.table;

			//第一个实例
			table.render({
				elem: '#user_table'
				,data: data.models
				,limit: data.pageInfo.limit
				,done:function(res,curr,count){
				}
				,cols: [[ //表头
				  {field: 'id', title: 'ID', sort: true, fixed: 'left', align:'center'}
				  ,{field: 'category_name', title: '分类名', align:'center'}
				  ,{field: 'create_time', title: '创建时间', sort: true, align:'center'}
				  ,{field: 'is_order', title: '首页显示', sort: true, align:'center'}
				  ,{field: 'times', title: '使用次数', sort: true, align:'center'}
				  ,{ title: '操作', align:'center',width:150, toolbar:'#barDemo'}
				]]
			});


			//监听工具条
			table.on('tool(test)', function(obj){
				var data = obj.data;
				// console.log(data);
				if(obj.event === 'detail'){
				  	layer.open({
						type: 1,
						area: ['420px', '240px'], //宽高
						content: '<div style="text-align:center;"><div style="margin: 10px 0px;">请输入要修改的值</div><div style="margin: 10px 0px;">0表示不显示再首页，值越大显示的位置越靠前</div><div class="layui-form-item seaerchbar_item"><input type="text" name="level" class="layui-input" value="'+data.is_order+'"></div><div class="layui-form-item seaerchbar_item"></div></div>',
						btn:["保存","取消"],
						yes:function(){
							if($("input[name='level']").val()!=""){
								// console.log($("input[name='level']").val())
								$.ajax({
									url:"http://127.0.0.1:8080/Blog/category/updateWeight",
									type:"POST",
									dataType:"JSON",
									data:{"id":data.id,"weight":$("input[name='level']").val()},
									success:function(data){
										if(data["code"]==1){
											layer.msg("修改成功");
											layer.closeAll();
											getList();
											getPage();
										}else{
											layer.msg("修改失败",function(){
												layer.closeAll();
											});
										}
									}
								})
							}else{
								layer.msg("不能为空")
							}
						}
					});

				} else if(obj.event === 'del'){
				  layer.confirm('真的删除这个分类吗？', function(index){
				    obj.del();
				    layer.close(index);
				    $.ajax({
						url:"http://127.0.0.1:8080/Blog/category/delete/"+data.id,
						dataType:"JSON",
						type:"delete",
						async:false,
				        xhrFields:{
				        	withCredentials:true
				        },
						success:function(e){
							data=e;
							// console.log(data);
							getList();
							getPage();
						}
					})
				  });
				}
			});

		})
	}
})