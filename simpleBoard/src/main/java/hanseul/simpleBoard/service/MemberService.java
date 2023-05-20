package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.exception.member.DuplicateEmailException;
import hanseul.simpleBoard.exception.member.MemberNotFoundException;
import hanseul.simpleBoard.exception.member.MemberPasswordIncorrectException;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.requestdto.member.MemberCreateDto;
import hanseul.simpleBoard.requestdto.member.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void existsByEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional
    public Long createMember(MemberCreateDto memberCreateDto) { // 회원 생성 (회원가입)
        existsByEmail(memberCreateDto.getEmail()); // 중복 이메일 검사
        String encodedPassword = passwordEncoder.encode(memberCreateDto.getPassword()); // 비밀번호 암호화
        Member member = new Member(memberCreateDto.getName(), memberCreateDto.getEmail(), encodedPassword);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Member updateMember(Long memberId, MemberUpdateDto memberUpdateDto) { // 회원정보 변경
        Member member = findOne(memberId);

        if (!passwordEncoder.matches(memberUpdateDto.getOldPassword(), member.getPassword())) {
            throw new MemberPasswordIncorrectException(); //비밀번호 검사
        }

        String email = memberUpdateDto.getEmail();
        existsByEmail(email);

        String password = passwordEncoder.encode(memberUpdateDto.getNewPassword());
        String name = memberUpdateDto.getName();

        if (email != null || password != null || name != null) {
            member.update(email, password, name);
            memberRepository.save(member);
        }
        return member;
    }

    @Transactional
    public void deleteMember(Long memberId) { // 회원 삭제 (회원탈퇴)
        Member member = findOne(memberId);
        memberRepository.delete(member);
    }
}
