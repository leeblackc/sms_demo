package com.lhc.sms;

import com.lhc.sms.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsDemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testString(){
        redisTemplate.opsForValue().set("13609745545","4759",5, TimeUnit.MINUTES);
    }
    @Test
    public void testList(){
        //redisTemplate.opsForValue().set("13609745545","4759",5, TimeUnit.MINUTES);
        for (int i = 1; i <= 10 ; i++) {
            redisTemplate.opsForList().rightPush("users",new User(i+10+"",i+10+""));
            //redisTemplate.expire("users",5,TimeUnit.MINUTES);
        }
    }
    @Test
    public void testHash(){
        for (int i = 1; i <= 10 ; i++) {
            //redisTemplate.opsForHash().put("hash","users"+i,new User(i+"",i+""));
            //redisTemplate.opsForList().rightPush("users",new User(i+10+"",i+10+""));
            //redisTemplate.expire("users",5,TimeUnit.MINUTES);
            System.out.println(redisTemplate.opsForHash().get("hash","users"+i));
        }
    }

}
