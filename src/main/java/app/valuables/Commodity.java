package app.valuables;

public class Commodity extends Valuable{
    private final String tradingUnit;
    private Integer minPrice;
    private Integer maxPrice;

    public Commodity(String name, Integer price, String tradingUnit, Integer minPrice, Integer maxPrice) {
        super(name, price);
        this.tradingUnit = tradingUnit;
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
