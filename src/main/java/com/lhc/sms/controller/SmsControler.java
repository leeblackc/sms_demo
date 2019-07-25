package com.lhc.sms.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.lhc.sms.SmsDemo;
import com.lhc.sms.model.User;
import com.lhc.sms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sms")
public class SmsControler {
    @Autowired
    @Qualifier("redisTemplate")
    //实例化
    private RedisTemplate<Object, Object> rts;
    @Autowired
    private UserService userService;

    @RequestMapping("/say")
    @ResponseBody
    public String say(){
        return "hello world!";
    }
    /**
     * 发送短信验证码
     * @param username
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCode")
    @ResponseBody
    public String getCode(String username)throws Exception{
        //rts.opsForValue().set("17730223870",123456);
        System.out.println("phone=" + username);
        SmsDemo smsDemo = new SmsDemo();
        smsDemo.setNewcode();
        String code = Integer.toString(smsDemo.getNewcode());
        SendSmsResponse sendSms = smsDemo.sendSms(username, code);//填写你需要测试的手机号码
        //将手机号和验证码存入redis,生存时间为5分钟
        rts.opsForValue().set(username,code,5, TimeUnit.MINUTES);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + sendSms.getCode());
        System.out.println("Message=" + sendSms.getMessage());
        System.out.println("RequestId=" + sendSms.getRequestId());
        System.out.println("BizId=" + sendSms.getBizId());
        return sendSms.getCode();
    }


    /**
     * 检测短信验证码是否相同  登陆
     * @param username
     * @param pcode
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/plogin")
    @ResponseBody
    public Object plogin(String username, String pcode, HttpSession session) throws Exception {
        System.out.println("username=" + username + ";pcode=" + pcode);
        try{
            Object code=rts.opsForValue().get(username);
            if (code.equals(pcode)) {
                User user = userService.findByPhone(username);
                session.setAttribute("user", user);
                return user;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
