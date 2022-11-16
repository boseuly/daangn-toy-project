package daangnmarket.daangntoyproject.home.controller;

import daangnmarket.daangntoyproject.user.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private KakaoService kakaoService;

    @GetMapping(value = "/")
    public String home(){
        return "home";
    }
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        System.out.println("logout 시작");
        kakaoService.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_token");
        session.removeAttribute("userInfo");
        System.out.println("session 삭제 완료");
        return "/home";
    }
}
