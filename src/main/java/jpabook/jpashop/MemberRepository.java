package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext //스프링부트가 자동으로 Entity Manager 생성해서 주입해줌 => db랑 연동하는 등의 기능 다 가지고 있는듯?
    private EntityManager em;

    //회원 저장
    public Long save(Member member) {
        em.persist(member);
        return member.getId();//저장한 회원의 id 반환
    }

    //회원 조회
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
