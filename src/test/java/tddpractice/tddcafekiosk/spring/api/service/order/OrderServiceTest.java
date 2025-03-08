package tddpractice.tddcafekiosk.spring.api.service.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tddpractice.tddcafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import tddpractice.tddcafekiosk.spring.api.service.order.response.OrderResponse;
import tddpractice.tddcafekiosk.spring.domain.product.Product;
import tddpractice.tddcafekiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;


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
                .stockCount(10)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(HOLD)  //판매상태
                .name("카페라떼")
                .price(4500)
                .stockCount(5)
                .build();

        List<Product> productList = List.of(product1, product2);
        productRepository.saveAll(productList);

        // productList에서 productNumber별 주문 개수 계산
        Map<String, Integer> productNumberCounts = productList.stream()
                .collect(Collectors.toMap(
                        Product::getProductNumber,
                        p -> 1, // 1개씩 주문
                        Integer::sum // 같은 productNumber가 있으면 개수를 합산
                ));

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setProductNumberCounts(productNumberCounts);

        LocalDateTime registeredDateTime = LocalDateTime.now();

        //when
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest, registeredDateTime);

        //then
        assertNotNull(orderResponse);
        assertThat(orderResponse.getProducts().size()).isEqualTo(2);
    }

    @DisplayName("재고가 부족하면 주문 생성은 불가하다.")
    @Test
    void cannotCreateOrderWithInsufficientStock() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .stockCount(1) //현재 재고
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .stockCount(1) // 현재 재고
                .build();

        List<Product> productList = List.of(product1, product2);
        productRepository.saveAll(productList);

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        Map<String, Integer> orderCreateRequestMap = new HashMap<>();
        orderCreateRequestMap.put("001", 1);
        orderCreateRequestMap.put("002", 2);
        orderCreateRequest.setProductNumberCounts(orderCreateRequestMap);

        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when & then
        //라떼의 재고는 1인데 2개 주문했으므로 에러 발생해야함.
        assertThatThrownBy(() -> orderService.createOrder(orderCreateRequest, registeredDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고 수량보다 주문 수가 많습니다.");
    }
}