package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //이름 없으면 테이블명이 Order가 됨
public class Order {

    @Id @GeneratedValue // db에 자동 생성(개발자가 안 지정해줘도 됌)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne //주문 여러개가 회원 한명에게 매핑되기 때문에
    @JoinColumn(name = "member_id") //Member 엔티티에 매핑을 뭐로 할껀지
    private Member member;

    @OneToMany(mappedBy = "order")// OrderItem 의 order 속성에 매핑을 당하는 입장(주인x)
    private List<OrderItem> orderItems = new ArrayList<>(); //한번 주문할때 상품 여러개 들어있을 수 있으므로

    @OneToOne
    @JoinColumn(name = "delivery_id") //주인(왜래키 보유)
    private Delivery delivery;

    private LocalDateTime orderDate;//시간, 분, 초 까지 다 있음(hibernate 가 자동지원 해줌)

    @Enumerated(EnumType.STRING)
    private OrderStatus status;// 주문 상태 [ORDER, CANCEL]
}
