package daangnmarket.daangntoyproject.user.controller;

import daangnmarket.daangntoyproject.user.model.UserDto;
import daangnmarket.daangntoyproject.user.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(value = "/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }
    @GetMapping(value = "/login")
    public String login(){
        return "account/login";
    }


    @GetMapping(value = "/signup")
    public String signup(){
        return "account/signup";
    }

    @PostMapping(value = "/signup", produces="application/json; charset=utf-8")
    @ResponseBody
    public ResponseEntity<Object> save(@RequestBody(required = true) UserDto userDto){
        // 회원가입을 시키기 위해서는 받은 정보를 저장해야 한다.
        ResponseEntity<Object> data = accountService.signup(userDto);
        return data;
    }


}
