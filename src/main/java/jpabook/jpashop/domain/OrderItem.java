package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // 주문 아이템은 여러개, 아이템의 개념은 한개이기 때문, 주인O
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")// Order 엔티티의 Column 명이 "order_id"(왜래 키) 인 것과 매핑됨, 주인O
    private Order order;

    private int orderPrice; // 주문 당시의 가격
    private int count; // 주문 수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);//재고를 감소시킨다
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {// item에서의 재고수량을 원상복구 해준다
        this.getItem().addStock(count);
    }

    //==조회 로직==//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

}
