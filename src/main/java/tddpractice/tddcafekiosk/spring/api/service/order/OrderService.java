package tddpractice.tddcafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tddpractice.tddcafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import tddpractice.tddcafekiosk.spring.api.service.order.response.OrderResponse;
import tddpractice.tddcafekiosk.spring.api.service.product.response.ProductResponse;
import tddpractice.tddcafekiosk.spring.domain.order.Order;
import tddpractice.tddcafekiosk.spring.domain.order.OrderRepository;
import tddpractice.tddcafekiosk.spring.domain.product.Product;
import tddpractice.tddcafekiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {

        /*
        요구사항 : 상품 번호 리스트를 받아 주문 생성하기
         - OrderCreateRequest에 Map 형태로 productNumber : count(주문수량) 받아서 처리
         */

        //productNumber로 상품들 조회
        List<String> productNumbers = new ArrayList<>(request.getProductNumberCounts().keySet());  //map 처리 로직
        List<Product> products = findAllByProductNumberIn(productNumbers);

        /*
        요구사항 : 주문 생성 시 재고 확인 및 개수 차감 후 생성하기
         - 각 상품별 주문 수량 확인 후 재고 차감
         */
        this.stockCheckAndUpdate(request, products);

        //조회된 상품들로 주문 객체 생성
        Order order = Order.create(products, registeredDateTime);

        // Order 객체로 주문 생성
        // 주문 1개 이상 구매 가능하게 방어로직 추가
        Order savedOrder = orderRepository.save(order);
        if (savedOrder.getOrderProducts().isEmpty()) {
            throw new IllegalArgumentException("주문은 최소한 1개 이상의 상품이 있어야합니다.");
        }

        OrderResponse orderResponse = OrderResponse.of(savedOrder);
        return orderResponse;

    }

    private void stockCheckAndUpdate(OrderCreateRequest request, List<Product> products) {
        //각 상품별 주문 수량 Map
        Map<String, Integer> productNumberCounts = request.getProductNumberCounts();
        for (Product product : products) {

            /* 요구사항
             - 재고 관련 타입 체크(병 음료, 베이커리만 재고 처리 가능)
             */
            if (product.getType().checkRelatedStockProduct()) {
                //특정 상품의 주문 수
                int productOrderCount = productNumberCounts.get(product.getProductNumber());
                //기존 재고 수
                int currentStockCount = product.getStockCount();
                //주문 수만큼 재고 차감
                int newStockCount = currentStockCount - productOrderCount;
                if (newStockCount < 0) {
                    throw new IllegalArgumentException("재고 수량보다 주문 수가 많습니다.");
                } else {
                    productRepository.save(product);
                }

            } else{
                throw new IllegalArgumentException("재고와 관련 있는 상품 타입이 아닙니다.");
            }
        }
    }

    private List<Product> findAllByProductNumberIn(List<String> productNumbers) {
        return productRepository.findAllByProductNumberIn(productNumbers);
    }
}
