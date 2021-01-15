// 配置
layui.config({
	base: './hpModules/' // 扩展模块目录
}).extend({ // 模块别名 ，引入自定义模块
	hpTab: 'hpTab/hpTab',
	hpRightMenu: 'hpRightMenu/hpRightMenu',
	hpFormAll: 'hpFormAll/hpFormAll',
});

//JavaScript代码区域
layui.use(['element', 'carousel','hpTheme', 'hpTab', 'hpLayedit', 'hpRightMenu'], function() {
	
	var element = layui.element;
	var carousel = layui.carousel; //轮播
	var hpTab = layui.hpTab;
	var hpRightMenu = layui.hpRightMenu;
	var hpTheme=layui.hpTheme;
	$ = layui.$;
	
    // 初始化主题
	hpTheme.init();
	 //初始化轮播
	carousel.render({
		elem: '#test1',
		width: '100%', //设置容器宽度
		interval: 1500,
		height: '500px',
		arrow: 'none', //不显示箭头
		anim: 'fade', //切换动画方式
	});

    // 初始化 动态tab
    hpTab.init();
    // 右键tab菜单
    hpRightMenu.init();

    //用户退出
	$("#exitUser").click(function () {
        layer.confirm('你确认退出么？', function(index){
            console.log("退出");
            exitUser();
        	layer.close(index);  //关闭当前弹框
        });
    });

    function exitUser() {
        $.ajax({
            type:"POST",  //请求方式，POST请求
            url:"adminusers/exitUser",   //访问服务器端的路径
            success:function (data) {  //请求执行正常函数回调
                if(data=='success'){
                    layer.msg('你已经成功退出', {icon: 1,time:2000,anim: 4,shade:0.5});
                    //用定时器完成系统的路径跳转到酒店管理平台登陆页面
                    setTimeout('window.location = "/"',2000);
                }else {
                    layer.msg('退出失败', {icon: 2,time:2000,anim: 2,shade:0.5});
                }
            },
            error:function () {  //请求执行异常时的函数回调
                layer.msg('服务器异常', {icon: 3,time:2000,anim: 6,shade:0.5});
            }
        });
    }


});