package tddpractice.tddcafekiosk.unit.beverage;

public class Latte implements Beverage{
	
	private int count = 1;
	
    @Override
    public String getName() {
        return "라떼";
    }

    @Override
    public int getPrice() {
        return 4500;
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
