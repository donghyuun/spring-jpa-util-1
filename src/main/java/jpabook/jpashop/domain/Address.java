package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable //내장 타입
@Getter
public class Address {

    // 생성할때만 값을 생성하고, Setter 을 제공안해서 변경이 안되도록(immutable)하게 해야함
    private String city; // 도시
    private String street;// 길
    private String zipcode;// 우편번호

    protected Address() {//jpa 스택상 만들어놓은것(JPA 에서 엔티티나 임베이드 타입은 기본생성자가 필수적으로 필요함), new 불가하도록 만들어놓음

    }
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
