package app.valuables;

import app.controls.ControlPanel;
import app.exceptions.WrongValuableParamException;

public class Commodity extends Valuable{
    private final String tradingUnit;
    private Integer minPrice;
    private Integer maxPrice;

    public Commodity(String name, Integer price, String tradingUnit, Integer minPrice, Integer maxPrice) throws WrongValuableParamException {
        super(name, price);
        if(tradingUnit.length() == 0 || tradingUnit.length()>20){
            throw new WrongValuableParamException("Wrong trading unit: " + tradingUnit);
        }
        this.tradingUnit = tradingUnit;
        if(minPrice <= 0 || maxPrice <= 0 || minPrice > maxPrice)
        {
            throw new WrongValuableParamException("Wrong min/max price: " + minPrice + " " + maxPrice);
        }
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        ControlPanel.getInstance().addCommodity(this);
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
