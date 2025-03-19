package tddpractice.tddcafekiosk.spring.api.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tddpractice.tddcafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import tddpractice.tddcafekiosk.spring.api.service.order.OrderService;
import tddpractice.tddcafekiosk.spring.api.service.order.response.OrderResponse;
import tddpractice.tddcafekiosk.spring.api.service.product.response.ProductResponse;
import tddpractice.tddcafekiosk.spring.domain.order.Order;
import tddpractice.tddcafekiosk.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static tddpractice.tddcafekiosk.spring.domain.product.ProductType.HANDMADE;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @DisplayName("신규 주문을 등록한다.")
    @Test
    void createOrder() throws Exception {

        // given
        List<Product> products = List.of(Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build());

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001"))
                .build();

        LocalDateTime localDateTime = LocalDateTime.now();

        Order order = Order.create(products, localDateTime);
        OrderResponse mockOrderResponse = OrderResponse.of(order);

        //when
        Mockito.when(orderService.createOrder(Mockito.any(OrderCreateRequest.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(mockOrderResponse);  //orderService.createOrder가 호출되면 mockOrderResponse를 반환

        // then
        mockMvc.perform(
                        post("/api/v1/orders/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(4000));

        //파라미터 검증
        Mockito.verify(orderService).createOrder(Mockito.argThat(argument ->
                argument.getProductNumbers().equals(request.getProductNumbers())
        ), Mockito.any(LocalDateTime.class));  // request의 productNumbers가 잘 전달됐는지 확인
    }

}
