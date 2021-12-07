package app.controls;

import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.markets.StockMarket;
import app.valuables.Currency;
import app.world.Company;
import app.world.Investor;

import java.util.List;

public class ControlPanel {
    private static ControlPanel instance;
    private List<StockMarket> stockMarkets;
    private List<CurrencyMarket> currencyMarkets;
    private List<CommodityMarket> commodityMarkets;
    private Float bearBullRatio;
    private List<Investor> investors;
    private List<Company> companies;

    public void forceBuyOut(){}
}
