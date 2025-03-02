package tddpractice.tddcafekiosk.unit;

import lombok.Getter;
import tddpractice.tddcafekiosk.unit.beverage.Americano;
import tddpractice.tddcafekiosk.unit.beverage.Beverage;
import tddpractice.tddcafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

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

    public void remove(Beverage beverage, int count) {
        if (count == 0) {
            throw new IllegalArgumentException("제거할 음료는 적어도 1개 이상이어야 합니다.");
        }

        for (int i = 0; i < count; i++) {
            beverages.remove(beverage);

        }
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
