package tddpractice.tddcafekiosk.unit.beverage;

public interface Beverage {

    String getName();

    int getPrice();
    
    int getCount();
    
    void addCount();
    
    void returnCount();
    
}
