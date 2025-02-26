package tddpractice.tddcafekiosk.unit;

import lombok.Getter;
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

    public void remove(Beverage beverage) {
    	beverage.returnCount();
        beverages.remove(beverage);
    }

    public void clear() {
    	for (Beverage beverage : beverages) {
    		beverage.returnCount();
    	}
        beverages.clear();
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (Beverage beverage : beverages) {
            totalPrice += beverage.getPrice() * beverage.getCount();
        }
        return totalPrice;
    }

    public Order createOrder() {
        return new Order(LocalDateTime.now(), beverages);
    }
    
    public void addBeverageCount(Beverage beverage) {
    	beverage.addCount();
    }
}
