package daangnmarket.daangntoyproject.home.controller;

import daangnmarket.daangntoyproject.user.model.UserDto;
import daangnmarket.daangntoyproject.user.service.KakaoOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private KakaoOAuthService kakaoService;

    @GetMapping(value = "/")
    public String home(){
        return "/home";
    }

}
