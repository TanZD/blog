$(function(){
	var data;
	var page=1;
	var limit=4;
	var isSearch=0;
	var word;

	var table;
	var laypage;


	getList();
	add();
	//getPage();

	function add(){
		$("#add_s").click(function(){
			var word=$("#sen_word").val();
			if(word==""){
				alert("请输入")
				return;
			}
			$.ajax({
				url:"http://106.14.135.196:8080/Blog/permission/save",
				data:{"url":word,'type':$("#p_type").val()},
				dataType:"JSON",
				type:"POST",
				async:false,
		        xhrFields:{
		        	withCredentials:true
		        },
				success:function(data){
					console.log(data)
					alert(data["msg"],function(){
						del();
					})
				}
			})
		})
	}

	layui.use('layer',function(){
		var layer=layui.layer;
	})

	//搜索按键
	// $("#search").click(function(){
	// 	page=1;
	// 	word=$("#search_word").val();
	// 	//type=$("#search_type").val();
	// 	if(word==""){
	// 		isSearch=0;
	// 		getList();
	// 	}else{
	// 		isSearch=1;
	// 		search(word);
	// 	}
	// 	//getPage();
	// })

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


	function search(word){
		console.log(word)
		if(word!=""){
			$.ajax({
				url:'http://106.14.135.196:8080/Blog/sensitive/search/'+word,
				type:"GET",
				contentType:"JSON",
				async:false,
				xhrFields:{
					withCredentials:true
				},
				success:function(e){
					data=e;
					console.log(data)
					getTable2(data);
				}
			})
		}else{
			getList();
		}
	}

	function getList(){
		$.ajax({
			url:"http://106.14.135.196:8080/Blog/permission/getAll",
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
		// $.ajax({
		// 	url:"http://106.14.135.196:8080/Blog/sensitive/delete/9"
		// })
		page=1;
		limit=4;
		window.location.reload();
	}


	function getTable(){
		layui.use('table',function(){
			var table=layui.table;

			//第一个实例
			table.render({
				elem: '#sen_table'
				,data: data.models
				,done:function(res,curr,count){
					$("[data-field='type']").children().each(function(){
						if($(this).text()==0){
							$(this).text("游客")
						}else if($(this).text()==1){
							$(this).text("需要登录")
						}else if($(this).text()==1){
							$(this).text("管理员登录")
					}
				}
				,cols: [[ //表头
				  {field: 'id', title: 'ID', sort: true, fixed: 'left', align:'center'}
				  ,{field: 'url', title: '地址', align:'center'}
				  ,{field: 'type',title:'等级',align:'center'}
				  ,{ title: '操作', align:'center',width:150, toolbar:'#barDemo'}
				]]
			});


			//监听工具条
			table.on('tool(test)', function(obj){
				var data = obj.data;
				// if(obj.event === 'detail'){
				//   layer.msg('ID：'+ data.id + ' 的查看操作');
				//   window.location.href="userInfo.html?user_id="+obj.data.id;
				// } else 
				if(obj.event === 'del'){
					console.log(obj)
				  layer.confirm('真的删除行么', function(index){
				    obj.del();
				    layer.close(index);
				    deletesen(obj.data.id);
				    del()

				  });
				}
			});

		})
	}

	function getTable2(data){
		layui.use('table',function(){
			var table=layui.table;

			//第二个实例
			table.render({
				elem: '#sen_table'
				,data: data.data
				,done:function(res,curr,count){
				}
				,cols: [[ //表头
				  {field: 'id', title: 'ID', sort: true, fixed: 'left', align:'center'}
				  ,{field: 'word', title: '敏感词', align:'center'}
				  ,{ title: '操作', align:'center',width:150, toolbar:'#barDemo'}
				]]
			});


			//监听工具条
			table.on('tool(test)', function(obj){
				var data = obj.data;
				// if(obj.event === 'detail'){
				//   layer.msg('ID：'+ data.id + ' 的查看操作');
				//   window.location.href="userInfo.html?user_id="+obj.data.id;
				// } else 
				if(obj.event === 'del'){
					console.log(obj)
				  layer.confirm('真的删除行么', function(index){
				    obj.del();
				    layer.close(index);
				    del()
				    $.ajax({
				    	url:"http://106.14.135.196:8080/Blog/sensitive/delete/"+id,
							dataType:"JSON",
							type:"post",
							async:false,
					        xhrFields:{
					        	withCredentials:true
					        },
							success:function(e){
								data=e;
								console.log(data)
								getTable();
							}
				    });
				  });
				}
			});

		})
	}

	function deletesen(id){
			$.ajax({
				    url: "http://106.14.135.196:8080/Blog/permission/delete/" + id,
				    dataType: "JSON",
				    type: "DELETE",
				    async: false,
				    xhrFields: {
				        withCredentials: true
				    },
				    success: function(e) {
				        data = e;
				        console.log(data)
				        getTable();
				    }
				});
	}

})