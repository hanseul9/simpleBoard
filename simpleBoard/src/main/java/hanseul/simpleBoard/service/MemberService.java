package hanseul.simpleBoard.service;

import hanseul.simpleBoard.domain.Member;
import hanseul.simpleBoard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    final private MemberRepository memberRepository;

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
