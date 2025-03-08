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

        //productNumber로 상품들 조회
        List<String> productNumbers = new ArrayList<>(request.getProductNumberCounts().keySet());
        List<Product> products = findProductsBy(productNumbers);

        //todo : 각 상품별 주문 수량 확인 후 재고 차감
        //각 상품별 주문 수량 Map
        Map<String, Integer> productNumberCounts = request.getProductNumberCounts();
        for (Product product : products) {
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
        }

        //조회된 상품들로 주문 객체 생성
        Order order = Order.create(products, registeredDateTime);

        //Order 객체로 주문 생성
        Order savedOrder = orderRepository.save(order);
        if (savedOrder.getOrderProducts().isEmpty()) {
            throw new IllegalArgumentException("주문은 최소한 1개 이상의 상품이 있어야합니다.");
        }

        //저장된 Order정보 OrderResponse로 반환 필요?
        OrderResponse orderResponse = OrderResponse.of(savedOrder);

        List<ProductResponse> productResponseList = orderResponse.getProducts();

        return orderResponse;


    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        return productRepository.findAllByProductNumberIn(productNumbers);
    }

}
