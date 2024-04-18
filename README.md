# jpabook
김영한 스프링부트와 jpa 활용 따라하기


## 다시 보기

토이 플젝 하면서 설계도 어떻게 하는지 모르고 개발도 이렇게 하는게 맞나 싶어도 일단 에러가 안나서 진행했다.
이 강의에서 어떤 상황에서 어떤 행동을 하는지 나와있다. 반복적으로 다시 보고 플젝하고 다시 보고 플젝하자.


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
@OneToMany(fetch = FetchType.LAZY)
```
```
- 안그러면 즉시로딩이 되어서 쿼리가 너무 많이 나간다.
- 지연로딩으로 설정하면 연관된 엔티티를 함께 조회할 때 쿼리가 한번만 나간다.
- 즉시로딩은 JPQL에서 N+1 문제를 일으킬 수 있다.
- 즉시로딩은 JPQL에서 조인을 사용하지 않고 연관된 엔티티를 함께 조회할 때 사용한다.
- 즉시로딩은 실무에서 사용하기 어렵다. 대부분 지연로딩을 사용해야 한다.
```





```java

```

```java

```

```java

```

```java

```

