package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.requestdto.member.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    final private MemberRepository memberRepository;
    final private PasswordEncoder passwordEncoder;


    @Transactional
    public Long createMember(MemberCreateDto memberCreateDto) {
        String encodedPassword = passwordEncoder.encode(memberCreateDto.getPassword()); // 비밀번호 암호화
        Member member = new Member(memberCreateDto.getUsername(), memberCreateDto.getEmail(), encodedPassword);
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }


//    public boolean isValidMember(String email, String password) {
//        Optional<Member> byEmail = memberRepository.findByEmail(email);
//
//        if (byEmail.isPresent()) { //올바른 비밀번호를 입력했는지 검증
//            Member member = byEmail.get();
//            return passwordEncoder.matches(password, member.getPassword());
//        }
//
//        return false;
//    }
}
