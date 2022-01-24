package app.world;

import app.controls.ControlPanel;
import app.exceptions.TransactionException;
import app.valuables.Currency;
import app.valuables.Share;


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
    private Float totalSales;

    private Share share;

    public Company(String name, String IPODate, Integer IPOShareValue, Integer openingPrice, Integer maxPrice,
                   Integer minPrice, Float profit, Float revenue, Float capital, Integer tradingVolume, Float totalSales) {
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

        ControlPanel.getInstance().addCompany(this);
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

    public void setTradingVolume(Integer tradingVolume) {
        this.tradingVolume = tradingVolume;
    }

    public Float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Float totalSales) {
        this.totalSales = totalSales;
    }
}
