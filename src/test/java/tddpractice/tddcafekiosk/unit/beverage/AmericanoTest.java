package tddpractice.tddcafekiosk.unit.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/*
 * Amaricano class 를 테스트코드로 작성할 수 있다.
 * 간단한 entity class 이지만 충분히 테스트 할 가치가 있다.
 */
class AmericanoTest {

    @DisplayName("아메리카노 이름은 아메리카노다.")
    @Test
    void americanoNameCheck() {
        //given
        Americano americano = new Americano();

        //when & then
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @DisplayName("아메리카노의 가격은 4000원이다.")
    @Test
    void americanoPriceCheck() {
        //given
        Americano americano = new Americano();

        //when & then
        assertThat(americano.getPrice()).isEqualTo(4000);
    }

}