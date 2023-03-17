package hanseul.simpleBoard.config.auth.pwencryption;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder{ //비밀번호 암호화 클래스

//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public MyPasswordEncoder() {
//        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public String encode(CharSequence rawPassword) {
//        return bCryptPasswordEncoder.encode(rawPassword);
//    }
//
//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
//    }
}
