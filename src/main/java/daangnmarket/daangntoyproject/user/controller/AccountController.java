package daangnmarket.daangntoyproject.user.controller;

import daangnmarket.daangntoyproject.user.model.UserDto;
import daangnmarket.daangntoyproject.user.service.AccountService;
import daangnmarket.daangntoyproject.user.service.EmailAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;
    @Autowired
    private EmailAuthService emailAuthService;

//    public AccountController(AccountService accountService){
//        this.accountService = accountService;
//    }
    @GetMapping(value = "/login")
    public String login(){
        return "account/login";
    }


    @GetMapping(value = "/signup")
    public String signup(){
        return "account/signup";
    }

    // 이메일 인증
    @GetMapping(value = "/email")
    @ResponseBody
    public String checkEmail(String email) {
        System.out.println("받은 이메일 : " + email);

        String data = emailAuthService.joinEmail(email);
        return data;
    }

    // 회원가입
    @PostMapping(value = "/signup")
    @ResponseBody
    public ResponseEntity<Object> save(@RequestBody(required = true) UserDto userDto) {

//        // 회원가입을 시키기 위해서는 받은 정보를 저장해야 한다.
//        // DB에 기본정보 insert
//        EmailAuthService.signUp(userDto);
//        //임의의 authKey 생성 & 이메일 발송
//        String authKey = emailAuthService.sendAuthMail(userDto.getEmail());
//        userDto.setAuthKey(authKey);
//
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("email", userDto.getEmail());
//        map.put("authKey", userDto.getAuthKey());
//        System.out.println("저장된 사용자 이메일"+map);
//
//        //DB에 authKey 업데이트
//        EmailAuthService.updateAuthKey(map);
//
//        // 중복 확인 + 데이터 저장
        ResponseEntity<Object> data = accountService.signup(userDto);
        return data;
    }


}
