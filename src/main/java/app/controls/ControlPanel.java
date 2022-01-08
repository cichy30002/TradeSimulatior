package app.controls;

import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.markets.Market;
import app.markets.StockMarket;
import app.valuables.Commodity;
import app.valuables.Currency;
import app.world.Company;
import app.world.Investor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControlPanel {
    private static ControlPanel instance;
    private List<StockMarket> stockMarkets;
    private List<CurrencyMarket> currencyMarkets;
    private List<CommodityMarket> commodityMarkets;
    private Float bearBullRatio;
    private List<Investor> investors;
    private List<Company> companies;
    private List<Commodity> commodities;
    private List<Currency> currencies;

    public ControlPanel() {
        this.stockMarkets = new ArrayList<>();
        this.currencyMarkets = new ArrayList<>();
        this.commodityMarkets = new ArrayList<>();
        this.bearBullRatio = 1.0f;
        this.investors = new ArrayList<>();
        this.companies = new ArrayList<>();
        this.commodities = new ArrayList<>();
        this.currencies = new ArrayList<>();
    }

    public static ControlPanel getInstance()
    {
        if(ControlPanel.instance == null)
        {
            ControlPanel.instance = new ControlPanel();
        }
        return ControlPanel.instance;
    }

    public Boolean MarketExist(String name)
    {
        boolean result = false;
        for (Market market:stockMarkets) {
            if (SpecificMarketExist(name, market))
                result = true;
        }
        for (Market market:currencyMarkets) {
            if (SpecificMarketExist(name, market))
                result = true;
        }
        for (Market market:commodityMarkets) {
            if (SpecificMarketExist(name, market))
                result = true;
        }
        return result;
    }

    private Boolean SpecificMarketExist(String name,  Market market)
    {
        return market.getName() == name;
    }

    public Boolean CurrencyExist(String name)
    {
        for (Currency currency: this.currencies) {
            if(Objects.equals(currency.getName(), name))
            {
                return true;
            }
        }
        return false;
    }

    public Boolean CommodityExist(String name)
    {
        for (Commodity commodity: this.commodities) {
            if(Objects.equals(commodity.getName(), name))
            {
                return true;
            }
        }
        return false;
    }

    public void addStockMarket(StockMarket stockMarket){
        this.stockMarkets.add(stockMarket);
    }

    public void addCurrencyMarket(CurrencyMarket currencyMarket){
        this.currencyMarkets.add(currencyMarket);
    }

    public void addCommodityMarket(CommodityMarket commodityMarket){
        this.commodityMarkets.add(commodityMarket);
    }

    public void removeStockMarket(StockMarket stockMarket){
        this.stockMarkets.remove(stockMarket);
    }

    public void removeCurrencyMarket(CurrencyMarket currencyMarket){
        this.currencyMarkets.remove(currencyMarket);
    }

    public void removeCommodityMarket(CommodityMarket commodityMarket){
        this.commodityMarkets.remove(commodityMarket);
    }
    public void forceBuyOut(){}
}
