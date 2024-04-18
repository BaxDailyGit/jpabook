package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Spring Bean으로 등록
// 마찬가지로 @component 스캔의 대상이 돼서 스프링 빈으로 등록된다.
public class MemberService {

    @Autowired // injection이 된다. 일단 필드 주입을 사용했는데, 생성자 주입을 권장한다.
    private MemberRepository memberRepository;
    // injection의 방법은 3가지가 있다.
    // 1. 필드 주입
    // 2. setter 주입
    // 3. 생성자 주입
    // 생성자 주입을 권장한다.

    // 핵심 기능: 회원 가입, 회원 전체 조회
    /*
    * 회원가입
    * */
    public Long join(Member member) {
        //memberRepository.save(member); // 이렇게만 해도 된다.

        // 하지만 중복 회원 검증 로직을 구현해본다고 가정
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member); // 중복 회원 검증에 문제가 없으면 여기서 정상적으로 save
        return member.getId(); // 저장한 회원의 id를 반환
        // 영속성 컨텍스트에 저장이 될때 키와 값으로 저장이 된다.
        // 이때 키가 id인데 @GeneratedValue를 세팅을 하면
        // 데이터베이스마다 다른데 (어떤 db는 시퀀스를 넣기도 하고, 어떤 db는 테이블을 가짜로 만들어서 그 테이블로부터 키를 따서 넣기도 한다.)
        // 어쨋든 id값이 항상 생성이 되는것이 보장이 된다.
        // 그래서 em.persist할때 항상 값이 박혀 들어가있다.

        // 왜냐하면 영속성 컨텍스트에 값을 넣어야 한다.
        // 그때 키,값 구조에서 키에 pk값을 그대로 넣는다.
        // 그러면서 동시에 id값을 채워준다.
        // 즉, 아직 DB에 저장이 안된 시점에서 id값을 가져올 수 있는 이유는 영속성 컨텍스트에 저장이 되어있기 때문이다.
        // 그래서 member.getId();로 꺼내면 항상 값이 있다는것을 보장이 된다.
    }
    private void validateDuplicateMember(Member member) { // 중복회원이면 안에서 예외를 터트린다.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    // 회원 전체 조회
    




}
