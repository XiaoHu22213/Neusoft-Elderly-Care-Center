package com.neuedu.controller;


import com.neuedu.service.CodeService;
import com.neuedu.service.MailService;
import com.neuedu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CodeController {

    @Autowired
    private MailService mailService;

    @Autowired
    private CodeService codeService;

    // 发送验证码接口
    @GetMapping("/sendcode")
    ResultVo<Boolean> sendCode(@RequestParam String username) {
        if (username == null || username.isEmpty()) {
            return ResultVo.failed(false,"用户名不能为空");
        }

        // 校验邮箱是否有效
        if (isEmail(username)) {
            // 生成6位随机验证码
            String code = generateCode();
            // 调用邮件服务发送验证码
            try {
                mailService.send(username, "您的验证码", "验证码为: " + code);
                // 保存验证码到数据库或缓存(伪代码)
                codeService.saveCode(username, code); // 保存验证码到Redis


                // saveCodeToDatabase(username, code);
                return ResultVo.success(true,"验证码已发送到邮箱");
            } catch (Exception e) {
                return ResultVo.failed(false,"验证码发送失败");
            }
        } else {
              return ResultVo.failed(false,"无效的邮箱格式");
        }
    }

    // 校验是否为邮箱
    private boolean isEmail(String username) {
        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        return username.matches(emailRegex);
    }

    // 生成6位随机验证码
    private String generateCode() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }

    @PostMapping("/verifycode")
    public ResultVo<Boolean> verifyCode(@RequestParam String username, @RequestParam String code) {
        boolean isValid = codeService.verifyCode(username, code);
        if (isValid) {
            return ResultVo.success(true);
        } else {
            return ResultVo.failed(false, "验证码不正确或已过期");
        }
    }
}