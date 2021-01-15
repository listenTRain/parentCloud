$(function () {

    var InterValObjIf = true;

    var InterValObj;

    if(InterValObjIf){
        //js轮询加载秒杀商品
        InterValObj = window.setInterval(loadUPSecKill, 2000);
    }

    var token = null; //登陆的用户令牌

    //取到登陆模块的令牌
    getToken();

    var InterValObjLogin; //定义定时器

    //js轮询
    InterValObjLogin = window.setInterval(getToken, 10000);


    //取到令牌
    function getToken() {
        console.log("在获取令牌。。")
        jQuery.ajax({
            type:"POST",
            xhrFields:{withCredentials:true}, //允许ajax的跨域访问获取cookie
            url:"http://localhost:8091/webusers/getToken",
            async:false,
            success:function (data) {
                console.log(data);
                if(data!=''){
                    //关掉js轮询
                    window.clearInterval(InterValObjLogin);
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
                    jQuery("#buyCarDiv").html("<font color='red'>已登录</font>")
                    jQuery("#loginHeader").replaceWith('<span class="fl" id="loginHeader">你好，欢迎'+data.uname+'<img src="'+data.userheader+'" width="45px;" height="45px;"/>&nbsp; &nbsp; </span>');
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
                }
            },
            error:function () {
                alert("服务器异常！！")
            }
        });
    })


    //加载可以秒杀的商品
    function loadUPSecKill(){
        //  console.log("执行了轮询。。")
        $.ajax({
            type:'POST',
            async:false,
            url:'/seckill/loadUPSecKill',
            success:function (data) {
                console.log(data);
                if(data.length!=0){
                    var liStr = '';
                    $.each(data,function (i,map) {
                        liStr += '<li>\n' +
                            '                \t<div class="img"><img src="'+map.avatorImg+'" width="160" height="140" /></div>\n' +
                            '                    <div class="name">'+map.title+'</div>\n' +
                            '                    <div class="price">\n' +
                            '                    \t<table border="0" style="width:100%; color:#888888;" cellspacing="0" cellpadding="0">\n' +
                            '                          <tr style="font-family:\'宋体\';">\n' +
                            '                            <td width="33%">市场价 </td>\n' +
                            '                            <td width="33%">优惠</td>\n' +
                            '                            <td width="33%">秒杀价</td>\n' +
                            '                          </tr>\n' +
                            '                          <tr>\n' +
                            '                            <td style="text-decoration:line-through;">￥'+map.price+'</td>                   \n' +
                            '                            <td>'+(map.price-map.seckillPrice)+'</td>\n' +
                            '                            <td>￥'+map.seckillPrice+'</td>\n' +
                            '                          </tr>\n' +
                            '                        </table>\n' +
                            '                    </div>\n' +
                            '                    <div class="ch_bg">\n' +
                            '                        <span class="ch_txt">￥<font>'+map.seckillPrice+'</font></span>\n' +
                            '                        <button class="ch_a" id="seckill" style="background-color: darkorange" secId="'+map.id+'">秒杀</button>\n' +
                            '                    </div>\n' +
                            '                    <div class="times">结束时间：'+map.endTime+'<p id="show"></p></div>\n' +
                            '                </li>';
                    })
                    $("#secKillUl").html(liStr);
                    TimeDown("show", data[0].endTime);  //开启倒计时
                    InterValObjIf = false;
                    window.clearInterval(InterValObj);
                }else {
                    $("#secKillUl").html('<font color="red" size="40px;">对不起，暂时没有秒杀商品！！</font>');
                }

            },
            error:function () {
                alert("服务器异常！！");
            }
        });


    }

    $("#secKillUl").on("click","#seckill",function () {
        var secId = $(this).attr("secId");
        var abtn = $(this);
        console.log(secId);
        console.log(token);
        //执行秒杀
        doSecKill(secId,token,abtn);
        return false;
    })

    //秒杀商品
    function doSecKill(secId,token,abtn) {
        $.ajax({
            type: 'POST',
            data: {"secId": secId, "token": token},
            async: false,
            url: '/seckill/doSeckill',
            success: function (data) {
                alert(data.msg);
                if(data.code==200){
                    //    abtn.css("background-color","gray");
                    //   abtn.attr("disabled","disabled");
                    return false;
                }
            },
            error: function () {
                alert("服务器异常！！");
            }
        });
    }

    //倒计时插件
    function TimeDown(id, endDateStr) {
        //结束时间
        var endDate = new Date(endDateStr);
        //当前时间
        var nowDate = new Date();
        //相差的总秒数
        var totalSeconds = parseInt((endDate - nowDate) / 1000);
        //天数
        var days = Math.floor(totalSeconds / (60 * 60 * 24));
        //取模（余数）
        var modulo = totalSeconds % (60 * 60 * 24);
        //小时数
        var hours = Math.floor(modulo / (60 * 60));
        modulo = modulo % (60 * 60);
        //分钟
        var minutes = Math.floor(modulo / 60);
        //秒
        var seconds = modulo % 60;
        //输出到页面
        document.getElementById(id).innerHTML = "还剩:" + days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
        //延迟一秒执行自己
        var time = setTimeout(function () {
            TimeDown(id, endDateStr);
        }, 1000)
        if(totalSeconds==0){
            window.clearTimeout(time);
            $("#secKillUl button").attr("disabled","disabled");
            $("#secKillUl button").css("background-color","gray");
            $("#show").html("秒杀已结束，欢迎下次再来")
        }
    }
});