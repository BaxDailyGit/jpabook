package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberReporsitory {

    @PersistenceContext // JPA의 EntityManager를 주입받을 수 있다.
    private EntityManager em; // JPA의 EntityManager를 사용할 수 있다.


    public Long save(Member member) { // 저장
        em.persist(member); // 영속성 컨텍스트에 저장
        return member.getId(); // 저장된 id 반환 (커맨드와 쿼리를 분리)
    }

    public Member find(Long id) { // 조회
        return em.find(Member.class, id);
    }
}
