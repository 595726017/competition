package com.example.competition.controller;

import com.example.competition.dao.entity.Account;
import com.example.competition.enums.ErrorEnum;
import com.example.competition.exception.CompetitionException;
import com.example.competition.service.AccountService;
import com.example.competition.service.impl.AccountServiceImpl;
import com.example.competition.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.CompletionException;

/**
 * @作者：刘富国
 * @创建时间：2018/1/19 14:27
 */
@Slf4j
@RestController
@RequestMapping("account")
public class LoginController {
    @Autowired
    private AccountServiceImpl accountService;

    @PostMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
        Account account=new Account();
        account.setAccountLoginName(request.getParameter("loginName"));
        account.setAccountPassword(Md5Util.toMd5(request.getParameter("password")));
        Account result=accountService.verifyAccount(account);
        if(result==null){
            log.error("【登录】账号密码错误，loginName={}",request.getParameter("loginName"));
            throw new CompetitionException(ErrorEnum.ACCOUNT_PASSWORD_ERROR);
        }
        request.getSession().setAttribute(result.getAccountId().toString(),result);
        return new ModelAndView("index","account",result);
    }

}