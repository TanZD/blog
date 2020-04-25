$(function(){
	var data;
	var page=1;
	var limit=2;
	var isAdd=0;
	//var type;
	var word;


	var table;
	var laypage;


	getList();
	// getPage();

	layui.use('layer',function(){
		var layer=layui.layer;
	})

	//新增按键
	$("#add").click(function(){
		page=1;
		word=$("#search_word").val();
		//type=$("#search_type").val();
		if(word==""){
			isAdd=0;
			getList();
		}else{
			isAdd=1;
			add(word);
		}
		// getPage();
	})

	// function getPage(){
	// 	layui.use(['table','laypage'], function(){
	// 		var table = layui.table;
	//   		laypage = layui.laypage;
	// 		//分页
	// 		laypage.render({
	// 		    elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
	// 		    ,count: data.pageInfo.totalNum //数据总数，从服务端得到
	// 	  		,limit: data.pageInfo.limit
	// 	  		,jump: function(obj, first){
	// 			    //obj包含了当前分页的所有参数，比如：
	// 			    console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
	// 			    console.log(obj.limit); //得到每页显示的条数
	// 			    //首次不执行
	// 			    if(!first){
	// 			    	page=obj.curr;
	// 			    	limit=obj.limit;
	// 			    	if(isSearch==1){
	// 			    		search(word,type);
	// 			    	}else{
	// 			    		getList();
	// 			    	}
	// 			    }
	// 		  	}
	// 	  	});
	// 	});
	// }

	function add(word){
		console.log(word)
		if(word!=""){
			$.ajax({
				url:"http://106.14.135.196:8080/Blog/category/save",
				type:"post",
				contentType:"JSON",
				async:false,
				data:{"category_name":word},
				xhrFields:{
					withCredentials:true
				},
				success:function(e){
					data=e;
					console.log(data)
					if(data.code == 1){
						alert("添加分类成功")
					}else{
						alert("添加分类失败")
					}
					getTable();
				}
			})
		}else{
			getList();
		}
	}

	function getList(){
		$.ajax({
			url:"http://106.14.135.196:8080/Blog/category/getAll",
			dataType:"JSON",
			type:"GET",
			async:false,
	        xhrFields:{
	        	withCredentials:true
	        },
			success:function(e){
				data=e;
				console.log(data)
				getTable();
			}
		})
	}

	function del(){
		//del
		page=1;
		limit=2;
		window.location.reload();
	}

	function getTable(){
		layui.use('table',function(){
			var table=layui.table;

			//第一个实例
			table.render({
				elem: '#user_table'
				,data: data.models
				,limit: 1000 //显示的数量
				,done:function(res,curr,count){
				}
				,cols: [[ //表头
				  {field: 'id', title: 'ID', sort: true, fixed: 'left', align:'center'}
				  ,{field: 'category_name', title: '分类名', align:'center'}
				  ,{field: 'create_time', title: '创建时间', sort: true, align:'center'}
				  ,{ title: '操作', align:'center',width:150, toolbar:'#barDemo'}
				]]
			});


			//监听工具条
			table.on('tool(test)', function(obj){
				var data = obj.data;
				console.log(data);
				if(obj.event === 'detail'){
				  layer.msg('ID：'+ data.id + ' 的查看操作');
				  window.location.href="javascript:;";
				} else if(obj.event === 'del'){
				  layer.confirm('真的删除这个分类吗？', function(index){
				    obj.del();
				    layer.close(index);
				    $.ajax({
						url:"http://106.14.135.196:8080/Blog/category/delete/"+data.id,
						dataType:"JSON",
						type:"delete",
						async:false,
				        xhrFields:{
				        	withCredentials:true
				        },
						success:function(e){
							data=e;
							console.log(data);
						}
					})
				    del()
				  });
				}
			});

		})
	}
})