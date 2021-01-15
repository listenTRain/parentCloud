package com.java.buycar.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;

import com.java.buycar.mapper.WebProductDetailMapper;
import com.java.buycar.utils.Base64Utils;
import com.java.model.BuyCar;
import com.java.model.Good;

import com.java.model.WebProductDetailEntity;
import com.java.model.WebUsersEntity;
import com.java.service.BuyCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * author:快乐风男
 * time:12:54
 */
@Service
@Transactional(readOnly = false)
public class BuyCarServiceImpl implements BuyCarService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WebProductDetailMapper webProductDetailMapper;

    /**
     *   未登录的情况下实现购物车添加(只有cookie操作)
     * @param goodId  商品id
     * @param num  商品数量
     * @param request  请求对象
     * @param response  响应对象
     * @return  添加的结果
     * @throws Exception
     */
    //cookie的区分
    //1.根据服务器：http://localhost:8089
    //2.根据设置产生cookie的路径：buyCarCookie.setPath("/buyCar")与控制器的访问路径一致;
    //3.根据cookie的name进行区分
    @Override
    public Map<String, Object> addBuyCar(Integer goodId, Integer num, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1.新建返回的添加map集合
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodId",goodId);
        map.put("num",num);
        map.put("code","fail");  //添加失败
        //定义一个空的buyCar购物车的cookie
        Cookie buyCarCookie = null;
        //首先访问并获取页面中存放购物车数据的cookie
        Cookie[] cookies = request.getCookies();
        //在cookies中寻找名为buyCar的cookie是否存在
        if (cookies != null){
            for (Cookie oneCookie : cookies){
                if ("buyCar".equals(oneCookie.getName())){
                    //存在名为buyCar的cookie，则得到购物车cookie
                    buyCarCookie = oneCookie;
                    break;
                }
            }
        }
        //判断存放购物车的cookie是否存在(buyCarCookie是否取到名为buyCar的cookie)
        if (buyCarCookie == null){ //页面没有名为buyCar的cookie，则创建
            System.out.println("页面中没有登录的情况下第1次添加购物车。。。");
            //页面中没有登录的情况下第1次添加购物车
            //得到要添加的商品对象
            Good good = new Good(goodId,num);
            //将要添加的商品加入到购物车对象中
            BuyCar buyCar = new BuyCar();
            List<Good> listGood = new ArrayList<>(); //多个good商品的List集合对象
            listGood.add(good);
            buyCar.setGoods(listGood);
            //双向加密，加密之前要将购物车对象转为字符串（将购物车对象JSON化）
            //因为Base64加密工具加密数据为字符串
            String buyCarStr = JSON.toJSONString(buyCar);
            System.out.println(buyCarStr);  //打印购物车封装类对象中的JSON数据
            //将转后的JSON数据进行加密操作
            String baseBuyCar = Base64Utils.getBASE64(buyCarStr);
            //将购物车放置进入cookie对象
            buyCarCookie = new Cookie("buyCar",baseBuyCar);
            //设置cookie的有效时间32hours
            buyCarCookie.setMaxAge(3600*32);
            //设置cookie的路径要与控制器的访问路径一致
            buyCarCookie.setPath("/buyCar");
            //添加cookie响应回客户端
            response.addCookie(buyCarCookie);
            map.put("code","success");
        }else {//名为buyCard的购物车的cookie存在
            System.out.println("存在buyCar的cookie...");
            //取出购物车buyCarCookie中的value
            String baseBuyCar = buyCarCookie.getValue();
            BuyCar buyCar = null;  //定义购物车对象
            List<Good> goods = null;  //定义商品集合对象
            //判断buyCarCookie中value是否有数据
            if (!StrUtil.isBlank(baseBuyCar) && !StrUtil.isNullOrUndefined(baseBuyCar)){ //hutool工具，判断buyCarCookie中value既不为空也不为空字符串或undefined
                System.out.println("存在buyCar的cookie..且cookie中有value值。。");
                //解密购物车
                String fromBASE64 = Base64Utils.getFromBASE64(baseBuyCar);
                //将baseBuyCar还原为BuyCar对象
                buyCar = JSON.parseObject(fromBASE64, BuyCar.class);
                //判断当前购物车里的商品与即将加入的商品是否重复
                goods = buyCar.getGoods();
                Good good = null;  //定义一个空的新的商品变量
                int i = 0;
                for (;i<goods.size();i++){
                    if(goods.get(i).getGoodId().equals(goodId)){
                        //存在相同的商品，将原有商品对象赋值给要添加的商品变量(此时只需要改变商品数量) A
                        good = goods.get(i);
                        break;
                    }
                }
                if (good != null){//新加入的商品ID与购物车中的商品ID有重复,只是修改原有商品的数量 A
                    int newNum = good.getNum() + num; //商品数量加num
                    //ArrayList集合元素不覆盖，需要将原有的商品对象删除掉. 因为是引用的地址或许-> good.setNum(newNum);?
                   // good.setNum(newNum); 初步试验证明可行，那么可以舍弃用于定位list集合下标的变量i，使用foreach简化代码
                     goods.remove(good);
                    //在List集合中的原位置加上新的商品（修改了数量的商品对象）
                     goods.add(i,new Good(goodId,newNum));
                    System.out.println("存在buyCar的cookie..且cookie中有value值。。且新加入的与之前有重复。。");
                }else { //新加入的商品ID与购物车中的商品ID没有重复
                    //则直接将要添加的商品加入到List集合中
                    goods.add(new Good(goodId,num));
                    System.out.println("存在buyCar的cookie..且cookie中有value值。。且新加入的与之前没有有重复。。");
                }

            }else { //cookie中没有数据
                System.out.println("存在buyCar的cookie..但是cookie中没有value值。。");
                //cookie名为buyCar的value值为空
                goods = Arrays.asList(new Good(goodId, num));
                //新建购物车对象
                buyCar = new BuyCar();
            }
            //将商品集合重新设置到购物车对象中
            buyCar.setGoods(goods);
            //购物车对象进行加密,再将购物车cookie响应给客户端
            String buyCarStr = JSON.toJSONString(buyCar);
            System.out.println(buyCarStr);  //打印购物车封装类对象中的JSON数据
            //加密
            String miwen = Base64Utils.getBASE64(buyCarStr);
            //将加密的换行操作去掉
            miwen = miwen.replaceAll("\r\n","");
            //覆盖cookie
            buyCarCookie = new Cookie("buyCar",miwen); // buyCarCookie.setValue(miwen); ?
            buyCarCookie.setMaxAge(3600*32);
            buyCarCookie.setPath("/buyCar");
            response.addCookie(buyCarCookie);
            map.put("code","success");

        }
        return map;
    }

    /**
     *   已登录的情况下实现购物车添加（只有redis操作）
     * @param goodId  商品id
     * @param num  商品数量
     * @param uid  登陆的用户id
     * @param request  请求对象
     * @param response  响应对象
     * @return  添加的结果
     * @throws Exception
     */
    @Override
    public Map<String, Object> addBuyCarAfterLogin(Integer goodId, Integer num, Integer uid, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.新建返回的添加map集合
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodId",goodId);
        map.put("num",num);
        map.put("uid",uid);
        map.put("code","fail");  //添加失败
        //通过redis缓存模板得到操作value的缓存对象
        ValueOperations vop = redisTemplate.opsForValue();
        //定义购物车对象
        BuyCar buyCar = null;
        //定义要添加的商品对象
        Good good = null;
        int j = 0; //用于定位list集合中的商品对象位置（foreach不需要）
        //从redis中取出商品数据，根据不同的登陆用户取到其用户自己的购物车数据
        String redisBuyCarStr = (String) vop.get("redisBuyCar," + uid);
        //判断redis中的是否存在buyCar数据
        if(!StrUtil.isBlank(redisBuyCarStr) && !StrUtil.isNullOrUndefined(redisBuyCarStr)){
            //存在购物车的数据
            //将购物车JSON数据反序列化为购物车对象（不用进行解密，因为存到redis中的数据不需要加密）
            buyCar = JSON.parseObject(redisBuyCarStr, BuyCar.class);
            //取出购物车中的商品集合数据
            List<Good> redisGoods = buyCar.getGoods();
            //循环判断原有的redis中的购物车里是否存在要添加的商品数据
            for (Good oneGood : redisGoods){
                if (goodId.equals(oneGood.getGoodId())){
                    //表示商品重复，则取出该商品的地址指针用于修改商品数量
                    good = oneGood;
                    break;
                }
            }
            //判断good对象是否取到数据
            if (good == null){
                System.out.println("redis中有数据，没有重复的商品，直接往后面加");
                //没有重复的商品数据，直接在集合后面加商品对象元素
                redisGoods.add(new Good(goodId,num));
            }else {
                System.out.println("redis中有数据，有重复的商品，更改重复的商品的数量再加");
                //存在，改原有的商品数量
                int newNum = good.getNum()+num;
                good.setNum(newNum);
            }
            //将商品集合设置到购物车对象中
            buyCar.setGoods(redisGoods);
            System.out.println(JSON.toJSONString(buyCar));  //测试打印
        }else {
            System.out.println("redis中没有此购物车数据，第一次加购物车数据");
            //redis中不存在购物车数据，直接新建购物车对象进行添加
            buyCar = new BuyCar();
            buyCar.setGoods(Arrays.asList(new Good(goodId,num)));
        }
        //最终将购物车对象数据装入到redis数据库中,统一JSON格式
        vop.set("redisBuyCar," + uid,JSON.toJSONString(buyCar));
        map.put("code","success");
        return map;
    }

    /**
     *   用户登录的时候对购物车数据进行合并（操作cookie和redis）
     * @param uid  登陆的用户id
     * @param request  请求对象
     * @param response  响应对象
     * @return  添加的结果
     * @throws Exception
     */
    @Override
    public Map<String, Object> appendBuyCarByLogin(Integer uid, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.新建返回的添加map集合
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("code", "fail");  //未进行合并
        //得到整个页面中的cookies
        Cookie[] cookies = request.getCookies();
        //定义一个空的buyCar购物车的cookie
        Cookie buyCarCookie = null;
        //判断cookies是否存在cookie
        if (cookies != null) {
            //找到名字为buyCar的cookie是否存在
            for (Cookie oneCookie : cookies) {
                if ("buyCar".equals(oneCookie.getName())) {
                    //存在，则得到购物车cookie
                    buyCarCookie = oneCookie;
                    break;
                }
            }
        }
        //判断buyCarCookie是否取得名为buyCar的cookie对象
        if (buyCarCookie != null) {
            //取出购物车buyCarCookie中的value
            String baseBuyCar = buyCarCookie.getValue();
            //判断buyCarCookie中value是否为空
            if (!StrUtil.isBlank(baseBuyCar) && !StrUtil.isNullOrUndefined(baseBuyCar)) {
                //表示存在值，不为空，解密得到购物车对象
                BuyCar buyCar = JSON.parseObject(Base64Utils.getFromBASE64(baseBuyCar), BuyCar.class);
                //取出buyCar中的商品集合(来自cookie)
                List<Good> goodsCookie = buyCar.getGoods();
                //通过redis缓存模板得到操作value的缓存对象
                ValueOperations vop = redisTemplate.opsForValue();
                //接下来判断redis中是否存在buyCar数据
                String redisBuycarStr = (String) vop.get("redisBuyCar," + uid);
                //判断redis中存在购物车数据
                if (!StrUtil.isBlank(redisBuycarStr) && !StrUtil.isNullOrUndefined(redisBuycarStr)) {
                    System.out.println("cookie中存在数据，且redis中也存在数据，进行匹配相同商品后再完成合并。。");
                    //获取redis中的购物车对象
                    BuyCar redisBuycar = JSON.parseObject(redisBuycarStr, BuyCar.class);
                    //获取 redis中的商品集合对象
                    List<Good> goodsRedis = redisBuycar.getGoods();
                    Good newGood = null;
                    //新建一个空的商品集合，把不同的商品数据全部存放在此集合中
                    List<Good> newList = new ArrayList<Good>();
                    //循环寻找redis中的购物车是否有与cookie中的重复的商品
                    for (Good redisGood : goodsRedis) {
                        //Iterator<Good> iterator = goodsCookie.iterator(); 关于ConcurrentModificationException
                        for (Good cookieGood : goodsCookie) {
                            if (redisGood.getGoodId().equals(cookieGood.getGoodId())) {
                                //在cookie购物车中找到一件与redis相同的商品，则修改redis商品数量，并将cookie中的该商品清除不参与下次循环
                                int newNum = redisGood.getNum() + cookieGood.getNum();
                                redisGood.setNum(newNum);
                                //清除cookie中的该商品
                                goodsCookie.remove(cookieGood);
                                break;
                            }
                        }
                    }
                    System.out.println(JSON.toJSONString(redisBuycar));
                    //cookie还余下的商品集合元素，为不重复的商品，直接添加进redis
                    if (goodsCookie.size() > 0) {
                        goodsRedis.addAll(goodsCookie);
                    }
                    System.out.println(JSON.toJSONString(redisBuycar));
                    //将匹配过的购物车数据JSON化加入到redis中
                    vop.set("redisBuyCar," + uid, JSON.toJSONString(redisBuycar));
                    map.put("code", "success");
                }else {  //redis没有buyCar的数据
                    System.out.println("cookie中存在数据，redis不存在数据，直接将cookie数据加入到redis中，完成合并");
                    //直接将cookie中的购物车加入到redis中，完成合并
                    vop.set("redisBuyCar," + uid, JSON.toJSONString(buyCar));
                    map.put("code", "success");
                }
            }else {
                System.out.println("购物车的cookie存在，但是没有数据，不用合并。。");
            }
            //清空buyCar的cookie，当cookie存在时清空
            Cookie cookie = new Cookie("buyCar", "");
            cookie.setPath("/buyCar");//此处与109行代码保持一致，否则cookie清空会失败
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }else {
            System.out.println("不存在购物车cookie，不用合并。。");
        }
        return map;
    }

    /**
     *   查询购物车数据
     * @param token  查找登陆用户数据的令牌
     * @param request  请求对象
     * @return  购物车数据
     * @throws Exception
     */
    @Override
    public List<Good> findBuyCar(String token, HttpServletRequest request) throws Exception {

        System.out.println("购物车是否取到token："+token);
        //创建返回对象
        List<Good> goods = null;
        if (token == null) { //查询cookie的购物车数据
            //得到整个页面中的cookies
            Cookie[] cookies = request.getCookies();
            //定义一个空的buyCar购物车的cookie
            Cookie buyCarCookie = null;
            //判断cookies是否存在cookie
            if (cookies != null) {
                //找到名字为buyCar的cookie是否存在
                for (Cookie oneCookie : cookies) {
                    if ("buyCar".equals(oneCookie.getName())) {
                        //存在，则得到购物车cookie
                        buyCarCookie = oneCookie;
                        break;
                    }
                }
            }
            if (buyCarCookie != null) {  //buyCarCookie取到了购物车cookie，意味buyCar存在
                //取出value数据
                String baseBuyCar = buyCarCookie.getValue();
                if (!StrUtil.isBlank(baseBuyCar) && !StrUtil.isNullOrUndefined(baseBuyCar)) {
                    //表示存在值，不为空，解密得到购物车对象
                    BuyCar buyCar = JSON.parseObject(Base64Utils.getFromBASE64(baseBuyCar), BuyCar.class);
                    //取出cookie中的商品集合
                    goods = buyCar.getGoods();
                }
            }
        }else {
            //若令牌不为null，到redis中找用户数据
            ValueOperations vop = redisTemplate.opsForValue();
            WebUsersEntity loginUser = (WebUsersEntity) vop.get("sessionId" + token);
            if (loginUser == null){ //取到了缓存的令牌，但是用户数据已注销
                //得到整个页面中的cookies
                Cookie[] cookies = request.getCookies();
                //定义一个空的buyCar购物车的cookie
                Cookie buyCarCookie = null;
                //判断cookies是否存在cookie
                if (cookies != null) {
                    //找到名字为buyCar的cookie是否存在
                    for (Cookie oneCookie : cookies) {
                        if ("buyCar".equals(oneCookie.getName())) {
                            //存在，则得到购物车cookie
                            buyCarCookie = oneCookie;
                            break;
                        }
                    }
                }
                if (buyCarCookie != null) {  //buyCarCookie取到了购物车cookie，意味buyCar存在
                    //取出value数据
                    String baseBuyCar = buyCarCookie.getValue();
                    if (!StrUtil.isBlank(baseBuyCar) && !StrUtil.isNullOrUndefined(baseBuyCar)) {
                        //表示存在值，不为空，解密得到购物车对象
                        BuyCar buyCar = JSON.parseObject(Base64Utils.getFromBASE64(baseBuyCar), BuyCar.class);
                        //取出cookie中的商品集合
                        goods = buyCar.getGoods();
                    }
                }
            }else { //用户已登录
                //判断redis中是否存在buyCar数据
                String redisBuycarStr = (String) vop.get("redisBuyCar," + loginUser.getId());
                if(!StrUtil.isBlank(redisBuycarStr) && !StrUtil.isNullOrUndefined(redisBuycarStr)){
                    //获取redis中的购物车对象
                    BuyCar redisBuycar = JSON.parseObject(redisBuycarStr, BuyCar.class);
                    //得到redis中的商品集合
                    goods = redisBuycar.getGoods();
                }
            }
        }
        if (goods != null){
            //接下来要通过商品集合中的goodId去查询mysql数据库，得到商品详情的其它属性数据
            for(Good good : goods) {
                //获取到商品详情数据
                WebProductDetailEntity productDetail = webProductDetailMapper.queryObjectById(good.getGoodId());
                //存入购物车时只需要good对象的id和num，其余属性为空，此时需要为其他属性赋值
                good.setAvatorimg(productDetail.getAvatorimg());
                good.setPrice(productDetail.getPrice());
                good.setTitle(productDetail.getTitle());
                good.setSubtitle(productDetail.getSubtitle());
            }
        }
        return goods;
    }

    /**
     *   删除已提交的购物车商品数据
     * @param uid  用户id
     * @param proIds  选中的商品id
     * @throws Exception
     */
    @Override
    public void delGoodByProIds(Integer uid, String proIds) throws Exception {
      //  System.out.println("uid aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+uid);
      //  System.out.println("uid aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+proIds);
        //取到redis的操作模板对象
        ValueOperations vop = redisTemplate.opsForValue();
        //判断redis中是否存在buyCar数据
        String redisBuycarStr = (String) vop.get("redisBuyCar," + uid);
        if (!StrUtil.isBlank(redisBuycarStr) && !StrUtil.isNullOrUndefined(redisBuycarStr)){
            //获取redis中的购物车对象
            BuyCar redisBuycar = JSON.parseObject(redisBuycarStr, BuyCar.class);
            //得到redis中BuyCar对象的商品集合
            List<Good> goods = redisBuycar.getGoods();
            //得到前台选中商品的数组
            String[] arrProIds = proIds.split(",");
            //循环对比goods里与arrProIds里相同的id，并删除该good对象(arrProIds的商品id为goods的子集)
            for (String proId : arrProIds){
              //  System.out.println("ddddddddddddddddddddddddddddddddddddddddddddd"+proId);
                for (Good good : goods){
                 //   System.out.println("ddddddddddddddddddddddddddddddddddddddddddddd"+good);
                 //   System.out.println("ddddddddddddddddddddddddddddddddddddddddddddd"+proId.equals(good.getGoodId()));
                    if (proId.equals(good.getGoodId().toString())){
                        //找到用户购物车里被选中提交为订单的商品，并删除
                        goods.remove(good);
                     //   System.out.println("ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
                        break;
                    }
                }
            }
            if (redisBuycar != null){
                //重新整理redis中的商品数据(redisBuycar中还剩下未被提交为订单的商品)
                vop.set("redisBuyCar," + uid,JSON.toJSONString(redisBuycar));
            }
        }
    }

}
