package hanseul.simpleBoard.config.auth;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyMemberDetailService implements UserDetailsService {

    //DB에서 가져온 유저 정보를 시큐리티한테 넘겨주기

    private final MemberService memberService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> findOne = memberService.findByEmail(email);
        Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다"));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles("USER")
                .build();
    }


}
