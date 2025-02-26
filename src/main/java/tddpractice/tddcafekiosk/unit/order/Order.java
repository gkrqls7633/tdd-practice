package tddpractice.tddcafekiosk.unit.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tddpractice.tddcafekiosk.unit.beverage.Beverage;

import java.time.LocalDateTime;
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
    private final int START_OPERATION_TIME = 10;
    private final int END_OPERATION_TIME = 22;
    
    public Boolean validOrderTime(LocalDateTime dateTime) {
    	 LocalDateTime startTime = dateTime.toLocalDate().atTime(START_OPERATION_TIME, 0); // 10시
         LocalDateTime endTime = dateTime.toLocalDate().atTime(END_OPERATION_TIME, 0);   // 22시

         return !dateTime.isBefore(startTime) && !dateTime.isAfter(endTime);
    }
}
