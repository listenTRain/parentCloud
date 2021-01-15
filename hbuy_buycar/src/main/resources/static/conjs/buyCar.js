jQuery(function () {

    var InterValObjLogin;

    var InterValObj;

    //取到登陆模块的令牌
    getToken();

    //js轮询
    InterValObjLogin = window.setInterval(getToken, 10000);

    loadBuyCar();

    //js轮询加载购物车
    InterValObj = window.setInterval(loadBuyCar, 60*20*1000);

    var allPrice = 0;  //总的价格

    var proIds = '';  //选中的商品id

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
                    jQuery("#token").val(data);
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
    function getRedisLoginUser() {
        var token = jQuery("#token").val();
        jQuery.ajax({
            type:"POST",
            url:"http://localhost:8091/webusers/getRedisLoginUser",
            data:{"token":token},
            success:function (data) {
                console.log(data);
                if(data!=""){
                    jQuery("#loginHeader").replaceWith('<span class="fl" id="loginHeader">你好，欢迎'+data.uname+'<img src="'+data.userheader+'" width="45px;" height="45px;"/>&nbsp; &nbsp; </span>');
                    //关掉js轮询
                    window.clearInterval(InterValObjLogin);
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
                }
            },
            error:function () {
                alert("服务器异常！！")
            }
        });
    })

    //加载购物车数据
    function loadBuyCar(){
        var token = jQuery("#token").val();
        console.log(token);
        console.log("执行了加载购物车。。");
        jQuery.ajax({
            type:"POST",
            url:"/buyCar/loadBuyCar",
            data:{"token":token},
            success:function (data) {
                console.log(data);
                if(data.length!=0){
                    htmlRightCar(data);
                    htmlCarTable(data);
                }
            },
            error:function () {
                alert('服务器异常！！');
            }
        });
    }

    //填充右侧购物车数据
    function htmlRightCar(data) {
        jQuery("#goodsNum").text(data.length);
        var zprice = 0.0;
        var buyCarUlStr = '';
        jQuery.each(data,function (i,good) {
            zprice += good.price*good.num;
            buyCarUlStr += '<li>';
            buyCarUlStr += '<div class="img"><a href="#"><img src="'+good.avatorimg+'" width="58" height="58" /></a></div>';
            buyCarUlStr += '<div class="name"><a href="#">'+good.title+'&nbsp;&nbsp;'+good.subtitle+'</a></div>';
            buyCarUlStr += '<div class="price"><font color="#ff4e00">￥'+good.price+'</font> X'+good.num+'</div>';
            buyCarUlStr += '</li>';
        })
        console.log(buyCarUlStr);
        jQuery("#showBuyCarUl").html(buyCarUlStr);
        jQuery("#zprice").text(zprice);
    }

    //填充表格购物车数据
    function htmlCarTable(data) {
        var trBuyCarStr = '';
        jQuery.each(data,function (i,buyCar) {
            trBuyCarStr += '<tr>\n' +
                '              <td>\n' +
                '                  <p style="float: left"><input type="checkbox" name="chk" value="'+buyCar.goodId+'" price="'+buyCar.price*buyCar.num+'"/></p><div class="c_s_img" style="margin-left: 120px;"><img src="'+buyCar.avatorimg+'" width="73" height="73" /></div>\n' +
                '                  '+buyCar.title+'\n' +
                '              </td>\n' +
                '              <td align="center">'+buyCar.subtitle+'</td>\n' +
                '              <td align="center" style="color:#ff4e00;">￥'+buyCar.price+'</td>\n' +
                '              <td align="center">\n' +
                '                  <div class="c_num">\n' +
                '                      <input type="button" value="" onclick="jianUpdate1(jq(this));" class="car_btn_1" />\n' +
                '                      <input type="text" value="'+buyCar.num+'" name="" class="car_ipt" />\n' +
                '                      <input type="button" value="" onclick="addUpdate1(jq(this));" class="car_btn_2" />\n' +
                '                  </div>\n' +
                '              </td>\n' +
                '              <td align="center">'+buyCar.price*buyCar.num+'</td>\n' +
                '              <td align="center"><a onclick="ShowDiv(\'MyDiv\',\'fade\')">删除</a>&nbsp; &nbsp;<a href="#">加入收藏</a></td>\n' +
                '          </tr>';
        });
        jQuery("#buyCarTbody").html(trBuyCarStr);
    }

    //全选或者反选
    jQuery("#chkAll").click(function () {
        //1.找到所有的复选框
        var arrchecks = document.getElementsByName("chk");
        //2.获取全选/全不选的标签
        var chkAll = document.getElementById("chkAll");
        //3.将所有复选框状态改为全选/全不选的标签的状态一致
        for(var i=0;i<arrchecks.length;i++){
            arrchecks[i].checked = chkAll.checked;
            var price = arrchecks[i].attributes["price"].nodeValue;
            allPrice += parseFloat(price);
            proIds += arrchecks[i].value + ",";
        }
        //当反选时，proIds为""
        if(!jQuery('#chkAll').is(':checked')){//判断是否被选中
            proIds = '';
            allPrice = 0;
        }
        proIds = proIds.substring(0,proIds.length-1);
        jQuery("#allPrice").html(allPrice);
    });

    //单个的选
    jQuery("#buyCarTbody").on("click",'input:checkbox',function () {
        var proId = jQuery(this).val();
        var price = parseFloat(jQuery(this).attr("price"));
        if(jQuery(this).is(':checked')){
            allPrice += price;
            proIds += ","+proId;
        }else {
            allPrice -= price;
        }
        console.log(proIds);
        jQuery("#allPrice").html(allPrice);
    });

    //提交订单
    jQuery("#subOrders").click(function () {
        var token = jQuery("#token").val();
        if(allPrice==0){
            alert("还未选中商品");
        }else {
            //进行添加订单
            addToMQ(token,proIds,allPrice);
        }
        return false;
    });

    function addToMQ(token,proIds,allPrice) {
        jQuery.ajax({
            type: 'post',
            url: '/buyCar/addRabbitMQToExCFormBuyCar',
            data: {"token": token,"proIds":proIds,"zPrice":allPrice},
            success: function (data) {
                alert(data.msg);
                if(data.code == 0){
                    //把redis中的数据删除
                   delGoodByProIds(token,proIds);
                }
            },
            error: function () {

            }
        });
    }

    //删除提交后的购物车数据
    function delGoodByProIds(token,proIds) {
        jQuery.ajax({
            type: 'post',
            url: '/buyCar/delGoodByProIds',
            data: {"token": token,"proIds":proIds},
            success: function (data) {
              //  alert(data.msg);
                if(data.code == 0){
                    loadBuyCar();
                    jQuery("#allPrice").text(0);
                }
            },
            error: function () {

            }
        });
    }


});