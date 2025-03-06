package tddpractice.tddcafekiosk.spring.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    INIT("주문생성"),
    CANCELED("주문취소"),
    PAYMENT_COMPLETED("결제완료"),
    PAYMENT_FAILED("결제실패"),
    RECEIVED("주문접수"),
    COMPLETED("처리완료");

    private final String text;

}
