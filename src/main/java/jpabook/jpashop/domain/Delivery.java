package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;


    @OneToOne(mappedBy = "delivery") // 양방향 매핑. 읽기 전용이다. 주인이 아닌쪽은 mappedBy를 사용한다.
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // Ordinal은 숫자로 1,2,3... 들어가기 때문에 사용하면 안된다. STRING으로 사용해야 순서가 바뀌어도 괜찮다.
    private DeliveryStatus status; // READY, COMP
}
