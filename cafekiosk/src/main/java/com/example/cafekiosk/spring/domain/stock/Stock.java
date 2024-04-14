package com.example.cafekiosk.spring.domain.stock;

import com.example.cafekiosk.spring.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본적으로 기본생성자가 필요하다.
@Entity
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    private int quantity;

    @Builder
    private Stock(String productNumber, int quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }

    public static Stock create(String productNumber, int quantity) {
        return Stock.builder()
                .productNumber(productNumber)
                .quantity(quantity)
                .build();
    }

    public boolean isQuantityLessThan(int quantity) {
        return this.quantity < quantity;
    }

    // 서비스에서 재고를 체크하는 로직과 별도로 재고 도메인은 다른곳에서 쓸 수 있으며 자체로 보장을 해줘야한다.
    public void deductQuantity(int quantity) {
        if(isQuantityLessThan(quantity)){
            throw new IllegalArgumentException("차감할 재고가 수량이 없습니다.");
        }
        this.quantity -= quantity;
    }
}
