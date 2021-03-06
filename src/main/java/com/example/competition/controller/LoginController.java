package com.example.competition.controller;

import com.example.competition.VO.ResultVO;
import com.example.competition.dao.entity.User;
import com.example.competition.enums.ErrorEnum;
import com.example.competition.enums.UserStatusEnum;
import com.example.competition.exception.CompetitionException;
import com.example.competition.service.impl.AccountServiceImpl;
import com.example.competition.utils.ResultVOUtil;
import com.example.competition.utils.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @作者：刘富国
 * @创建时间：2018/1/19 14:27
 */
@Slf4j
@Controller
public class LoginController {
    @Autowired
    private AccountServiceImpl accountService;
    /**
     * 登录
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultVO<String> login(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "rememberMe", defaultValue = "false") boolean rememberMe,
                          @RequestParam(value = "urlPath") String urlPath) {
        User user = new User();
        user.setUsername(username);
        user = accountService.findOne(user);
        if (user == null||user.getUserStatus()==UserStatusEnum.DELETE.getCode()) {
            log.error("【登录】账号不存在，username={}", username);
            throw new CompetitionException(ErrorEnum.USER_NOT_EXIST);
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(ShiroUtil.getToken(username,password,user.getPasswordSalt(),rememberMe));
        } catch (IncorrectCredentialsException e) {
            return ResultVOUtil.error(ErrorEnum.ACCOUNT_PASSWORD_ERROR);
        }catch (CompetitionException e){
            return ResultVOUtil.error(ErrorEnum.ACCOUNT_PASSWORD_ERROR);//账号不存在
        }
        SecurityUtils.getSubject().getSession().setAttribute("user",user);
        return ResultVOUtil.success(urlPath);
    }



    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:index.html";
    }


}
