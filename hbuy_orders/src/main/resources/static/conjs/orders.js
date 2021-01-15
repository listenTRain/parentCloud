jQuery(function () {

    var token = null; //登陆的用户令牌

    //取到登陆模块的令牌
    getToken();

    var InterValObjLogin; //定义定时器

    var InterValObjloadOrders;  //加载订单数据的定时器

    //js轮询
    InterValObjLogin = window.setInterval(getToken, 10000);

    //js轮询加载订单数据
    InterValObjloadOrders = window.setInterval(loadOrdersByUid, 5000);


    //取到令牌
    function getToken() {
        console.log("在获取令牌。。");
        jQuery.ajax({
            type:"POST",
            xhrFields:{withCredentials:true}, //允许ajax的跨域访问获取cookie
            url:"http://localhost:8091/webusers/getToken",
            async:false,
            success:function (data) {
                console.log(data);
                if(data!=''){
                    token = data;  //将token赋值给全局变量的令牌
                    //获取到了令牌，拿着令牌去redis中找用户数据
                    getRedisLoginUser(data);
                }
            },
            error:function () {
                alert('服务器异常！！');
            }
        });
    }

    //获取到了令牌，拿着令牌去redis中找用户数据
    function getRedisLoginUser(token) {
        jQuery.ajax({
            type:"POST",
            url:"http://localhost:8091/webusers/getRedisLoginUser",
            data:{"token":token},
            success:function (data) {
                console.log(data);
                if(data!=""){
                    //关掉js轮询
                    window.clearInterval(InterValObjLogin);
                    jQuery("#loginHeader").replaceWith('<span class="fl" id="loginHeader">你好，欢迎'+data.uname+'<img src="'+data.userheader+'" width="45px;" height="45px;"/>&nbsp; &nbsp; </span>');
                    //加载订单数据
                    loadOrdersByUid(token)
                }else{
                    InterValObjLogin = window.setInterval(getToken, 10000);  //重新开启js轮询
                }
            },
            error:function () {
                alert("服务器异常！！")
            }
        });
    }

    //注销
    jQuery("#exitUser").click(function () {
        jQuery.ajax({
            type:"POST",
            url:"http://localhost:8091/webusers/exitUser",
            xhrFields:{withCredentials:true}, //允许ajax的跨域访问获取cookie
            async:false,
            success:function (data) {
                console.log(data);
                if(data){
                    token = null;  //将令牌为null
                    jQuery("#loginHeader").replaceWith('<span class="fl" id="loginHeader">你好，请<a href="model/toLogin">登录</a>&nbsp; <a href="model/toReg" style="color:#ff4e00;">免费注册</a>&nbsp; </span>');
                    InterValObjLogin = window.setInterval(getToken, 10000);  //重新开启js轮询
                    jQuery("#ordersTbody").html("");
                }
            },
            error:function () {
                alert("服务器异常！！")
            }
        });
    });

    //加载订单
    function loadOrdersByUid() {
        console.log("加载订单数据。。");
        jQuery.ajax({
            type:'POST',
            async:false,
            data:{"token":token},
            url:'/orders/loadOrdersByUid',
            success:function (data) {
                var orderStr = '';
                if(data!=""){
                    jQuery.each(data,function (i,orders) {
                        var flagStr = "";
                        if(orders.flag=="1"){
                            flagStr = "购物车";
                        }else {
                            flagStr = "秒杀";
                        }
                        var ordersStatustr = '';
                        var play = '';
                        if(orders.orderstatus=="0"){
                            ordersStatustr = "已创建";
                            play = "去支付"
                        }
                        if(orders.orderstatus=="1"){
                            ordersStatustr = "未支付";
                            play = "去支付"
                        }
                        if(orders.orderstatus=="2"){
                            ordersStatustr = "已支付";
                            play = "待收货"
                        }
                        if(orders.orderstatus=="3"){
                            ordersStatustr = "已收货";
                            play = "待评价"
                        }
                        if(orders.orderstatus=="4"){
                            ordersStatustr = "已评价";
                            play = "查看评价"
                        }
                        if(orders.orderstatus=="5"){
                            ordersStatustr = "已失效";
                            play = "删除"
                        }
                        orderStr += ' <tr>\n' +
                            '                <td><font color="#ff4e00">'+orders.orderno+'</font></td>\n' +
                            '                <td>'+orders.createdate+'</td>\n' +
                            '                <td>'+orders.enddate+'</td>\n' +
                            '                <td>￥'+orders.cost+'</td>\n' +
                            '                <td>'+ordersStatustr+'</td>\n' +
                            '                <td>'+flagStr+'</td>\n' +
                            '                <td>'+play+'</td>\n' +
                            '              </tr>';
                    })
                    jQuery("#ordersTbody").html(orderStr);
                }
            },
            error:function () {
                alert("服务器异常！！");
            }
        });
    }

});

