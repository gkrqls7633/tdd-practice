package tddpractice.tddcafekiosk.spring.domain.product;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tddpractice.tddcafekiosk.spring.api.service.product.response.ProductResponse;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
//@SpringBootTest // 통합테스트
@DataJpaTest      // 가벼운 용도
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품을 조회한다.")
    @Test
    void findAllBySellingStatusIn() {

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

        productRepository.saveAll(productList);

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("원하는 판매상태를 가진 상품 리스트를 조회할 때, 누락되는 정보가 없는지 체크한다.")
    @Test
    void findFailAllBySellingStatusIn() {

        //given
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

        //when
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        productRepository.saveAll(productList);

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        //then
        assertThat(products).isNotEmpty(); // 리스트가 비어 있으면 바로 실패
        for (Product product : products) {
            assertThat(product.getProductNumber()).isNotNull();
            assertThat(product.getType()).isNotNull();
            assertThat(product.getSellingStatus()).isNotNull();
            assertThat(product.getName()).isNotNull();
            assertThat(product.getPrice()).isNotNull();
        }
    }


}
