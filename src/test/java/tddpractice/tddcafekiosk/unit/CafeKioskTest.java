package tddpractice.tddcafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tddpractice.tddcafekiosk.unit.beverage.Americano;
import tddpractice.tddcafekiosk.unit.beverage.Beverage;
import tddpractice.tddcafekiosk.unit.beverage.Latte;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


/*
 * CafeKiosk 클래스의 각각의 메소드를 테스트 코드로 작성해야한다.
 * 실패 케이스를 먼저 고려하여 테스트 코드를 작성하는 습관을 들여보자.
 */


// 한 종류의 음료 여러 잔을 한 번에 담는 기능
// 가게 운영시간 (10:00~22:00) 이외에는 주문 생성 불가

class CafeKioskTest {

    private static final Logger log = LoggerFactory.getLogger(CafeKioskTest.class);

    @DisplayName("하나의 음료는 적어도 1개 이상의 음료를 포함해야 한다. ")
    @Test
    void addZeroBeverage() {

        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // when & then
        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료 개수는 1 이상이어야 합니다.")
                .satisfies(ex -> log.error("예외 발생: ", ex)); // 예외 로그 남기기
    }

    @DisplayName("키오스크 주문 목록에 상품을 추가한다.")
    @Test
    void add() {

        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano, 1);
        cafeKiosk.add(latte, 1);

        // when & then
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
    }


}