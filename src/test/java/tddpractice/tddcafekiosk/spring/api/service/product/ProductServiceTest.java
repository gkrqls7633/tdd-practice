package tddpractice.tddcafekiosk.spring.api.service.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tddpractice.tddcafekiosk.spring.domain.product.Product;
import tddpractice.tddcafekiosk.spring.domain.product.ProductRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductType.HANDMADE;


@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithProducts() {

        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingStatus(SELLING)  //판매상태
                .name("아메리카노")
                .price(4000)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(HOLD)  //판매상태
                .name("카페라떼")
                .price(4500)
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        //when



    }

    @DisplayName("상품 번호 리스트가 빈 값이면 주문을 생성할 수 없다.")
    @Test
    void createOrderWithNoProducts() {

    }
}