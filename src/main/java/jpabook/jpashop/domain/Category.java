package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item", // 다대다 관계를 일대다, 다대일 관계로 풀어낼 때 사용한다.
            joinColumns = @JoinColumn(name = "category_id"), // 중간 테이블에 있는 category_id
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 중간 테이블에 있는 item_id
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent; // 부모 카테고리 (계층구조)(자기 자신과 연관관계를 맺는다.)

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>(); // 자식 카테고리 (계층구조)(자기 자신과 연관관계를 맺는다.)
}
