$(function(){
	var data;
	var page=1;
	var limit=4;
	var isSearch=0;
	var type;
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
				    		search(word,type);
				    	}else{
				    		getList();
				    	}
				    }
			  	}
		  	});
		});
	}

	function search(word,type){
		console.log(word)
		if(word!=""){
			$.ajax({
				url:"http://106.14.135.196:8080/Blog/user/search/word/"+word+"/type/"+type+"/page/"+page+"/limit/"+limit,
				type:"GET",
				contentType:"JSON",
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
		}else{
			getList();
		}
	}

	function getList(){
		$.ajax({
			url:"http://106.14.135.196:8080/Blog/user/getList/page/"+page+"/limit/"+limit,
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
				,done:function(res,curr,count){
					$("[data-field='gender']").children().each(function(){
						if($(this).text()==1){
							$(this).text("男")
						}else if($(this).text()==0){
							$(this).text("女")
						}
					})
				}
				,cols: [[ //表头
				  {field: 'id', title: 'ID', sort: true, fixed: 'left', align:'center'}
				  ,{field: 'username', title: '用户名', align:'center'}
				  ,{field: 'gender', title: '性别', sort: true, align:'center'}
				  ,{field: 'user_mail', title: '邮箱', align:'center'}
				  ,{field: 'register_time', title: '注册时间', sort: true, align:'center'}
				  ,{ title: '操作', align:'center',width:150, toolbar:'#barDemo'}
				]]
			});


			//监听工具条
			table.on('tool(test)', function(obj){
				var data = obj.data;
				if(obj.event === 'detail'){
				  layer.msg('ID：'+ data.id + ' 的查看操作');
				  window.location.href="userInfo.html?user_id="+obj.data.id;
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