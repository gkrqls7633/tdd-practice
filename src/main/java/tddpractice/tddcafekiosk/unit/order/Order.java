package tddpractice.tddcafekiosk.unit.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tddpractice.tddcafekiosk.unit.beverage.Beverage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/*
 주문 생성 시 필요 도메인 정의
  - 주문 시간
  - 음료 리스트
 */

@Getter
//@RequiredArgsConstructor
public class Order {

    private static final LocalTime STORE_OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime STORE_CLOSE_TIME = LocalTime.of(22, 0);

    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverages;

    public Order (LocalDateTime orderDateTime, List<Beverage> beverages) {

        int beverageCount = countBeverages(beverages);

        //주문할 음료 목록이 없으면 에러
        if(beverageCount == 0) {
            throw new IllegalArgumentException("주문 생성 가능한 음료가 없습니다. 주문 목록을 다시 한번 확인해주세요.");
        }

        //주문 가능시간이 아닌경우 에러
        LocalTime orderTime = orderDateTime.toLocalTime();
        if (orderTime.isBefore(STORE_OPEN_TIME) || orderTime.isAfter(STORE_CLOSE_TIME)) {
            throw new IllegalArgumentException("주문 가능 시간이 아닙니다. (10시~22시)");
        }

        this.orderDateTime = orderDateTime;
        this.beverages = beverages;
    }

    private int countBeverages(List<Beverage> beverages) {
        return beverages.size();
    }



}
