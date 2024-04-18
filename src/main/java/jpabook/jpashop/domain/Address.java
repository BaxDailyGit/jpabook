package jpabook.jpashop.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 값 타입은 @Embeddable로 선언한다. 값 타입은 식별자가 없고 생명주기를 엔티티에 의존한다. @Embeddable, @Embedded 모두 사용할 수 있다.
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() { // 기본생성자. protected로 설정해서 외부에서 사용하지 못하도록 제약한다.
        // JPA 스펙상 만들어두는 것이 좋다. JPA가 리플렉션을 사용하기 때문에 기본 생성자가 필요하다.
    }   // JPA 스펙상 엔티티나 임베디드 타입은 기본 생성자를 public 또는 protected로 설정해야 한다.
        // JPA가 이런 제약을 두는 이유는 JPA 구현 라이브러리가 객체를 생성할 때 리플렉션 같은 기술을 사용할 수 있도록 지원해야 하기 때문이다.


    // Setter를 사용하지 않아서 값을 변경할수 없다.
    // 생성자로만 값을 초기화할 수 있도록 제약한다.
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
