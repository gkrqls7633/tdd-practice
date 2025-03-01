package tddpractice.tddcafekiosk.unit.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Latte class 를 테스트코드로 작성할 수 있다.
 * 간단한 entity class 이지만 충분히 테스트 할 가치가 있다.
 */
class LatteTest {


    @DisplayName("라떼 이름은 라떼다.")
    @Test
    void latteNameCheck() {
        //given
        Latte latte = new Latte();

        //when & then
        assertThat(latte.getName()).isEqualTo("라떼");
    }

    @DisplayName("라떼 가격은 4500원이다.")
    @Test
    void lattePriceCheck() {
        //given
        Latte latte = new Latte();

        //when & then
        assertThat(latte.getPrice()).isEqualTo(4500);
    }
}