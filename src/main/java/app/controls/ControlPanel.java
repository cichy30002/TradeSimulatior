package app.controls;

import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.markets.Market;
import app.markets.StockMarket;
import app.valuables.Commodity;
import app.valuables.Currency;
import app.valuables.Index;
import app.valuables.Share;
import app.world.Company;
import app.world.InvestmentFound;
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
    private List<InvestmentFound> investmentFounds;
    private List<Commodity> commodities;
    private List<Currency> currencies;
    private List<Index> indexes;
    private List<Share> shares;

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
        return Objects.equals(market.getName(), name);
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
    public boolean investorExist(String name) {
        for (Investor investor: this.investors) {
            if(Objects.equals(investor.getName(), name))
            {
                return true;
            }
        }
        return false;
    }

    private boolean InvestmentFoundExist(String name) {
        for (InvestmentFound investmentFound: this.investmentFounds) {
            if(Objects.equals(investmentFound.getName(), name))
            {
                return true;
            }
        }
        return false;
    }
    public Boolean CompanyExist(String name)
    {
        return findCompanyByName(name) != null;
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

    public void addCurrency(Currency currency){
        if(!CurrencyExist(currency.getName()))
        {
            this.currencies.add(currency);
        }
    }

    public void addCommodity(Commodity commodity){
        if(!CommodityExist(commodity.getName()))
        {
            this.commodities.add(commodity);
        }
    }

    public void addInvestor(Investor investor)
    {
        if(!getInstance().investorExist(investor.getName())) {
            this.investors.add(investor);
        }
    }

    public void addCompany(Company company) {
        if(!getInstance().CompanyExist(company.getName()))
        {
            this.companies.add(company);
        }
    }

    public void addInvestmentFound(InvestmentFound investmentFound)
    {
        if(!getInstance().InvestmentFoundExist(investmentFound.getName()))
        {
            this.investmentFounds.add(investmentFound);
        }
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

    public void removeCurrency(Currency currency){
        this.currencies.remove(currency);
    }

    public void removeCommodity(Commodity commodity){
        this.commodities.remove(commodity);
    }

    public Currency getCurrency(String name)
    {
        for(Currency currency : this.currencies)
        {
            if(Objects.equals(currency.getName(), name))
            {
                return currency;
            }
        }
        return null;
    }
    public void forceBuyOut(){}

    public Company findCompanyByName(String name) {
        for (Company company: this.companies) {
            if(Objects.equals(company.getName(), name))
            {
                return company;
            }
        }
        return null;
    }



    public Commodity getCommodity(String name) {
        for(Commodity commodity : this.commodities)
        {
            if(Objects.equals(commodity.getName(), name))
            {
                return commodity;
            }
        }
        return null;
    }

    public Index getIndex(String name) {
        for(Index index : this.indexes)
        {
            if(Objects.equals(index.getName(), name))
            {
                return index;
            }
        }
        return null;
    }

    public Share getShare(String name) {
        for(Share share : this.shares)
        {
            if(Objects.equals(share.getName(), name))
            {
                return share;
            }
        }
        return null;
    }
}
