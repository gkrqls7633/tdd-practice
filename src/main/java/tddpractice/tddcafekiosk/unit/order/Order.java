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
@RequiredArgsConstructor
public class Order {

    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverages;

}