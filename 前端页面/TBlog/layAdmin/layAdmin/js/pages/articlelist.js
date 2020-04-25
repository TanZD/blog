$(function(){
	var data;
	var page=1;
	var limit=4;
	var isSearch=0;
	//var type;
	var word;


	var table;
	var laypage;


	getList();
	getPage();

	layui.use('layer',function(){
		var layer=layui.layer;
	})

	//搜索按键
	$("#search").click(function(){
		page=1;
		word=$("#search_word").val();
		type=$("#search_type").val();
		if(word==""){
			isSearch=0;
			getList();
		}else{
			isSearch=1;
			search(word,type);
		}
		getPage();
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
				    	if(isSearch==1){
				    		search(word);
				    	}else{
				    		getList();
				    	}
				    }
			  	}
		  	});
		});
	}

	function search(word){
		console.log(word)
		if(word!=""){
			$.ajax({
				url:'http://106.14.135.196:8080/Blog/article/search/words/'+word+'/page/'+page+'/limit/'+limit,
				type:"GET",
				contentType:"JSON",
				async:false,
				xhrFields:{
					withCredentials:true
				},
				success:function(e){
					data=e;
					console.log(data)
					getsearchTable();
				}
			})
		}else{
			getList();
		}
	}

	function getList(){
		$.ajax({
			url:"http://106.14.135.196:8080/Blog/article/a/page/"+page+"/limit/"+limit+"/order/0",
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
		limit=4;
		window.location.reload();
	}

	function getTable(){
		layui.use('table',function(){
			var table=layui.table;

			//第一个实例
			table.render({
				elem: '#art_table'
				,data: data.models
				,done:function(res,curr,count){
				}
				,cols: [[ //表头
				  {field: 'id', title: 'ID', sort: true, fixed: 'left',templet: '<div>{{d.article.id}}</div>', align:'center'}
				  ,{field: 'title', title: '标题',templet: '<div>{{d.article.title}}</div>', align:'center'}
				  ,{field: 'summary', title: '内容', templet: '<div>{{d.article.summary}}</div>', align:'center'}
				  ,{field: 'tag_name', title: '标签', templet: '<div>{{d.tag[0].tag_name}}</div>', align:'center'}
				  ,{field: 'create_time', title: '创建时间', sort: true, templet: '<div>{{d.article.create_time}}</div>', align:'center'}
				  ,{ title: '操作', align:'center',width:150, toolbar:'#barDemo'}
				]]
			});


			//监听工具条
			table.on('tool(test)', function(obj){
				var data = obj.data;
				console.log(data);
				var art = data.article;
				console.log(art);
				console.log(art.id);
				if(obj.event === 'detail'){
				  layer.msg('ID：'+ data.id + ' 的查看操作');
				  window.location.href="http://127.0.0.1:88/blog/index/articlepage.html?id="+ data.article.id ;
				} else if(obj.event === 'del'){
				  layer.confirm('真的删除行么', function(index){
				    obj.del();
				    layer.close(index);

				    $.ajax({
						url:'http://106.14.135.196:8080/Blog/article/delete/'+art.id,
						type:"delete",
						contentType:"JSON",
						async:false,
						xhrFields:{
							withCredentials:true
						},
						success:function(e){
							data=e;
							console.log(data);
							if (data.code == 1) {
								alert("删除成功");
							}else if(data.code == 0){
								alert("删除成功");
							}
						},
						error:function(jx){
							return;
						}
					})

				    del()
				  });
				}
			});

		})
	}

	function getsearchTable(){
		layui.use('table',function(){
			var table=layui.table;

			//第一个实例
			table.render({
				elem: '#art_table'
				,data: data.models
				,done:function(res,curr,count){
				}
				,cols: [[ //表头
				  {field: 'id', title: 'ID', sort: true, fixed: 'left', align:'center'}
				  ,{field: 'title', title: '标题', align:'center'}
				  ,{field: 'summary', title: '内容', align:'center'}
				  ,{field: 'create_time', title: '创建时间', sort: true, align:'center'}
				  ,{ title: '操作', align:'center',width:150, toolbar:'#barDemo'}
				]]
			});


			//监听工具条
			table.on('tool(test)', function(obj){
				var data = obj.data;
				if(obj.event === 'detail'){
				  layer.msg('ID：'+ data.id + ' 的查看操作');
				  window.location.href="http://127.0.0.1:88/blog/index/articlepage.html?id="+ obj.data.id;
				} else if(obj.event === 'del'){
				  layer.confirm('真的删除行么', function(index){
				    obj.del();
				    layer.close(index);
				    del()
				  });
				}
			});

		})
	}
})