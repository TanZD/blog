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

	function add(){
		$("#add_s").click(function(){
			var word=$("#sen_word").val();
			if(word==""){
				layer.msg("不能为空哦~")
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
					// console.log(data)
					layer.msg(data["msg"])
					getList();
				}
			})
		})
	}

	layui.use('layer',function(){
		var layer=layui.layer;
	})

	function getList(){
		$.ajax({
			url:"http://127.0.0.1:8080/Blog/sensitive/getAll",
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
				elem: '#sen_table'
				,data: data.models
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
				if(obj.event === 'del'){
					// console.log(obj)
				  	layer.confirm('真的删除行么', function(index){
				   	 	obj.del();
				    	layer.close(index);
					    deletesen(obj.data.id);
				  	});
				}
			});
		})
	}

	function deletesen(id){
		$.ajax({
		    url: "http://127.0.0.1:8080/Blog/sensitive/delete/" + id,
		    dataType: "JSON",
		    type: "DELETE",
		    xhrFields: {
		        withCredentials: true
		    },
		    success: function(e) {
		    	layer.msg(e["msg"])
		        getList();
		    }
		});
	}

})