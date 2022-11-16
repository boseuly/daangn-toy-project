package daangnmarket.daangntoyproject.user.controller;

import daangnmarket.daangntoyproject.user.model.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value = "/account")
public class AccountController {
    @GetMapping(value = "/login")
    public String login(){

        return "account/login";
    }


    @GetMapping(value = "/signup")
    public String signup(){
        return "account/signup";
    }


}
