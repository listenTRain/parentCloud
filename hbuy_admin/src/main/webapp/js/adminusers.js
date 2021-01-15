//AdminUsersjs文件
layui.use(['table','form','laydate','layer','jquery'], function(){
  var table = layui.table
  ,form = layui.form
  ,laydate = layui.laydate
  ,layer = layui.layer
  ,$ = layui.jquery;

//日期，账号最新变动时间
laydate.render({
    elem: '#updatetime',
    format:'yyyy/MM/dd HH:mm:ss',
	type:'datetime'
});
  
  var currentPage = 1;
  
  //初始化
  loadAdminUsers();


  
function loadAdminUsers(jsonEntity){
  //方法级渲染
  table.render({
   elem: '#listTable'
   ,url: '/adminusers/listByPramas'
   ,width:1560
   ,height: 480
   ,page:true
   ,even:true
   ,where:jsonEntity
   ,limit:5
   ,limits:[2,3,5,8,10]
   ,cols: [[
     {checkbox: true}
     ,{field:'id', title: 'ID',align:'center', width:80, sort: true}
     ,{field:'username', title: '用户名',align:'center', width:260, sort: true}
     ,{field:'userheader', title: '用户头像',align:'center', width:160, sort: true,templet:'<div><img src="{{d.userheader}}"/></div>'}
     ,{field:'pwd', title: '密码',align:'center', width:160, sort: true}
     ,{field:'isroot', title: '1超级管理员，0普通管理员',align:'center', width:160, sort: true}
     ,{field:'updatetime', title: '账号最新变动时间',align:'center', width:360, sort: true}
	  ,{fixed: 'right',title: '操作', width:256, align:'center', toolbar: '#bar'}
    ]] 
    ,done: function(res, curr, count){
    	currentPage = curr;
    	// hoverOpenImg();
	  }
  });
  }
  
  //监听工具条
  table.on('tool(list)', function(obj){
    var data = obj.data;
    if(obj.event === 'show'){
    	layer.alert('查看行：<br>'+ JSON.stringify(data))
    } else if(obj.event === 'del'){
      layer.confirm('真的删除行么', function(index){
        var jsonEntity = {};
        jsonEntity['id'] = data.id;
        czAdminUsers("/adminusers/delete",jsonEntity,obj);
        layer.close(index);
      });
    } else if(obj.event === 'edit'){
      layer.open({
  		type:1,
  		title:"信息编辑",
  		area:['400px','500px'],
		anim:4,
        shade:0.6,
  		content:$("#updAdminUsersDiv"),
  		success:function(){
        form.val("updAdminUsersForm", {
    "username": data.username,
    "pwd": data.pwd,
    "isroot": data.isroot,
    "updatetime": data.updatetime,
    "userheader": data.userheader,
        });

  		          //监听提交
			  form.on('submit(submitUpdAdminUsers)', function(FormData){
			    var jsonEntity = FormData.field
			    jsonEntity.id = data.id;
			    czAdminUsers("/adminusers/updT",jsonEntity,obj);
			    layer.closeAll();
			    return false;
			  });
  		}
  	});
    } 
  });

    $("#delBatchAdminUsers").click(function(){
      var data = table.checkStatus('listTable').data;
	   if(data.length<1){
          layer.msg("请选择数据！！！",{"icon":3,anim:6,shade:0.6,"time":2000});
	   }else {
           layer.confirm("你确定要删除所选中的数据吗？？？", {icon: 7, title: '提示'}, function (index) {
               var params = "";
               for (var i = 0; i < data.length; i++) {
                   params += data[i].id+ ",";
               }
               delManyAdminUsers("/adminusers/deletes", params);
               flush(currentPage);
               layer.close(index);
           });
       }
    });
  

  
  //刷新
  $("#flush").click(function(){
	  flush(currentPage);
  });
  
  //添加
  $("#saveUI").click(function(){
	  layer.open({
  		type:1,
  		title:"添加页面",
  		area:['600px','500px'],
	    anim:4,
	    shade:0.6,
  		content:$("#updAdminUsersDiv"),
  		success:function(){
  		   $('form').eq(0).find('input').val('')
  		  //监听提交
		  form.on('submit(submitUpdAdminUsers)', function(FormData){
            var jsonEntity = FormData.field
		    czAdminUsers("/adminusers/saveT",jsonEntity);
		    layer.closeAll();
		    return false;
		  });
  		}
  	});
  });
  
  function flush(currentPage){
	  table.reload('listTable', {
	        page: {
	          curr: currentPage 
	        }
	     });
  }
  
  //普通增删改操作
  function czAdminUsers(url,jsonEntity,obj){
	  $.ajax({
		   	type:"post",
		   	url:url,
		   	data:jsonEntity,
		   	success:function(data){
		   		if(data=="delSuccess"){
	   				layer.msg("删除单个成功。。。",{"icon":6,anim:4,shade:0.6,"time":2000});
		   		    obj.del();
		   		}else if(data=="updSuccess"){
	   				layer.msg("信息修改成功。。。",{"icon":6,anim:4,shade:0.6,"time":2000});
		   			 obj.update({
                                                                                                                username: jsonEntity.username,
                                                                                         pwd: jsonEntity.pwd,
                                                                                         isroot: jsonEntity.isroot,
                                                                                         updatetime: jsonEntity.updatetime,
                                                                                         userheader: jsonEntity.userheader,
                                           		   	      });
		   		}else if(data=="saveSuccess"){
		   		    layer.msg("数据添加改成功。。。",{"icon":6,anim:4,shade:0.6,"time":2000});
		   		    flush(1);
		   		}else{
		   			layer.msg("操作失败！！！",{"icon":2,anim:4,shade:0.6,"time":2000});
		   		}	
		   	},
		   	error:function(){
		   		layer.msg("操作异常！！！",{"icon":3,anim:6,shade:0.6,"time":2000});
		   	}
	   });
  }
  
  //批量删除
  function delManyAdminUsers(url,ids){
	  $.ajax({
			type:"post",
			url:url,
			data:{'ids':ids},
			success:function(data){
				if(data=="delSuccess"){
					layer.msg("批量删除数据成功。。",{"icon":6,anim:4,shade:0.6,"time":2000});
					 flush(currentPage);
				}else{
					layer.msg("批量删除数据失败！！",{"icon":2,anim:4,shade:0.6,"time":2000});
				}
			},
			error:function(){
				layer.msg("对不起，服务器异常！！！",{"icon":3,anim:6,shade:0.6,"time":2000});
			}
		});
  }


    /*//图片放大镜
    function hoverOpenImg(){
        var img_show = null; // tips提示
        $('td img').hover(function(){
            var img = "<img class='img_msg' src='"+$(this).attr('src')+"' style='width:230px;' />";
            img_show = layer.tips(img, this,{
                tips:[2, 'rgba(41,41,41,.5)']
                ,area: ['260px']
            });
        },function(){
            layer.close(img_show);
        });
        $('td img').attr('style','max-width:70px');
    }*/
});
 




