$(function(){
	var data;
	var page=1;
	var limit=15;
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
				    // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
				    // console.log(obj.limit); //得到每页显示的条数
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
		// console.log(word)
		if(word!=""){
			$.ajax({
				url:'http://127.0.0.1:8080/Blog/article/search/words/'+word+'/page/'+page+'/limit/'+limit,
				type:"GET",
				contentType:"JSON",
				async:false,
				xhrFields:{
					withCredentials:true
				},
				success:function(e){
					data=e;
					// console.log(data)
					getsearchTable();
				}
			})
		}else{
			getList();
		}
	}

	function getList(){
		$.ajax({
			url:"http://127.0.0.1:8080/Blog/article/a/page/"+page+"/limit/"+limit+"/order/3",
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

	function del(id){
		$.ajax({
			url:"http://127.0.0.1:8080/Blog/article/delete/"+id,
			dataType:"JSON",
			type:"DELETE",
			async:false,
	        xhrFields:{
	        	withCredentials:true
	        },
	        success:function(d){
	        	// console.log(d)
	        	return;
	        	if(d["code"]==1){
	        		alert("删除成功",function(){
						page=1;
						limit=15;
						window.location.reload();
	        		})
	        	}
	        }
		})
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
				  {field: 'id', title: 'ID',width:80, sort: true, fixed: 'left',templet: '<div>{{d.article.id}}</div>', align:'center'}
				  ,{field: 'title',width:200,title: '标题',templet: '<div>{{d.article.title}}</div>', align:'center'}
				  ,{field: 'summary', title: '内容', templet: '<div>{{d.article.summary}}</div>', align:'center'}
				  ,{field: 'view_count',width:80, title: '浏览数', templet: '<div>{{d.article.view_count}}</div>', align:'center'}
				  ,{field: 'comment_count',width:80, title: '评论数', templet: '<div>{{d.article.comment_count}}</div>', align:'center'}
				  ,{field: 'like_count',width:80, title: '点赞数', templet: '<div>{{d.article.likes_count}}</div>', align:'center'}
				  ,{field: 'create_time',width:120,title: '创建时间', sort: true, templet: '<div>{{d.article.create_time}}</div>', align:'center'}
				  ,{ title: '操作', align:'center',width:150, toolbar:'#barDemo'}
				]]
			});


			//监听工具条
			table.on('tool(test)', function(obj){
				var data = obj.data;
				// console.log(data);
				if(obj.event === 'detail'){
				  layer.msg('ID：'+ data.id + ' 的查看操作');
				  window.open("http://127.0.0.1:88/blog/index/articlepage.html?id="+ data.article.id);
				} else if(obj.event === 'del'){
				  layer.confirm('真的删除行么', function(index){
				    obj.del();
				    layer.close(index);
				    del(data.article.id)
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
				,limit: data.pageInfo.limit
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
				// console.log(data);
				if(obj.event === 'detail'){
				  layer.msg('ID：'+ data.id + ' 的查看操作');
				  window.open("http://127.0.0.1:88/blog/index/articlepage.html?id="+ data.id);
				} else if(obj.event === 'del'){
				  layer.confirm('真的删除行么', function(index){
				    obj.del();
				    layer.close(index);
				    del(data.article.id)
				  });
				}
			});

		})
	}
})