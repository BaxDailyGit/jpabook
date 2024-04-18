package jpabook.jpashop.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 값 타입은 @Embeddable로 선언한다. 값 타입은 식별자가 없고 생명주기를 엔티티에 의존한다. @Embeddable, @Embedded 모두 사용할 수 있다.
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
