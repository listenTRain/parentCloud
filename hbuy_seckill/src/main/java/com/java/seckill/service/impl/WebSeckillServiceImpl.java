package com.java.seckill.service.impl;

import com.java.model.WebSeckillEntity;
import com.java.model.WebUsersEntity;
import com.java.seckill.mapper.WebSeckillMapper;
import com.java.service.RabbitMQService;
import com.java.service.WebSeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:快乐风男
 * time:15:12
 */
@Service
@Transactional(readOnly = false)
public class WebSeckillServiceImpl implements WebSeckillService{

    @Resource
    private WebSeckillMapper webSeckillMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitMQService rabbitMQService;

    //将秒杀数据放入到redis中
    @Scheduled(cron = "0/5 * * * * ?")//从0秒开始每隔5秒执行一次
    @Override
    public Map<String, Object> addSecKillToRedis() throws Exception {
        //新建返回的结果
        Map<String, Object> map = new HashMap<String, Object>();
        //从mysql查询即将秒杀的商品
        List<WebSeckillEntity> webSeckillEntities = webSeckillMapper.selectSecKillByTimes();
        if (webSeckillEntities != null){ //存在符合条件的秒杀商品
            //把存在的商品数据装入到redis中(每件商品均只装入一次)
            for (WebSeckillEntity seckill : webSeckillEntities){
                //创建key
                String key = seckill.getId() + "_" + seckill.getProductid();
                //判断redis中是否存在此秒杀商品数，存在则以前装入了，就不再装入
                if (!redisTemplate.hasKey(key)){ //没有这个key则装入
                    map.put("code",200);
                    map.put("msg","此次已装入秒杀商品"+key);
                    //获取redis操作list的模板
                    ListOperations lop = redisTemplate.opsForList();
                    for (int i = 0; i < seckill.getNum(); i++) { //对于同一件商品，放出多少数量秒杀，就添加多少件该商品
                        lop.leftPush(key,seckill.getId()+","+seckill.getProductid());
                    }
                }else {
                    map.put("code",500);
                    map.put("msg","之前已装入过秒杀商品"+key);
                    System.out.println("之前已装入过秒杀商品"+key);
                }
            }
        }else {
            map.put("code",404);
            map.put("msg","没有符合秒杀的数据");
            System.out.println("没有符合秒杀的数据被放进redis中。。");
        }

        return map;
    }

    //2.将秒杀的商品状态改为开始秒杀（准备0------>开始1）
    //修改秒杀的商品的状态(准备-->开始)   假如   13：59：55 <= 14：00：00 <= 14：00：05
    //系统时间14：00：05  最多服务器有5次机会去修改状态updateUPSecKillStatus
    @Scheduled(cron = "0/2 * * * * ? ") // 间隔2秒执行
    @Override
    public String updateUPSecKillStatus() throws Exception {
       //修改状态
        if (webSeckillMapper.updateUPSecKillStatus() >0){
            System.out.println("秒杀状态修改成功，（准备0------>开始1）。。");
            return "success";
        }else {
            System.out.println("秒杀状态修改失败，（准备0------>开始1）！！");
            return "fail";
        }
    }

    //3.将秒杀商品的状态改为已结束(开始1-->结束2)
    //修改秒杀的商品的状态(开始-->结束)   假如   13：59：55 <= 14：00：00 <= 14：00：05
    //系统时间14：00：05  最多服务器有5次机会去修改状态updateEndSecKillStatus
    @Override
    @Scheduled(cron = "0/2 * * * * ? ") // 间隔2秒执行
    public String updateEndSecKillStatus() throws Exception {
        if (webSeckillMapper.updateEndSecKillStatus() > 0){
            System.out.println("秒杀状态修改成功，（开始1-->结束2）。。");
            return "success";
        }else {
            System.out.println("秒杀状态修改失败，（开始1-->结束2）！！");
            return "fail";
        }
    }

    /**
     *   用户执行秒杀（操作的是redis数据库）,测试
     * @param uid  用户id
     * @param secId  秒杀id
     * @return  操作结果
     */
    @Override
    public Map<String, Object> doSeckill(String token, Long secId) throws Exception {
        //1.新建操作的结果集合
        Map<String, Object> map = new HashMap<String, Object>();
        //用令牌去到redis中找用户数据
        ValueOperations value = redisTemplate.opsForValue();
        WebUsersEntity loginUser = (WebUsersEntity) value.get("sessionId" + token);
        //2.判断用户是否登陆
        if (loginUser != null){
            //根据秒杀id查询该秒杀的数据
            WebSeckillEntity seckill = webSeckillMapper.selectObjectById(secId);
            if ("0".equals(seckill.getStatus())){ //状态是否为开启秒杀模式
                map.put("code","300");
                map.put("msg","抱歉，商品还未开始秒杀！！");
            }
            if ("2".equals(seckill.getStatus())){
                map.put("code","302");
                map.put("msg","抱歉，商品秒杀已结束！！");
            }
            if(seckill.getStatus().equals("1")) {  //开启秒杀模式，可以抢购
                //得到操作list的模板对象
                ListOperations lop = redisTemplate.opsForList();
                //获得商品秒杀的list的key  seckill.getId() + "_" + seckill.getProductid();
                String listkey = secId+"_"+seckill.getProductid();

                //商品已经提前加载进redis的list中，此时根据listkey删除list中的一个元素，删除成功则返回值不为null，
                //删除失败返回值为null，也说明此listkey中已没有元素，被秒杀完
                Object obj = lop.leftPop(listkey);
                if (obj != null){ //该商品还没被抢走，可以秒杀
                    //判断此用户是否重复秒杀
                    //获取操作set的模板
                    SetOperations sop = redisTemplate.opsForSet();
                    //构建set的key值
                    String setkey = "secKill_"+secId+"_"+seckill.getProductid();
                    //构建set元素中的value值(根据用户id编号)
                    String setvalue = secId+","+loginUser.getId()+","+seckill.getProductid();
                    //判断set中是否存在此元素
                    if (!sop.isMember(setkey,setvalue)){ //不存在，直接加
                        //不存在，可以秒杀
                        sop.add(setkey,setvalue);
                        //7.2在秒杀成功的同时我们要将数据装到rabbitMQ中
                        //uid secId productId cost flag
                        rabbitMQService.addRabbitMQToExCFormSeckill(secId,seckill.getProductid(),seckill.getSeckillprice(),loginUser.getId().intValue());
                        map.put("code",200);
                        map.put("msg","恭喜你，秒杀成功！！");
                    }else {
                        //存在，不能重复秒杀
                        lop.leftPush(listkey,obj);   //把商品重新加回商品的list中
                        map.put("code","600");
                        map.put("msg","很抱歉，不能重复秒杀！！");
                    }
                }
            }
        }else { //未登录
            map.put("code","400");
            map.put("msg","没有登录用户不能秒杀！！");
        }
        return map;
    }

    /**
     *   页面加载开始秒杀的商品数据
     * @return  秒杀的商品数据
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findUPSecKill() throws Exception {
        return webSeckillMapper.selectUPSecKill();
    }
}
