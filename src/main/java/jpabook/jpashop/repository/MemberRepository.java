package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 자동으로 스프링 빈으로 등록된다. 까보면 @Component가 있으며 @Component스캔의 대상이 돼서
// 스프링 부트 같은경우에 기본적으로 동작 방식 자체가
// JpashopApplication클래스의 @SpringBootApplication이 있는 패키지와 그 하위 패키지들을 @Component스캔한다.
// 그래서 @Repository를 붙이고 @SpringBootApplication이 있는 패키지의 하위 패키지안에 있으므로
// 스프링 빈으로 자동 등록된다.
public class MemberRepository {

    @PersistenceContext // 엔티티 매니저를 주입해준다.
    // 엔티티 매니저는 엔티티를 저장, 수정, 삭제, 조회하는 등 여러가지 기능을 제공한다.
    private EntityManager em; // 엔티티 매니저를 사용해서 JPA를 사용한다.

    public void save(Member member) {
        em.persist(member); // 영속성 컨텍스트에 member 엔티티를 넣는다.(저장한다.)
        // 그러면 트랜젝션이 커밋되는 시점에 DB에 반영된다.(DB에 insert 쿼리가 날라간다.)
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 단건 조회
        // jpa의 find메서드를 사용한다.
        // 첫번째 파라미터는 조회할 엔티티 타입이고 (테이블이 아님)(jpql의 특징)
        // 두번째 파라미터는 식별자(PK)이다.
        // 조회할 엔티티 타입과 식별자를 넣어주면 해당 엔티티를 조회한다.
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // JPQL을 사용한다.
            .getResultList(); // 결과를 반환한다.
    }
    // sql은 테이블을 대상으로 쿼리를 날리는 것이고
    // jpql은 엔티티 객체를 대상으로 쿼리를 날리는 것이다.

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class) // JPQL을 사용한다.
            .setParameter("name", name) // 파라미터를 바인딩한다.
            .getResultList(); // 결과를 반환한다.
    }
}
