package tddpractice.tddcafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tddpractice.tddcafekiosk.unit.beverage.Americano;
import tddpractice.tddcafekiosk.unit.beverage.Beverage;
import tddpractice.tddcafekiosk.unit.beverage.Latte;

import static org.junit.jupiter.api.Assertions.*;


/*
 * CafeKiosk 클래스의 각각의 메소드를 테스트 코드로 작성해야한다.
 * 실패 케이스를 먼저 고려하여 테스트 코드를 작성하는 습관을 들여보자.
 */

class CafeKioskTest {
	
	CafeKiosk kiosk = new CafeKiosk();

    @DisplayName("키오스크 주문 목록에 상품을 추가한다.")
    @Test
    public void add() {
    	// given
    	Americano ame = new Americano();
    	Latte lat = new Latte();
    	
    	// when
    	this.kiosk.add(ame);
    	this.kiosk.add(lat);
    	
    	// then
    	assertEquals(this.kiosk.getBeverages().size(), 2);
    	assertTrue(this.kiosk.getBeverages().contains(ame));
    	assertTrue(this.kiosk.getBeverages().contains(lat));
    }

}