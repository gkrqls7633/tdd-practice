package tddpractice.tddcafekiosk.spring.api.controller.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreateRequest {

//    private List<String> productNumbers;

    private Map<String, Integer> productNumberCounts;


    @Builder
    private OrderCreateRequest(List<String> productNumbers) {
        this.productNumberCounts = productNumberCounts;
    }
}
