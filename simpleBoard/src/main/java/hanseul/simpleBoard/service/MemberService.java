package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.exception.member.MemberNotFoundException;
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


    public Member findOne(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException());
    }

    @Transactional
    public Long createMember(MemberCreateDto memberCreateDto) { // 회원 생성 (회원가입)
        String encodedPassword = passwordEncoder.encode(memberCreateDto.getPassword()); // 비밀번호 암호화
        Member member = new Member(memberCreateDto.getName(), memberCreateDto.getEmail(), encodedPassword);
        memberRepository.save(member);
        return member.getId();
    }

//    @Transactional
//    public Long createMember(MemberCreateDto memberCreateDto) { // 회원정보 변경
//        String encodedPassword = passwordEncoder.encode(memberCreateDto.getPassword()); // 비밀번호 암호화
//        Member member = new Member(memberCreateDto.getName(), memberCreateDto.getEmail(), encodedPassword);
//        memberRepository.save(member);
//        return member.getId();
//    }

    @Transactional
    public void deleteMember(Long memberId) { // 회원 삭제 (회원탈퇴)
        Member member = findOne(memberId);
        memberRepository.delete(member);
    }




}
