# jpabook
김영한 스프링부트와 jpa 활용 따라하기


## 다시 보기

지금껏 토이 플젝 하면서 설계가 이게 맞는지 싶고  
개발도 이렇게 하는게 맞나 싶은 불안감이 항상 존재.  
이 강의에서는 어떤 상황에서 어떤 행동을 하는지 나와있다.  
반복적으로 다시 보고 플젝하고 다시 보고 플젝하자.  


# 배운 내용 정리


## 엔티티 설계시 주의점 및 엔티티 클래스 개발 팁

```java
실무에서는 가급적 getter는 열어두고 setter는 꼭 필요한 경우에만 사용
setter가 모두 열려있으면 변경포인트가 너무 많아져서 유지보수가 어려워진다.
```

<hr>

```java
양방향 연관관계: 두 테이블중 하나를 연관관계의 주인으로 정해야한다.
(주로 외래키(@JoinColumn을 붙인)를 가진 엔티티를 연관관계 주인으로 한다.)
연관관계 주인이 아닌 엔티티의 필드에 (mappedBy = "member")를 한다. 즉 읽기 전용
```

<hr>

```java
양방향 연관관계에서 1대1관계는 외래키를 어디에 두어도 상관없다.
하지만 주로 access를 많아하는 쪽에서 외래키를 둔다.
```

<hr>

```java
@Table(name = "orders") // 테이블 이름을 orders로 매핑한다.
안하면 클래스 이름인 order를 테이블 이름으로 매핑하는데 예약어이기 때문에 orders로 하는것을 추천
```

<hr>

```java
@Column(name = "order_id") // order_id로 매핑한다. DBA분들이 이렇게 이름 짓는것을 선호한다.
//엔티티는 타입이 있으므로 id 필드만으로 쉽게 구분할 수 있다. 테이블은 타입이 없으므로 구분이 어렵다.
```

<hr>

```java
protected Address() { // 기본생성자. protected로 설정해서 외부에서 사용하지 못하도록 제약한다.
// JPA 스펙상 만들어두는 것이 좋다. JPA가 리플렉션을 사용하기 때문에 기본 생성자가 필요하다.
// JPA 스펙상 엔티티나 임베디드 타입은 기본 생성자를 public 또는 protected로 설정해야 한다.
// JPA가 이런 제약을 두는 이유는 JPA 구현 라이브러리가 객체를 생성할 때 리플렉션 같은 기술을 사용할 수 있도록 지원해야 하기 때문이다.
}

// Setter를 사용하지 않아서 값을 변경할수 없다.
// 생성자로만 값을 초기화할 수 있도록 제약한다.
public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
    }
```
<hr>

```java
@Enumerated(EnumType.STRING)
Ordinal은 숫자로 1,2,3... 들어가기 때문에 사용하면 안된다. STRING으로 사용해야 순서가 바뀌어도 괜찮다.
```

<hr>

```java
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
단일 테이블 전략
```

<hr>

```java
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
```

<hr>

### 모든 연관관계는 지연로딩으로 설정!

```java
- 즉시로딩( EAGER )은 예측이 어렵고, 어떤 SQL이 실행될지 추적하기 어렵다. 특히 JPQL을 실행할 때 N+1 문제가 자주 발생한다.
- 실무에서 모든 연관관계는 지연로딩( LAZY )으로 설정해야 한다.
- 연관된 엔티티를 함께 DB에서 조회해야 하면, fetch join 또는 엔티티 그래프 기능을 사용한다.
- @XToOne(OneToOne, ManyToOne) 관계는 기본이 즉시로딩이므로 직접 지연로딩으로 설정해야 한다.
- @ManyToOne, @OneToOne은 기본이 즉시로딩이다. @OneToMany, @ManyToMany는 기본이 지연로딩이다.
- 모든 @ManyToOne, @OneToOne은 기본이 즉시로딩이므로 LAZY로 설정해야 한다.
```
```java
@ManyToOne(fetch = FetchType.LAZY)
@OneToOne(fetch = FetchType.LAZY)
```
```
- 안그러면 즉시로딩이 되어서 쿼리가 너무 많이 나간다.
- 지연로딩으로 설정하면 연관된 엔티티를 함께 조회할 때 쿼리가 한번만 나간다.
- 즉시로딩은 JPQL에서 N+1 문제를 일으킬 수 있다.
- 즉시로딩은 JPQL에서 조인을 사용하지 않고 연관된 엔티티를 함께 조회할 때 사용한다.
- 즉시로딩은 실무에서 사용하기 어렵다. 대부분 지연로딩을 사용해야 한다.
```

<hr>


### 컬렉션은 필드에서 초기화 하자.

```java
컬렉션은 필드에서 바로 초기화 하는 것이 안전하다.
- null 문제에서 안전하다.
- 하이버네이트는 엔티티를 영속화 할 때, 컬랙션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다.
- 만약 getOrders() 처럼 임의의 메서드에서 컬렉션을 잘못 생성하면 하이버네이트 내부 메커니즘에 문제가 발생할 수 있다.
- 따라서 필드레벨에서 초기화 하는 것이 가장 안전하고, 코드도 간결하다.
```

```java
Member member = new Member();
System.out.println(member.getOrders().getClass());
em.persist(member);
System.out.println(member.getOrders().getClass());
//출력 결과
class java.util.ArrayList
class org.hibernate.collection.internal.PersistentBag
```

<hr>

### 테이블, 컬럼명 생성 전략

```java
예를들어 @Table(name = "order_date")처럼 이름 직접 설정 안해놓으면 스프링부트에서 자동적으로 SpringPhysicalNamingStrategy을 따른다.
```

```java
1. 카멜 케이스 언더스코어(memberPoint member_point)
2. .(점) _(언더스코어)
3. 대문자 소문자
```

```java
회사마다 네이밍 전략이 다르다. 예를들어 'xx_테이블명', 'jpashop_테이블명' 이런식으로 네이밍을 한다.
```

<hr>

### Cascade

```java
CascadeType.ALL: 모두 적용
CascadeType.PERSIST: 영속
```

<hr>

### 연관관계 편의 메서드

연관관계 편의 메서드를 생성하면 양방향 연관관계일 때 편리하다.

```java
//==연관관계 편의 메서드==//
// 양방향 연관관계에서 양쪽에 값을 설정하는 메서드를 만들어주는 것이 좋다.
// 둘 중 컨트롤 하는 쪽에 연관관계 편의 메서드를 만들어 주는것이 좋다.
public void setMember(Member member) {
    this.member = member;
    member.getOrders().add(this);
}
public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
}

public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
}
```

<hr>

## 애플리케이션 아키텍처

<img alt="image" src="https://github.com/BaxDailyGit/jpabook/assets/99312529/45e7ad19-e9e5-47cb-9c46-1aeaba841793">

```java
모든것을 서비스를 거치는것이 아니라 간단한 경우에는 컨트롤러에서 바로 리포지토리를 호출하는것도 괜찮다.
```
```java
패키지 구조
    - jpabook.jpashop
        - domain
        - exception
        - repository
        - service
        - web
```

<hr>

## 개발 순서

```java
도메인,서비스, 리포지토리 계층을 먼저 개발하고, 테스트 케이스를 작성해서 검증, 마지막에 웹 계층 적용
```
정리하자면

1. 도메인 개발
2. 서비스 개발
3. 리포지토리 개발
4. 기능 테스트 (테스트 케이스 작성)

   (이때 회원을 1,2,3,4 순서로 개발하고나면 상품을 1,2,3,4 순서로 개발하고, 다음은 주문을 1,2,3,4 순서로 개발한다.)

5. 웹 계층 개발 (thymeleaf)
   (홈화면과 레이아웃, 관련 페이지들)
6. API 개발
   (controller)(CRUD)
7. API 개발 고급
   (조회용 샘플 데이터 입력, 지연로딩과 조회 성능 최적화, 컬렉션 조회 최적화, 페이징 한계 돌파, OSIV와 성능 최적화 등등)


<hr>

## 회원 도메인 개발

```java
구현 기능
    1. 회원 등록
    2. 회원 목록 조회
```
```java
순서
    1. 회원 엔티티 코드 다시 보기
    2. 회원 리포지토리 개발
    3. 회원 서비스 개발
    4. 회원 기능 테스트
```

<hr>

### 회원 레파지토리 개발

```java

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
```

<hr>

### 회원 서비스 개발

```java

```