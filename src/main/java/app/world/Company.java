package app.world;

import app.controls.ControlPanel;
import app.valuables.Share;

import java.util.Date;

public class Company extends MarketClient{
    private Date IPODate;
    private Integer IPOShareValue;
    private Integer openingPrice;
    private Integer maxPrice;
    private Integer minPrice;
    private Share share;
    private Float profit;
    private Float revenue;
    private Float capital;
    private Integer tradingVolume;
    private Float totalSales;

    public Company(String name) {
        super(name);
        ControlPanel.getInstance().addCompany(this);
    }
}
