package app.world;

import app.controls.ControlPanel;
import app.exceptions.TransactionException;
import app.valuables.Currency;
import app.valuables.Share;


public class Company extends MarketClient{
    private String IPODate;
    private Integer IPOShareValue;
    private Integer openingPrice;
    private Integer maxPrice;
    private Integer minPrice;
    private Float profit;
    private Float revenue;
    private Float capital;
    private Integer tradingVolume;
    private Float totalSales;

    private Share share;

    public Company(String name, String IPODate, Integer IPOShareValue, Integer openingPrice, Integer maxPrice, Integer minPrice, Float profit, Float revenue, Float capital, Integer tradingVolume, Float totalSales) {
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
    public void addFunds(String currencyName, Integer amount) throws TransactionException {
        this.addToWallet(currencyName, amount);
    }
}
