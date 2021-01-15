jQuery(function () {

    //点击搜索进行查询操作
    jQuery("#slorBtn").click(function () {
        var slorPra = jQuery("#slorPra").val();
        if (slorPra==''){
            alert("请输入要搜索的数据。。");
        }else {
            //发送ajax请求访问服务器
            loadProductBySlor(slorPra);
        }
    });

    //根据条件加载slor引擎中的商品数据
    function loadProductBySlor(slorPra) {
        jQuery.ajax({
            url:"/solr/loadProductBySlor",
            type:"post",
            data:{"slorPra":slorPra},
            success:function (data) {
                if(data!=''){
                    var productStr = "";
                    jQuery.each(data,function (i, product) {
                        productStr += '<li>';
                        productStr += '<div class="img"><a href="#"><img src="'+product.avatorImg+'" width="210" height="185" /></a></div>';
                        productStr += '<div class="price">';
                        productStr += '<font>￥<span>'+product.price+'</span></font> &nbsp; 26R';
                        productStr += '</div>';
                        productStr += '<div class="name"><a href="'+product.pid+'">'+product.title+'</a></div>';
                        productStr += '<div class="carbg">';
                        productStr += '<a href="#" class="ss">收藏</a>';
                        productStr += '<a href="#" class="j_car">加入购物车</a>';
                        productStr += '</div>';
                        productStr += '</li>';
                    });
                    jQuery("#cate_list").html(productStr);
                }else {
                    alert("无此商品。。");
                }
            },
            error: function () {
                alert("服务器异常！！")
            }
        })
    }

});
