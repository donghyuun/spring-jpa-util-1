package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속 클래스 객체들 모두 한 테이블(싱글 테이블)에 때려박음
@DiscriminatorColumn(name = "dtype") // 상속 클래스 객체들 한 테이블일때 구분해줘야 함
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue // db에 자동 생성(개발자가 안 지정해줘도 됌)
    @Column(name = "item_id")
    private Long id;

    private String name;//이름
    private int price;//가격
    private int stockQuantity;//현재

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>(); //다대다 관계

}
