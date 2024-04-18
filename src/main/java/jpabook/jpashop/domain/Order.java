package jpabook.jpashop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블 이름을 orders로 매핑한다. 안하면 클래스 이름인 order를 테이블 이름으로 매핑하는데 예약어이기 때문에 orders로 하는것을 추천
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id") // order_id로 매핑한다. DBA분들이 이렇게 이름 짓는것을 선호한다. 엔티티는 타입이 있으므로 id 필드만으로 쉽게 구분할 수 있다. 테이블은 타입이 없으므로 구분이 어렵다.
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") // 외래키를 매핑한다. 이렇게 하면 member_id가 외래키가 된다.
    private Member member;

    @OneToMany(mappedBy = "order") // 양방향 매핑. 읽기 전용이다. 주인이 아닌쪽은 mappedBy를 사용한다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne // 양방향 연관관계에서 1대1관계는 외래키를 어디에 두어도 상관없다. 하지만 주로 access가 많은 곳에 둔다.
    @JoinColumn(name = "delivery_id") // 외래키를 매핑한다. 이렇게 하면 delivery_id가 외래키가 된다.
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]
}
