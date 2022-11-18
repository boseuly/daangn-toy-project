package daangnmarket.daangntoyproject.user.service;

import daangnmarket.daangntoyproject.config.WebSecurityConfig;
import daangnmarket.daangntoyproject.user.controller.AccountController;
import daangnmarket.daangntoyproject.user.domain.Role;
import daangnmarket.daangntoyproject.user.domain.User;
import daangnmarket.daangntoyproject.user.model.UserDto;
import daangnmarket.daangntoyproject.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private KakaoOAuthService kakaoService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
//    private AuthenticationManager authenticationManager;

    @Autowired
    public AccountService(KakaoOAuthService kakaoService, UserRepository userRepository
                    , PasswordEncoder passwordEncoder //, AuthenticationManager authenticationManager
                           ){
        this.kakaoService = kakaoService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
    }

    public void kakaoLogin(String code) throws IOException {
        String access_token = kakaoService.getToken(code);
        Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);

        String kakaoId = (String)userInfo.get("id"); // string을 바로 (Long)으로 형변환하려 하면 안 되고, parseLong을 사용해준다.
        String email = (String) userInfo.get("email");
        String nickname = (String) userInfo.get("nickname");
        String image = (String) userInfo.get("profile_image");
        String password = kakaoId + access_token;

        // DB에서 중복 확인하기
        User kakaoUser =  userRepository.findById(kakaoId).orElse(null);   // Optional로 하면 자꾸 에러나서 일단 Optional사용 x
        if(kakaoUser == null){
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자
            kakaoUser = new User(kakaoId, encodedPassword, nickname, email, image, true);
            Role role = new Role(1, "user");

            kakaoUser.getRoles().add(role);
            userRepository.save(kakaoUser);
        }

        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(kakaoId, password);  // Collection<? extends GrantedAuthority> authorities 인수를 넣어줘야 인증이 완료 되는데, 안 넣어줘서 그런 듯
//        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(kakaoUsernamePassword);

    }
    // 일반 회원가입
    public ResponseEntity<Object> signup(UserDto userDto) {
        logger.info("AccountService - signup(userDto={})", userDto);
        ResponseEntity<Object> responseEntity = null;
        // email과 userId 중복 확인해야 한다.
        UserDto data = userRepository.findByUserIdAndEmail(userDto.getUserId(), userDto.getEmail());

        if(data == null){
            // 회원가입 진행
            logger.info("userDto.getUserPassword() = {}", userDto.getUserPassword());
            String passwordEncoding = passwordEncoder.encode(userDto.getUserPassword());    // 패스워드 암호화
            userDto.setUserPassword(passwordEncoding);
            userDto.setEnabled(true);
            User userEntity = new User(userDto);
            Role role = new Role(1, "user");
            userEntity.getRoles().add(role);
            userRepository.save(userEntity);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        }else {
            // 중복 -> return 시키기
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


}
