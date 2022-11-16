package daangnmarket.daangntoyproject.user.controller;

import daangnmarket.daangntoyproject.user.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

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
    private KakaoService kakaoService;

    @GetMapping("/kakao")
    public String getCI(@RequestParam String code, Model model, HttpSession session) throws IOException {
        System.out.println("code = " + code);
        // 토큰 얻기
        String access_token = kakaoService.getToken(code);
        // 토큰으로 로그인 정보 가져오기
        Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);
        System.out.println("kakaoLoginController(userInfo의 email) : " + userInfo.get("email"));
        System.out.println("kakaoLoginController(userInfo의 nickname) : " + userInfo.get("nickname"));

//        session.setAttribute("code", code);
        session.setAttribute("access_token", access_token); // access_token을 이용해서 언제든 사용자 데이터를 가져올 수 있다.
        session.setAttribute("userInfo", userInfo);
        System.out.println("kakao api 결과 userInfo : " + userInfo);

        //ci는 비즈니스 전환후 검수신청 -> 허락받아야 수집 가능
        return "/home";
    }

}
