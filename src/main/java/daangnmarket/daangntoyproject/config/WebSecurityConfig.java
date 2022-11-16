package daangnmarket.daangntoyproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource; // application.properties에 기재된 데이터 정보를 사용할 수 있도록 Autowired 한다.

    public WebSecurityConfig(DataSource dataSource) { // 필드 주입은 권장 하지 않는다. 생성자 주입을 권장
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // 테스트를 위해서 .csrf().disabled()를 했지만 실제로는 이렇게 하면 보안 취약해짐...
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/", "/account/signup","/css/**", "/api/**").permitAll() // 해당 페이지는 모든 사람들이 접속할 수 있는 권한을 부여한다.
                        .anyRequest().authenticated()   // 만약 그냥 "/"만 해주면 css 적용이 안 됨. 따라서 "/css/**"를 해줌으로써 css폴더 안에 있는 하위 파일들을 다 가져오도록 한다.
                )
                .formLogin((form) -> form
                        .loginPage("/account/login")    // 위에서 설정할 페이지("/", "/css/**")를 제외하고 다른 페이지로 이동하려고 하면 login페이지로 이동한다.
                        .usernameParameter("user_id")   //
                        .passwordParameter("user_password")
                        .permitAll()            // loginPage()에 설정한 페이지로 바로 이동된다.
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    // jdbcauthentication 방법 (db로 인증하는 방법)
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) // autowired를 통해서 autenticationManagerBuilder를 생성한다.
            throws Exception {      // AuthenticationManagerBuilder 인스턴스를 가지고 스프링 내부에서 인증 처리를 한다.
        auth.jdbcAuthentication()
                .dataSource(dataSource) // datasource에 있는 정보를 사용해서 인증 처리를 한다.
                .passwordEncoder(passwordEncoder()) // 아래 있는 PasswordEncoder를 여기에 넣어주고, 비밀번호 암호화를 자동으로 해준다.
                .usersByUsernameQuery("select user_id,user_password " // 인증 처리
                        + "from tb_users "
                        + "where user_id = ?")
                .authoritiesByUsernameQuery("select u.user_id,r.role_name "   // 권한 처리
                        + "from tb_user_role ur inner join tb_users u on ur.user_id = u.user_id "
                        + "inner join tb_role r on ur.role_id = r.role_id "
                        + "where u.user_name = ?");
    }



    // 안전하게 암호화할 수 있도록 하는 설정
    @Bean
    public static PasswordEncoder passwordEncoder() {  // 처음에 public 으로만 되어 있었는데 BeanCurrentlyInCreationException 오류가 뜸
        return new BCryptPasswordEncoder();            // public static으로 바꿔줘야 오류 해결 됨
    }
}
