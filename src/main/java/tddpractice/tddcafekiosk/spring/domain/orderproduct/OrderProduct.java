package tddpractice.tddcafekiosk.spring.domain.orderproduct;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tddpractice.tddcafekiosk.spring.domain.BaseEntity;
import tddpractice.tddcafekiosk.spring.domain.order.Order;
import tddpractice.tddcafekiosk.spring.domain.product.Product;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public OrderProduct(Order order, Product product) {
        this.order = order;
        this.product = product;
    }
}
