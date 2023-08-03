package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //스프링과 같이 테스트하기 위해
@Transactional //"테스트 내에서(한정)" 테스트 함수 호출을 rollback 시키기 위해
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Test
    public void 회원가입() throws Exception {
        //given 이런게 주어졌을때
        Member member = new Member();
        member.setName("kim");

        //when 이렇게 하면
        Long savedId = memberService.join(member);

        //then 이렇게 되야 한다
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예약() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));//해당 예외 터진게 맞는지 확인

        //then
    }
}