package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;//회원명

    @Embedded //내장 타입을 나타냄
    private Address address;//주소

    @OneToMany(mappedBy = "member") //한명의 회원이 여러개의 상품을 주문하기 때문에, Order 클래스의

    // member 속성에 매핑을 당하는 입장(읽기전용, 주인x)
    private List<Order> orders = new ArrayList<>();//회원의 주문 리스트
}
