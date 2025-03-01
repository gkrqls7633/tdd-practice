package tddpractice.tddcafekiosk.unit;

import lombok.Getter;
import tddpractice.tddcafekiosk.unit.beverage.Beverage;
import tddpractice.tddcafekiosk.unit.order.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
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

    public Order createOrder(LocalDateTime now) throws Exception {
    	
    	LocalDateTime openTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 00));
    	LocalDateTime closeTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 00));
    	
    	if(now.isBefore(openTime) || now.isAfter(closeTime)) {
    		throw new Exception("가게 운영시간이 아닙니다.");
    	}
    	
        return new Order(now, beverages);
    }
    
    public void addMany(Beverage beverage, int count) {
    	
    	if(count <= 0) {
    		throw new NullPointerException("음료는 1잔 이상 주문하셔야 합니다.");
    	}
    	
    	for(int i=0; i<count; i++) {
    		beverages.add(beverage);
    	}
    	
    }

}
