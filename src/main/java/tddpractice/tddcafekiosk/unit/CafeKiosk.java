package tddpractice.tddcafekiosk.unit;

import lombok.Getter;
import tddpractice.tddcafekiosk.unit.beverage.Beverage;
import tddpractice.tddcafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

//    private static final LocalTime storeStartTime = LocalTime.of(10, 0);
//    private static final LocalTime storeEndTime = LocalTime.of(22, 0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    // 20250226 요구사항 추가 : 한 종류의 음료 여러 잔을 한 번에 담는 기능 추가
    public void add(Beverage beverage, int count) {

        if (count == 0) {
            throw new IllegalArgumentException("음료 개수는 1 이상이어야 합니다.");
        }

        for (int i = 0; i < count; i++) {
            beverages.add(beverage);
        }

    }

    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (Beverage beverage : beverages) {
            totalPrice += beverage.getPrice();
        }
        return totalPrice;
    }

    public Order createOrder(LocalDateTime orderDateTime) {
        return new Order(orderDateTime, beverages);
    }

}
