package tddpractice.tddcafekiosk.spring.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tddpractice.tddcafekiosk.spring.domain.BaseEntity;
import tddpractice.tddcafekiosk.spring.domain.orderproduct.OrderProduct;
import tddpractice.tddcafekiosk.spring.domain.productstock.ProductStock;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;

    private String name;

    private int price;

    //재고수량
    private int stockCount;

//    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
//    private ProductStock productStock;

//    @Builder
    private Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    //재고 수량 있는 생성자
    @Builder
    private Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price, int stockCount) {
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
        this.stockCount = stockCount;
    }

}
