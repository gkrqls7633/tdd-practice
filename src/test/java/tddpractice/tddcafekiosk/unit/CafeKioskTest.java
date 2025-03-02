package tddpractice.tddcafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tddpractice.tddcafekiosk.unit.beverage.Americano;
import tddpractice.tddcafekiosk.unit.beverage.Latte;
import tddpractice.tddcafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;


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
                .satisfies(ex -> log.error("예외 발생: ", ex));
    }

    @DisplayName("키오스크 주문 목록에 상품을 추가한다.")
    @Test
    void addBeverage() {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano, 1);
        cafeKiosk.add(latte, 1);

        // when & then
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
    }

    @DisplayName("영업시간이 아닌 시간에 주문을 생성할 수 없다.")
    @Test
    void createOrderWithNotOpenStore() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 1);
        LocalDateTime orderDateTime = LocalDateTime.now().withHour(22).withMinute(0);

        // when & then
        // 주문 시간이 10시 ~ 22시 사이인지 검증
        assertThatThrownBy(() -> cafeKiosk.createOrder(orderDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 가능 시간이 아닙니다. (10시~22시)")
                .satisfies(ex -> log.error("예외 발생: ", ex));
    }

    @DisplayName("주문 생성은 주문 음료가 적어도 1개 이상 있어야한다.")
    @Test
    void createOrderWithNoBeverage() {

        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 1);
        LocalDateTime orderDateTime = LocalDateTime.now().withHour(20).withMinute(0);

        // when
        Order order = cafeKiosk.createOrder(orderDateTime);

        // then
        assertThat(order.getBeverages()).hasSize(1);
    }

    @DisplayName("영업시간에는 주문 생성이 가능하다.")
    @Test
    void addWithOpenStore() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        LocalTime storeStartTime = LocalTime.of(10, 0);
        LocalTime storeEndTime = LocalTime.of(22, 0);

        LocalDateTime orderDateTime = LocalDateTime.now().withHour(20).withMinute(0);
        LocalTime orderTime = orderDateTime.toLocalTime();

        // when & then
        // 주문 시간이 10시 ~ 22시 사이인지 검증
        assertThat(orderTime)
                .isAfterOrEqualTo(storeStartTime)
                .isBeforeOrEqualTo(storeEndTime);
    }

    @DisplayName("주문목록에서 음료를 제거할 수 있다.")
    @Test
    void removeBeverages() {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 1);

        //when & then
        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(0);
    }

    @DisplayName("주문목록에서 음료 제거는 1개 이상 해야한다.")
    @Test
    void removeZeroBeverage() {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // when & then
        assertThatThrownBy(() -> cafeKiosk.remove(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제거할 음료는 적어도 1개 이상이어야 합니다.")
                .satisfies(ex -> log.error("예외 발생: ", ex));
    }

    @DisplayName("주문목록을 비운다.")
    @Test
    void clearBeverages() {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano, 1);
        cafeKiosk.add(latte, 2);

        //when
        cafeKiosk.clear();

        //then
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }
}