package daangnmarket.daangntoyproject.user.controller;

import daangnmarket.daangntoyproject.user.service.KakaoOAuthService;
import daangnmarket.daangntoyproject.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping(value = "/oauth")
public class KakaoLoginController {
    /**
     * kauth.kakao.com/oauth/authorize?client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}&response_type=code
     * 주소로 이동하면 자동으로 code에 값이 들어가며 oauth/kakao?code= *** 페이지로 이동이 된다.
     *
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
    @Autowired
    private KakaoOAuthService kakaoService;
    @Autowired
    private AccountService userService;

    @GetMapping("/kakao")
    public String getCI(@RequestParam String code) throws IOException {
        userService.kakaoLogin(code);
        return "redirect:/";
    }

}
