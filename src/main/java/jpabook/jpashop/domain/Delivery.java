package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY) //1대1 관계일때도 연관관계(주인, 하인?) 매핑을 해줘야함
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // 이걸 써야지 중간에 추가되도 밀리는게 없음, ORDINAL 절대 사용 X!
    private DeliveryStatus status;//배송상태, READY(배송준비중), COMP(배송중)
}
