package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.exception.member.DuplicateEmailException;
import hanseul.simpleBoard.exception.member.MemberNotFoundException;
import hanseul.simpleBoard.repository.MemberRepository;
import hanseul.simpleBoard.requestdto.member.MemberCreateDto;
import hanseul.simpleBoard.requestdto.member.MemberUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void testBefore() {

        MemberCreateDto memberCreateDto
                = new MemberCreateDto("Test@naver.com", "password", "nickName");

        memberService.createMember(memberCreateDto);

    }
    @AfterEach
    public void testAfter() {
        memberRepository.deleteAll();
    }

    @Test
    public void 회원_생성() {

        //given
        MemberCreateDto memberCreateDto
                = new MemberCreateDto("test@naver.com", "password", "nickName");

        //when
        Long createdMemberId = memberService.createMember(memberCreateDto);

        //then
        assertNotNull(createdMemberId);
    }

    @Test
    @Transactional
    public void 중복_회원_예외() throws Exception {
        //given
        MemberCreateDto memberCreateDto
                = new MemberCreateDto("test@naver.com", "password", "nickName");

        //when
        memberService.createMember(memberCreateDto);

        //then
        assertThrows(DuplicateEmailException.class, () -> {
            memberService.createMember(memberCreateDto); //예외가 발생해야 함
        });
    }

    @Test
    public void 회원_업데이트() {
        // Given
        MemberCreateDto memberCreateDto
                = new MemberCreateDto("test@naver.com", "password", "nickName");
        Long memberId = memberService.createMember(memberCreateDto);

        MemberUpdateDto memberUpdateDto
                = new MemberUpdateDto("newTest@naver.com", "newPassword", "newNickName");

        // When
        Long updatedMemberId = memberService.updateMember(memberId, memberUpdateDto);
        Member member = memberService.findOne(updatedMemberId);

        // Then
        assertNotNull(updatedMemberId);
        assertEquals(memberUpdateDto.getName(), member.getName());
        assertEquals(memberUpdateDto.getEmail(), member.getEmail());
        assertEquals(memberUpdateDto.getPassword(), member.getPassword());
    }

    @Test
    public void 회원_삭제() {
        // Given
        MemberCreateDto memberCreateDto
                = new MemberCreateDto("test@naver.com", "password", "nickName");
        Long memberId = memberService.createMember(memberCreateDto);

        // When
        memberService.deleteMember(memberId);

        // Then
        assertThrows(MemberNotFoundException.class, () -> {
            memberService.findOne(memberId);
        });
    }

}
