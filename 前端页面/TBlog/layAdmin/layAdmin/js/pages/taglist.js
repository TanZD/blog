$(function(){
	var data;
	var page=1;
	var limit=2;
	var isAdd=0;
	var type;
	var word;


	var table;
	var laypage;


	getList();
	getPage();

	layui.use('layer',function(){
		var layer=layui.layer;
	})

	//新增按键
	$("#addtag").click(function(){
		page=1;
		word=$("#add_wrod").val();
		//type=$("#search_type").val();
		 var a = confirm("确定新增标签？");
		 if (a==true) {
		 	if(word==""){
			isAdd=0;
			getList();
		}else{
			isAdd=1;
			addtag(word);
		}
		 }else{
		 	trturn;
		 }
		
		 //getPage();
	})

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
				    	if(isAdd==1){
				    		addtag(word);
				    	}else{
				    		getList();
				    	}
				    }
			  	}
		  	});
		});
	}

	function addtag(word){
		console.log(word)
		if(word!=""){
			console.log(word)
			// return;
			$.ajax({
				url:"http://106.14.135.196:8080/Blog/tag/save",
				type:"post",
				contentType:"JSON",
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
				},
				error:function(jx){
					alert("添加标签失败");
				}
			})
		}else{
			getList();
		}
	}

	function getList(){
		$.ajax({
			url:"http://106.14.135.196:8080/Blog/tag/get/page/"+page+"/limit/3/order/0",
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
		limit=3;
		window.location.reload();
	}

	function getTable(){
		layui.use('table',function(){
			var table=layui.table;

			//第一个实例
			table.render({
				elem: '#user_table'
				,data: data.models
				,done:function(res,curr,count){
				}
				,cols: [[ //表头
				  {field: 'id', title: 'ID', sort: true, fixed: 'left', align:'center'}
				  ,{field: 'tag_name', title: '标签名', align:'center'}
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
							url:"http://106.14.135.196:8080/Blog/tag/delete/"+data.id,
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

							},
							error:function(jx){
								alert("删除失败");
								return;
							}
						})
				    del()
				  });
				}
			});

		})
	}
})