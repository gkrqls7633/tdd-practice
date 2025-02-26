package tddpractice.tddcafekiosk.unit.beverage;

public class Americano implements Beverage{
	
	private int count = 1;
	
    @Override
    public String getName() {
        return "아메리카노";
    }

    @Override
    public int getPrice() {
        return 4000;
    }

	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	public void addCount() {
		this.count++;
	}

	@Override
	public void returnCount() {
		this.count = 1;
	}
}
