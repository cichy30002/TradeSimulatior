package app.world;

import app.controls.ControlPanel;
import app.exceptions.TransactionException;
import app.exceptions.WrongValuableParamException;
import app.valuables.Share;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Company extends MarketClient{
    private final String IPODate;
    private final Integer IPOShareValue;
    private Integer openingPrice;
    private Integer maxPrice;
    private Integer minPrice;
    private Float profit;
    private Float revenue;
    private Float capital;
    private Integer tradingVolume;
    private Integer totalSales;
    private Integer numberOfShares;
    private Integer soldShares;

    private final Share share;

    public Company(String name, String IPODate, Integer IPOShareValue, Integer openingPrice, Integer maxPrice,
                   Integer minPrice, Float profit, Float revenue, Float capital, Integer tradingVolume, Integer totalSales) throws WrongValuableParamException {
        super(name);
        this.IPODate = IPODate;
        this.IPOShareValue = IPOShareValue;
        this.openingPrice = openingPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.profit = profit;
        this.revenue = revenue;
        this.capital = capital;
        this.tradingVolume = tradingVolume;
        this.totalSales = totalSales;
        this.numberOfShares = new Random().nextInt(10, 50000);
        this.soldShares = 0;

        ControlPanel.getInstance().addCompany(this);
        this.share = new Share(this.getName(), this.IPOShareValue);
    }

    @Override
    protected Integer call() throws Exception {
        while(ControlPanel.getInstance().getSimulationState())
        {
            tryToMakeAction();
            randomSleep();
        }
        System.out.println(getName() +"died");
        return 0;
    }

    private void tryToMakeAction() {
        int randomActionID = ThreadLocalRandom.current().nextInt(10);
        if(randomActionID == 0)
        {
            increaseNumberOfShares();
        }else {
            tryToMakeTransaction();
        }
    }

    private void increaseNumberOfShares() {
        this.setNumberOfShares(this.getNumberOfShares() + ThreadLocalRandom.current().nextInt(100));
    }

    public String getIPODate() {
        return IPODate;
    }

    public Integer getIPOShareValue() {
        return IPOShareValue;
    }

    public Integer getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(Integer openingPrice) {
        this.openingPrice = openingPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Float getProfit() {
        return profit;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }

    public Float getRevenue() {
        return revenue;
    }

    public void setRevenue(Float revenue) {
        this.revenue = revenue;
    }

    public Float getCapital() {
        return capital;
    }

    public void setCapital(Float capital) {
        this.capital = capital;
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

    public Integer getTotalSales() {
        return totalSales;
    }

    public void resetTotalSales() {
        this.totalSales = 0;
    }

    public void increaseTotalSales(Integer amount)
    {
        this.totalSales += amount * getShare().getPrice();
    }

    public Share getShare() {
        return share;
    }

    public Integer getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(Integer numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public Integer getSoldShares() {
        return soldShares;
    }

    /**
     * Method toupdate how many shares were sold.
     * @param soldShares number of shares sold in given transaction.
     */
    public void increaseSoldShares(Integer soldShares) {
        this.soldShares += soldShares;
    }


}
