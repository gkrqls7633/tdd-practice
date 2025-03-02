package tddpractice.tddcafekiosk.unit.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/*
 * Amaricano class 를 테스트코드로 작성할 수 있다.
 * 간단한 entity class 이지만 충분히 테스트 할 가치가 있다.
 */
class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();

//        assertEquals(americano.getName(), "아메리카노");
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4000);
    }

}