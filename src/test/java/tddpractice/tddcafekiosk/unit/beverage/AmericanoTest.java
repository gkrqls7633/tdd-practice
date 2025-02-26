package tddpractice.tddcafekiosk.unit.beverage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/*
 * Amaricano class 를 테스트코드로 작성할 수 있다.
 * 간단한 entity class 이지만 충분히 테스트 할 가치가 있다.
 */
class AmericanoTest {

	@Test
	public void 여러잔_추가_기능_확인() {
		//given
		Americano initValTest = new Americano();
		Americano twoCups = new Americano();
		
		//when
		twoCups.addCount();
		
		//then
		assertEquals(initValTest.getCount(), 1);
		assertEquals(twoCups.getCount(), 2);
	}
}