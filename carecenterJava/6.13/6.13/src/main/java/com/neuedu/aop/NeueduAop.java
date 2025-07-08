package com.neuedu.aop;

import com.neuedu.service.MailService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@Component
@Aspect
public class NeueduAop {
    @Resource
    MailService mailService;
    @AfterReturning(value = "execution(public * com.neuedu.service.impl.UserServiceImpl.add(..))")
    public void sendMail(JoinPoint joinPoint) throws Throwable {
        sendUser(joinPoint.getArgs());
    }

    private void sendUser(Object[] args) throws MessagingException {
        // 获取 收件人
        String to = args[7].toString();
        // 定义主题
        String subject = "系统消息";
        // 定义邮件内容
        String text = "<h2>尊敬的" + args[0].toString() + ":<h2>" +
                "<p>HR已经帮您创建好了系统用户,您可以使用了</p>" +
                "<p>登录名是您的手机号: " + args[6].toString() + "</p>" +
                "<p>登录密码是:" + args[8].toString() + "</p>";
        mailService.send(to, subject, text);
    }

}
