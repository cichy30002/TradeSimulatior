package app.valuables;

import app.exceptions.wrongValuableParamException;

public class Commodity extends Valuable{
    private final String tradingUnit;
    private Integer minPrice;
    private Integer maxPrice;

    public Commodity(String name, Integer price, String tradingUnit, Integer minPrice, Integer maxPrice) throws wrongValuableParamException {
        super(name, price);
        if(tradingUnit.length() == 0 || tradingUnit.length()>20){
            throw new wrongValuableParamException("Wrong trading unit: " + tradingUnit);
        }
        this.tradingUnit = tradingUnit;
        if(minPrice <= 0 || maxPrice <= 0 || minPrice > maxPrice)
        {
            throw new wrongValuableParamException("Wrong min/max price: " + minPrice + " " + maxPrice);
        }
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getTradingUnit() {
        return tradingUnit;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }
}
