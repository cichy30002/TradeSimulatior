package app.valuables;

import app.controls.ControlPanel;
import app.exceptions.WrongValuableParamException;

import java.util.concurrent.ThreadLocalRandom;

public class Commodity extends Valuable{
    private static Integer tradingVolumeTotal;
    private final String tradingUnit;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer tradingVolume;

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
        this.tradingVolume = 0;
        ControlPanel.getInstance().addCommodity(this);
    }

    public static Integer getTradingVolumeTotal() {
        return Commodity.tradingVolumeTotal;
    }

    public static void setTradingVolumeTotal(Integer tradingVolumeTotal) {
        Commodity.tradingVolumeTotal = tradingVolumeTotal;
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
    public Integer getTradingVolume() {
        return tradingVolume;
    }

    public void resetTradingVolume() {
        this.tradingVolume = 0;
    }

    public void increaseTradingVolume(Integer amount)
    {
        this.tradingVolume+=amount;
    }

    /**
     * keeps track how many times given commodity was bought and all commodities were bought.
     * @param amount
     */
    @Override
    public void bought(Integer amount) {
        increaseTradingVolume(amount);
        setTradingVolumeTotal(getTradingVolumeTotal()+1);
    }
    /*
    @Override
    synchronized Integer calculateUpdatedPrice()
    {
        if(getTradingVolume() > getTradingVolumeTotal()/ControlPanel.getInstance().getAllCommodities().size())
        {
            return this.getPrice() + ThreadLocalRandom.current().nextInt(1, (int) (this.getPrice()*0.05));
        }else
        {
            return Math.max(1, this.getPrice() + ThreadLocalRandom.current().nextInt(-1*(int) (this.getPrice()*0.05), -1));
        }
    }
    */
}
