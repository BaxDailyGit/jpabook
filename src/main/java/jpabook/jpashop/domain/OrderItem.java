package jpabook.jpashop.domain;


import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id") // 외래키를 매핑한다. item과
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id") // 외래키를 매핑한다. order와
    private Order order;

    private int orderPrice; // 주문 가격

    private int count; // 주문 수량

}
