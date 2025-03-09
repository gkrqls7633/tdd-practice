package tddpractice.tddcafekiosk.spring.api.service.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tddpractice.tddcafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import tddpractice.tddcafekiosk.spring.api.service.order.response.OrderResponse;
import tddpractice.tddcafekiosk.spring.domain.order.OrderRepository;
import tddpractice.tddcafekiosk.spring.domain.orderproduct.OrderProductRepository;
import tddpractice.tddcafekiosk.spring.domain.product.Product;
import tddpractice.tddcafekiosk.spring.domain.product.ProductRepository;
import tddpractice.tddcafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductType.*;

@ActiveProfiles("test")
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderProductRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();
    }


    @DisplayName("상품 번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithProducts() {

        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(BOTTLE)
                .sellingStatus(SELLING)  //판매상태
                .name("아메리카노")
                .price(4000)
                .stockCount(10)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(BOTTLE)
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
                .type(BOTTLE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .stockCount(1) //현재 재고
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(BOTTLE)
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

    @DisplayName("재고 상품 타입이 아닌 상품 번호 리스트로 주문을 생성할 수 없다.")
    @Test
    void createOrderWithNotStockType() {

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
                .type(BOTTLE)
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

        //when & then
        assertThatThrownBy(() ->  orderService.createOrder(orderCreateRequest, registeredDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고와 관련 있는 상품 타입이 아닙니다.");
    }

    @DisplayName("재고와 관련이 없는 상품 타입은 제조 음료다.")
    @Test
    void checkStockProductTypeFailCase() {
        //given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(HANDMADE)  //제조음료
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .stockCount(1) //현재 재고
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(BOTTLE)  //병음료
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .stockCount(5)
                .build();

        Product product3 = Product.builder()
                .productNumber("002")
                .type(BAKERY)  //병음료
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .stockCount(5)
                .build();


        //when
        boolean possibleStockCheckType1 = product1.getType().checkRelatedStockProduct();  //false
        boolean possibleStockCheckType2 = product2.getType().checkRelatedStockProduct();  //true
        boolean possibleStockCheckType3 = product3.getType().checkRelatedStockProduct();  //true

        //then
        assertAll(
                () -> assertFalse(possibleStockCheckType1, "HANDMADE Type should not have related stock"),
                () -> assertTrue(possibleStockCheckType2, "BOTTLE Type should have related stock"),
                () -> assertTrue(possibleStockCheckType3, "BAKERY Type should have related stock")

        );
    }

    @DisplayName("재고와 관련이 있는 상품 타입은 병음료, 베이커리다.")
    @Test
    void checkStockProductTypeSuccessCase() {
        //given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(BOTTLE)  //제조음료
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .stockCount(1) //현재 재고
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(BOTTLE)  //병음료
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .stockCount(5)
                .build();

        Product product3 = Product.builder()
                .productNumber("002")
                .type(BAKERY)  //병음료
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .stockCount(5)
                .build();

        //when
        boolean possibleStockCheckType1 = product1.getType().checkRelatedStockProduct();  //true
        boolean possibleStockCheckType2 = product2.getType().checkRelatedStockProduct();  //true
        boolean possibleStockCheckType3 = product3.getType().checkRelatedStockProduct();  //true

        //then
        assertAll(
                () -> assertTrue(possibleStockCheckType1, "BOTTLE Type should have related stock"),
                () -> assertTrue(possibleStockCheckType2, "BOTTLE Type should have related stock"),
                () -> assertTrue(possibleStockCheckType3, "BAKERY Type should have related stock")

        );
    }
}