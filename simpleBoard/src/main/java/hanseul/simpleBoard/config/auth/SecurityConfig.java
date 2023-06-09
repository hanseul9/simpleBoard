package hanseul.simpleBoard.config.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

//@RequiredArgsConstructor
//@EnableWebSecurity // Spring Security 설정 활성화
@Configuration
@EnableWebSecurity // Spring Security 설정 활성화
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) //@PreAuthorize @PostAuthorize 활성화
public class SecurityConfig{

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final GoogleOauth2UserService googleOauth2UserService;

    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    protected void configure(HttpSecurity http) throws Exception { //세션관리
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/login")
                .maximumSessions(1);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable().cors().disable()  //csrf와 cors disable
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/status", "/", "/home", "/signup", "/api/members", "/profile").permitAll()
                        //로그인 안 해도 위 url들은 접근 가능
                        .anyRequest().authenticated() // 어떠한 요청이라도 인증이 필요
                )
                .formLogin((form) -> form // form 방식 로그인 사용
                        .loginPage("/login")  //로그인 페이지
                        .loginProcessingUrl("/login-process") //submit을 받을 url - 시큐리티가 처리해줌
                        .usernameParameter("email") //submit할 아이디(이메일)
                        .passwordParameter("password") //submit할 비밀번호
                        .defaultSuccessUrl("/posts", true) //성공시
                        .failureUrl("/login") //로그인 실패시
                        .failureHandler(authenticationFailureHandler)
                        .permitAll()  // 로그인 페이지 이동이 막히면 안되므로 관련된애들 모두 허용
                )

                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(googleOauth2UserService)
                        )
                        .defaultSuccessUrl("/posts", true) //성공시
                        .permitAll()
                )



                .logout(withDefaults())  // 로그아웃은 기본설정으로 (/logout으로 인증해제)


                //.httpBasic()//postman 사용시 필요
                ;


        http
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true);

        return http.build();
    }

}
