layui.use(['table','form','laydate','layer','jquery'], function() {
    var table = layui.table
        , form = layui.form
        , laydate = layui.laydate
        , layer = layui.layer
        , $ = layui.jquery;

    var usernameIf = false; //验证用户名唯一性的变量

    var phoneIf = false;  //验证手机号唯一性的变量

    var code = '';  //手机短信验证码

    var curCount = 60;  //倒计时的时间/单位为s


    //自定义表单验证
    form.verify({
        username: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value.length < 3 || value.length > 12) {
                return '用户名必须为3-12位';
            }
            checkUsername(value);  //验证用户名的唯一性
            if(!usernameIf){
                return '此用户名已被占用';
            }
        },
        pwd: [/^[\S]{6,12}$/,'密码必须6到12位，且不能出现空格'],
        pwd2:function (value, item) {
            if(value!=$("#pwd").val()){
                return '两次密码不一致';
            }
        },
        regPhone:function (value, item) { //value：表单的值、item：表单的DOM对象
            checkPhone(value);  //验证用户名的唯一性
            if(!phoneIf){
                return '此手机号已被使用';
            }
        },
        viyzm:function (value, item) {
            if(value!=code){  //验证码匹配
                return '验证码输入错误';
            }
        }
    });

    //点击短信发送
    $("#send_btn").click(function () {
        //开启验证
        var reg = /^1[3456789]\d{9}$/;
        if(reg.test($("#phone").val())){  //正则验证
            checkPhone($("#phone").val());  //验证用户名的唯一性
        }else {
            layer.tips('手机号格式不正确！！', $("#phone"), {tips: [3,'#fc1505'],time:2000,});
        }
        if(phoneIf){
            //发送手机短信
            layer.msg("手机验证码已发送");
            //获取随机验证码（6位数）
            code = getCode(6);
            //将用户输入的手机号和产生的验证码发送到服务器完成短信发送
            sendSms($("#phone").val(),code);
        }else {
            layer.msg('手机号异常！！', {icon: 7,time:2000,anim: 6,shade:0.5});
        }
    });

    //监听注册的添加提交
    form.on('submit(demo2)',function (data) {
        var saveJsonWebUser = data.field;
        saveJsonWebUser['uname'] = '我的蛇皮名称';
        saveJsonWebUser['updatetime'] = getNowDate(new Date());
        saveJsonWebUser['userheader'] = 'http://q6eepizvg.bkt.clouddn.com/fm1.jpg';
        saveWebUser(saveJsonWebUser);
        return false;  //阻止表单进行action提交
    });

    /************************验证函数*************************/

    //验证用户名唯一性
    function checkUsername(username) {
        $.ajax({
            type:"POST",  //请求方式，POST请求
            url:"webusers/getTotalByPramas",   //访问服务器端的路径
            async:false,  //允许ajax外部的变量获得去数据
            data:{'username':username},
            success:function (data) {  //请求执行正常函数回调
                if(data>0){
                    usernameIf = false;
                }else {
                    usernameIf = true;
                }
            },
            error:function () {  //请求执行异常时的函数回调
                layer.msg('服务器异常', {icon: 3,time:2000,anim: 6,shade:0.5});
            }
        })
    }

    //验证手机号唯一性
    function checkPhone(phone) {
        $.ajax({
            type: "POST",  //请求方式，POST请求
            url: "webusers/getTotalByPramas",   //访问服务器端的路径
            async: false,  //允许ajax外部的变量获得去数据
            data: {'phone': phone},
            success: function (data) {  //请求执行正常函数回调
                if (data > 0) {
                    layer.tips('手机号已被使用！！', $("#phone"), {tips: [3, '#fc1505'], time: 2000,});
                    phoneIf = false;
                } else {
                    //   layer.tips('手机号可用！！', $("#phone"), {tips: [3,'green'],time:2000,});
                    phoneIf = true;
                }
            },
            error: function () {  //请求执行异常时的函数回调
                layer.msg('服务器异常', {icon: 3, time: 2000, anim: 6, shade: 0.5});
            }
        })
    }

    //产生随机验证码
    function getCode(codeLength) {
        //产生验证码
        var icon = "";
        var sCode = "0,1,2,3,4,5,6,7,8,9";
        var aCode = sCode.split(",");
        var aLength = aCode.length;
        for (var i = 1; i <= codeLength; i++) {
            var j = Math.floor(Math.random() * aLength);
            //不想在验证码第1位产生0
            if (i == 1 && aCode[j] == '0') {
                icon += 1;
            } else {
                icon += aCode[j];
            }
        }
        console.log("code=" + icon);//打印发送的验证码
        return icon;
    }

    //发送短信验证码函数
    function sendSms(phone,code){
        $.ajax({
            url:"webusers/sendSms",
            style:"post",
            data:{"phone":phone,"code":code},
            success:function (data) {
                if (data == "OK"){
                    layer.msg("短信验证码已发送。。。",{"icon":1,anim:4,shade:0.6,"time":2000});
                    sendSmsOK();
                }else {
                    layer.msg('短信验证码发送失败！！', {icon: 2,time:2000,anim: 3,shade:0.5});
                }
            },
            error:function () {
                layer.msg('服务器异常', {icon: 3,time:2000,anim: 6,shade:0.5});
            }
        });
    }

    //发送信息成功
   function sendSmsOK() {
       //设置button效果，开始计时
       $("#send_btn").attr("disabled", "true");  //将发送短信的按钮设置为不可用
       $("#phone").attr("disabled", "true");  //手机号的输入框设置为不可用
       $("#send_btn").css('background-color', "grey");  //将发送短信的按钮背景色设置为灰色
       $("#send_btn").val("剩余" + curCount + "秒");  //将发送短信的按钮设计倒计时（从60秒开始）
       InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
      //自定义验证码持续时间，单位毫秒
      /* window.setTimeout(function () {
           code = "";
       },120000);
       window.setInterval(function () {
           console.log("code = "+code);
       },3000)*/
   }

    //timer处理函数(定时器执行的方法，进行倒计时)
    function SetRemainTime() {
        if (curCount == 0) {  //剩余的时间为0，表示倒计时已结束
            window.clearInterval(InterValObj);//停止计时器，清除定义的定时器
            $("#send_btn").removeAttr("disabled");//启用按钮
            $("#phone").removeAttr("disabled");//启用输入手机号
            $("#send_btn").css('background-color', "blue");
            $("#send_btn").val("重新发送");
            //        smsIf = false;   //将判断验证码是否可用改为false
            curCount = 60;  //将倒计时的时间改为60秒，方便用户从新发送短信
            code = ""; //清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
        }
        else {
            curCount--;
            $("#send_btn").val("剩余" + curCount + "秒");
        }
    }

    //获取当前时间字符串     Date()   ---->  yyyy/MM/dd HH:mm:ss 格式的字符串
    function getNowDate(date) {
        var sign1 = "/";
        var sign2 = ":";
        var year = date.getFullYear(); // 年
        var month = date.getMonth() + 1; // 月
        var day  = date.getDate(); // 日
        var hour = date.getHours(); // 时
        var minutes = date.getMinutes(); // 分
        var seconds = date.getSeconds(); //秒
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (day >= 0 && day <= 9) {
            day = "0" + day;
        }
        if (hour >= 0 && hour <= 9) {
            hour = "0" + hour;
        }
        if (minutes >= 0 && minutes <= 9) {
            minutes = "0" + minutes;
        }
        if (seconds >= 0 && seconds <= 9) {
            seconds = "0" + seconds;
        }
        var currentdate = year + sign1 + month + sign1 + day + " " + hour + sign2 + minutes + sign2 + seconds ;
        return currentdate;
    }

    //执行用户添加
    function saveWebUser(saveJsonWebUser) {
        $.ajax({
            url:"webusers/saveT",
            type:"post",
            data:saveJsonWebUser,
            success:function (data) {  //请求执行正常函数回调
                if(data=='saveSuccess'){
                    layer.msg('恭喜你，注册成功。。', {icon: 1,time:2000,anim: 4,shade:0.5});
                    //用定时器完成系统的路径跳转
                    setTimeout('window.location = "http://localhost:8083/"',2000);
                }else {
                    layer.msg('很遗憾，注册失败！！', {icon: 2,time:2000,anim: 3,shade:0.5});
                }
            },
            error:function () {  //请求执行异常时的函数回调
                layer.msg('服务器异常', {icon: 3,time:2000,anim: 6,shade:0.5});
            }
        })
    }

    //发送注册的邮箱提示
    function sendEmail(email,msg){
        $.ajax({
            type:"POST",  //请求方式，POST请求
            url:"webusers/sendEmail",   //访问服务器端的路径
            data:{"email":email,"msg":msg},
            success:function (data) {  //请求执行正常函数回调
                console.log(data);
            },
            error:function () {  //请求执行异常时的函数回调
                layer.msg('服务器异常', {icon: 3,time:2000,anim: 6,shade:0.5});
            }
        });
    }

});