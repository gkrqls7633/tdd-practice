package tddpractice.tddcafekiosk.unit.beverage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import tddpractice.tddcafekiosk.unit.CafeKiosk;
import tddpractice.tddcafekiosk.unit.order.Order;

/*
 * Latte class 를 테스트코드로 작성할 수 있다.
 * 간단한 entity class 이지만 충분히 테스트 할 가치가 있다.
 */
class LatteTest {
	
	@Test
	public void orderLatte() {
		
		CafeKiosk cafeKiosk = new CafeKiosk();
		
		try {
		
		int latteCount = 3;
		
		assert latteCount > 0 : "음료는 1잔 이상 주문하셔야 합니다.";
		
		cafeKiosk.addMany(new Latte(), latteCount);
		
		LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30));
		
		LocalDateTime openTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 00));
    	LocalDateTime closeTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 00));
    	
		assert now.isAfter(openTime) && now.isBefore(closeTime) : "지금은 운영시간이 아닙니다.";
		
		Order order = cafeKiosk.createOrder(now);
		
		int totalPrice = cafeKiosk.calculateTotalPrice();
		
		System.out.println("주문 시간: " + order.getOrderDateTime());
		System.out.println("주문 내역:");
		for(Beverage beverage : order.getBeverages()) {
			System.out.println(beverage.getName() + " " + beverage.getPrice());
		}
		System.out.println("총 가격: " + totalPrice + "원");
		System.out.println(">>> 주문 완료");
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		
	}

}