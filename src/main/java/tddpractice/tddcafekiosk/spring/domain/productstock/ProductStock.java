package tddpractice.tddcafekiosk.spring.domain.productstock;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tddpractice.tddcafekiosk.spring.domain.BaseEntity;
import tddpractice.tddcafekiosk.spring.domain.product.Product;

@Getter
@NoArgsConstructor
//@Entity
public class ProductStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stockCount;

    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "productNumber", referencedColumnName = "productNumber") // productNumber 기준으로 조인
    private Product product;


    @Builder
    public ProductStock(Product product) {
        this.stockCount = calculateStockCount(product);
        this.product = product;
    }

    //재고 수량 계산
    public int calculateStockCount(Product product) {

        return 0;
    }

}
