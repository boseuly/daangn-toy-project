package daangnmarket.daangntoyproject.home.controller;

import daangnmarket.daangntoyproject.user.domain.User;
import daangnmarket.daangntoyproject.user.model.UserDto;
import daangnmarket.daangntoyproject.user.repository.UserRepository;
import daangnmarket.daangntoyproject.user.service.KakaoOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private KakaoOAuthService kakaoService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/")
    public String home(Principal principal, HttpSession session){
        UserDto login = null;
        if(principal != null){
            login = new UserDto(userRepository.findById(principal.getName()).orElse(null));
        }
        session.setAttribute("login", login);
        return "/home";
    }

}
