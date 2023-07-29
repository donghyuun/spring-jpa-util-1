package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //스프링 빈으로 등록(@Component를 포함하므로)
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;//스프링이 자동으로 생성해서 주입해줌(생성자에서 autowired)

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);//단건 조회, 두번째는 pk(primary key)
    }

    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();//JPQL: from 의 대상이 Entity 이다. 즉 Entity 객체에 대한 쿼리를 날림(SQL은 테이블을 대상으로 쿼리를 날림)
        return result;
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)//where로 파라미터를 바인딩함, Member.class 는 조회 타입
                .setParameter("name", name)//name 이라는 이름의 파라미터에 name 변수를 설정해줌
                .getResultList();
    }
}
