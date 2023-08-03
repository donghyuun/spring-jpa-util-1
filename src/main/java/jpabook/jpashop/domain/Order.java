package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders") //이름 없으면 테이블명이 Order가 됨
@Getter @Setter
public class Order {

    @Id @GeneratedValue // db에 자동 생성(개발자가 안 지정해줘도 됌)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY) //주문 여러개가 회원 한명에게 매핑되기 때문에
    @JoinColumn(name = "member_id") //Member 엔티티에 매핑을 뭐로 할껀지
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)// OrderItem 의 order 속성에 매핑을 당하는 입장(주인x)
    private List<OrderItem> orderItems = new ArrayList<>(); //한번 주문할때 상품 여러개 들어있을 수 있으므로

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)//CascadeType.ALL => order만 persist 하면 delivery 도 함께 persist 된다!, 원래는 모든 엔티티들은 각각 persist 해줘야 함
    @JoinColumn(name = "delivery_id") //주인(왜래키 보유)
    private Delivery delivery;

    private LocalDateTime orderDate;//시간, 분, 초 까지 다 있음(hibernate 가 자동지원 해줌)

    @Enumerated(EnumType.STRING)
    private OrderStatus status;// 주문 상태 [ORDER, CANCEL]

    //==연관관계 편의 메서드(양방향일때 양쪽 다 세팅)==//
    //위치는 핵심적으로 control 하는곳이 들고 있는게 좋음
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

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    //주문 취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);// 취소 상태로 바꿔줌
        for (OrderItem orderItem : this.orderItems) {// 루프를 돌면서 재고를 원상복구
            orderItem.cancel();
        }
    }


    //==조회 로직==//
    //전체 주문 가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
