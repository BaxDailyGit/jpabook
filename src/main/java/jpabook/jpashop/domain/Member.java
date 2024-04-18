package jpabook.jpashop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 값 타입을 포함했다. @Embeddable, @Embedded 모두 사용할 수 있다.
    private Address address;

    @OneToMany(mappedBy = "member") // 양방향 매핑. 읽기 전용이다. 주인이 아닌쪽은 mappedBy를 사용한다.
    private List<Order> orders = new ArrayList<>();

}
