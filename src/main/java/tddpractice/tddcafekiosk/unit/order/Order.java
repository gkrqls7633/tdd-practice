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
    
    public Boolean validOrderTime() {
    	 LocalDateTime startTime = this.orderDateTime.toLocalDate().atTime(START_OPERATION_TIME, 0); // 10시
         LocalDateTime endTime = this.orderDateTime.toLocalDate().atTime(END_OPERATION_TIME, 0);   // 22시

         return !this.orderDateTime.isBefore(startTime) && !this.orderDateTime.isAfter(endTime);
    }
    

    // 주문 목록에 음료 추가 삭제
    // 키오스크에 있는 음료 리스트를 주문 기능으로 가져온다.
}
