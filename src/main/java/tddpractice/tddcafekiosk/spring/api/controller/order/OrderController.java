package tddpractice.tddcafekiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tddpractice.tddcafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import tddpractice.tddcafekiosk.spring.api.service.order.OrderService;
import tddpractice.tddcafekiosk.spring.api.service.order.response.OrderResponse;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/order/new")
    public OrderResponse createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        LocalDateTime registeredDateTime = LocalDateTime.now();

        return orderService.createOrder(orderCreateRequest, registeredDateTime);
    }

}
