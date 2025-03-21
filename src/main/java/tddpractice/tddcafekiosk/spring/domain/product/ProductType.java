package tddpractice.tddcafekiosk.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리");

    private final String text;

    public boolean checkRelatedStockProduct() {
        if (this == BOTTLE || this == BAKERY) {
            return true;
        } else {
            return false;
        }
    }


}
