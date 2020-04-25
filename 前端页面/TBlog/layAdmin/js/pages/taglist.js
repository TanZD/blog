$(function(){
	var data;
	var page=1;
	var limit=5;
	var hot_limit=10;
	var order=0;
	var hots_tag;
	var word;


	var table;
	var laypage;

	//获取标签列表
	getList();
	//获取列表分页
	getPage();
	//获取热门列表
	get_pic();

	layui.use('layer',function(){
		var layer=layui.layer;
	})

	//新增按键
	$("#addtag").click(function(){
		page=1;
		word=$("#add_wrod").val();
		var a = confirm("确定新增标签？");
		if (a==true) {
			addtag(word);
	 	}
	})

	//下拉选择框监听
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

	function get_pic(){
		$.ajax({
			url:"http://127.0.0.1:8080/Blog/tag/getHotTags/"+hot_limit,
			dataType:"JSON",
			type:"GET",
			xhrFields:{
				withCredentials:true
			},
			success:function(data){
				console.log(data)
				if(data["models"]!=""){
					hots_tag=data["models"];
					show_pic();
				}
			}
		})
	}

	function show_pic(){
		var hot_tag_name=[];
		var hot_tag_num=[];
		for(var i=hots_tag.length-1;i>=0;i--){
			hot_tag_name.push(hots_tag[i]["tag_name"]);
			hot_tag_num.push(hots_tag[i]["times"]);
		}
		var myChart = echarts.init(document.getElementById('pic'));
		var option = {
			    title: {
			        text: '热门标签',
			    },
			    legend: {
			        data: ['标签']
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
			        data: hot_tag_name
			    },
			    series: [
			        {
			            name: '使用次数',
			            type: 'bar',
			            data: hot_tag_num,
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
				    console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
				    console.log(obj.limit); //得到每页显示的条数
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

	function addtag(word){
		if(word!=""){
			$.ajax({
				url:"http://127.0.0.1:8080/Blog/tag/save",
				type:"post",
				dataType:"JSON",
				async:false,
				data:{"tag_name":word},
				xhrFields:{
					withCredentials:true
				},
				success:function(e){
					data=e;
					console.log(data);
					if (data.code == 1) {
						alert("添加标签成功")
					}else{
						alert("添加标签失败")
					}
					getList();
					getPage();
				},
				error:function(jx){
					alert("添加标签失败");
				}
			})
		}else{
			layer.msg("不能为空")
			getList();
			getPage();
		}
	}

	function getList(){
		$.ajax({
			url:"http://127.0.0.1:8080/Blog/tag/get/page/"+page+"/limit/"+limit+"/order/"+order,
			dataType:"JSON",
			type:"GET",
			async:false,
	        xhrFields:{
	        	withCredentials:true
	        },
			success:function(e){
				data=e;
				console.log(e)
				getTable();

			}
		})
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
				  ,{field: 'tag_name', title: '标签名', align:'center'}
				  ,{field: 'times', title: '使用次数', sort: true, align:'center'}
				  ,{field: 'create_time', title: '创建时间', sort: true, align:'center'}
				  ,{ title: '操作', align:'center',width:150, toolbar:'#barDemo'}
				]]
			});


			//监听工具条
			table.on('tool(test)', function(obj){
				var data = obj.data;
				console.log(data.id);
				if(obj.event === 'detail'){
				  layer.msg('ID：'+ data.id + ' 的查看操作');
				  window.location.href="javascript:;";
				} else if(obj.event === 'del'){
				  layer.confirm('真的删除数据么', function(index){
				    obj.del();
				    layer.close(index);
				    	$.ajax({
							url:"http://127.0.0.1:8080/Blog/tag/delete/"+data.id,
							dataType:"JSON",
							type:"delete",
							async:false,
					        xhrFields:{
					        	withCredentials:true
					        },
							success:function(e){
								data=e;
								console.log(data);
								alert("删除成功");
								getList();
								getPage();
							},
							error:function(jx){
								alert("删除失败");
								return;
							}
						})
				  });
				}
			});

		})
	}
})