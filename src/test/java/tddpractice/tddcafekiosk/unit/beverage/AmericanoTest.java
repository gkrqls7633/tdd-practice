package tddpractice.tddcafekiosk.unit.beverage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tddpractice.tddcafekiosk.unit.CafeKiosk;
import tddpractice.tddcafekiosk.unit.order.Order;

/*
 * Amaricano class 를 테스트코드로 작성할 수 있다.
 * 간단한 entity class 이지만 충분히 테스트 할 가치가 있다.
 */
class AmericanoTest {
	
	@DisplayName("운영시간에 아메리카노 3잔 주문")
	@Test
//	@Disabled
	public void orderAmericano() {
		
		System.out.println("===========Test1: 운영시간에 아메리카노 3잔 주문===========");
		
		CafeKiosk cafeKiosk = new CafeKiosk();
		
		int americanoCount = 3;
		
		try {
			cafeKiosk.addMany(new Americano(), americanoCount);
			System.out.println("아메리카노 총 " + americanoCount + "잔 주문");
			
			LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30));
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
	

	@DisplayName("아메리카노 0잔을 담는 경우")
	@Test
//	@Disabled
	public void orderZeroAmericano() {
		
		System.out.println("===========Test2: 아메리카노 0잔을 담는 경우===========");
		
		CafeKiosk cafeKiosk = new CafeKiosk();
		
		int americanoCount = 0;
		
		try {
			cafeKiosk.addMany(new Americano(), americanoCount);
			System.out.println("아메리카노 총 " + americanoCount + "잔 주문");
			
			LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30));
			System.out.println("현재 시간: " + now);
			Order order = cafeKiosk.createOrder(now);
			
			int totalPrice = cafeKiosk.calculateTotalPrice();
			System.out.println(">>> 주문 완료");
			System.out.println(">>> 주문 시간: " + order.getOrderDateTime());
			System.out.println(">>> 주문 내역: " + order.getBeverages());
			System.out.println(">>> 총 가격: " + totalPrice + "원");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@DisplayName("운영시간 외에 아메리카노를 주문 할 경우")
	@Test
//	@Disabled
	public void orderAmericanoClosedTime() {
		
		System.out.println("===========Test3: 운영시간 외에 아메리카노를 주문 할 경우===========");
		
		CafeKiosk cafeKiosk = new CafeKiosk();
		
		int americanoCount = 3;
		LocalDateTime failTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 30));
		
		try {
			cafeKiosk.addMany(new Americano(), americanoCount);
			System.out.println("아메리카노 총 " + americanoCount + "잔 주문");
			
			System.out.println("현재 시간: " + failTime);
			Order order = cafeKiosk.createOrder(failTime);
			
			int totalPrice = cafeKiosk.calculateTotalPrice();
			System.out.println(">>> 주문 완료");
			System.out.println(">>> 주문 시간: " + order.getOrderDateTime());
			System.out.println(">>> 주문 내역: " + order.getBeverages());
			System.out.println(">>> 총 가격: " + totalPrice + "원");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	
}