$(function(){
	var data;
	var page=1;
	var limit=4;
	var isSearch=0;
	var word;
	var type=1;

	var table;
	var laypage;


	getList();
	add();
	//getPage();


	$("#search").click(function(){
		type=$("#p_type").val();
		getList();
	})

	function add(){
		$("#add_s").click(function(){
			var word=$("#sen_word").val();
			if(word==""){
				alert("请输入")
				return;
			}
			$.ajax({
				url:"http://127.0.0.1:8080/Blog/sensitive/save",
				data:{"word":word},
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

	function getList(){
		$.ajax({
			url:"http://127.0.0.1:8080/Blog/media/file/"+type,
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
		// 	url:"http://127.0.0.1:8080/Blog/sensitive/delete/9"
		// })
		page=1;
		limit=2;
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
				}
				,cols: [[ //表头
				  {field: 'root', title: 'ID', sort: true, fixed: 'left', align:'center'}
				  ,{field: 'fileName', title: '文件名', align:'center'}
				  ,{field: 'fileType',width:80, title: '文件类型', align:'center'}
				  ,{ title: '操作', align:'center' , toolbar:'#barDemo'}
				]]
			});
			//监听工具条
			table.on('tool(test)', function(obj){
				var data = obj.data;
				if(obj.event === 'detail'){
					// layer.open({
					// 	type: 2,
					// 	title: false,
					// 	area: ['80%', '80%'],
					// 	shade: 0.8,
					// 	closeBtn: 0,
					// 	shadeClose: true,
					// 	content: 'http://127.0.0.1:8080' + obj.data.fileName
					// });
					var content="";
					if(obj.data.fileType==1){
						content="<img style='width: 100%; height: 100%; object-fit: scale-down;' src='http://127.0.0.1:8080"+obj.data.fileName+"'>"
						layer.open({
					  		type: 1,
					  		title: false,
				  			closeBtn: 0,
					  		shadeClose: true,
	                		area: ['500px', '500px'],
				 	 		skin: 'yourclass',
					  		content: content
						});
					}else if(obj.data.fileType==2){
						content='<audio controls="controls" src="http://127.0.0.1:8080'+obj.data.fileName+'"></audio>'
						layer.open({
					  		type: 1,
					  		title: false,
				  			closeBtn: 0,
					  		shadeClose: true,
					  		content: content
						});
					}else if(obj.data.fileType==3){
						content='<video controls="controls" src="http://127.0.0.1:8080'+obj.data.fileName+'"></video>'
						layer.open({
					  		type: 1,
					  		title: false,
				  			closeBtn: 0,
					  		shadeClose: true,
					  		content: content
						});
					}
					console.log(content)
				}else if(obj.event == 'del'){
					console.log(obj)
				  	layer.confirm('真的删除行么', function(index){
					    obj.del();
					    layer.close(index);
					    deletesen(obj.data.fileName);
					    del()
			  		});
				}else if(obj.event== 'download'){
					$("#downloadFile").attr("href","http://127.0.0.1:8080"+obj.data.fileName);
					$("#downloadFile").click();
					console.log("daa")
				}
			});
		})
	}

	function deletesen(id){
		$.ajax({
		    url: "http://127.0.0.1:8080/Blog/media/delfile/?file=" + id,
		    dataType: "JSON",
		    type: "DELETE",
		    async: false,
		    xhrFields: {
		        withCredentials: true
		    },
		    success: function(e) {
		        data = e;
		        alert(e["msg"],function(){
		        	getTable();
		        })
		        console.log(data)
		    }
		});
	}

})