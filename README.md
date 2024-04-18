# jpabook
김영한 스프링부트와 jpa 활용 따라하기


## 다시 보기

지금껏 토이 플젝 하면서 설계가 이게 맞는지 싶고  
개발도 이렇게 하는게 맞나 싶은 불안감이 항상 존재.  
이 강의에서는 어떤 상황에서 어떤 행동을 하는지 나와있다.  
반복적으로 다시 보고 플젝하고 다시 보고 플젝하자.  


## 배운 내용 정리

```java
실무에서는 가급적 getter는 열어두고 setter는 꼭 필요한 경우에만 사용
```

```java
양방향 연관관계: 두 테이블중 하나를 연관관계의 주인으로 정해야한다.
(주로 외래키(@JoinColumn을 붙인)를 가진 엔티티를 연관관계 주인으로 한다.)
연관관계 주인이 아닌 엔티티의 필드에 (mappedBy = "member")를 한다. 즉 읽기 전용
```

```java
양방향 연관관계에서 1대1관계는 외래키를 어디에 두어도 상관없다.
하지만 주로 access를 많아하는 쪽에서 외래키를 둔다.
```

```java
@Table(name = "orders") // 테이블 이름을 orders로 매핑한다.
안하면 클래스 이름인 order를 테이블 이름으로 매핑하는데 예약어이기 때문에 orders로 하는것을 추천
```

```java
@Column(name = "order_id") // order_id로 매핑한다. DBA분들이 이렇게 이름 짓는것을 선호한다.
//엔티티는 타입이 있으므로 id 필드만으로 쉽게 구분할 수 있다. 테이블은 타입이 없으므로 구분이 어렵다.
```

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


```java
@Enumerated(EnumType.STRING)
Ordinal은 숫자로 1,2,3... 들어가기 때문에 사용하면 안된다. STRING으로 사용해야 순서가 바뀌어도 괜찮다.
```

```java
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
단일 테이블 전략
```

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

```java

```

```java

```

```java

```

```java

```

