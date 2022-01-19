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
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ControlPanel {
    private static ControlPanel instance;
    private HashMap<String, StockMarket> stockMarkets;
    private HashMap<String, CurrencyMarket> currencyMarkets;
    private HashMap<String, CommodityMarket> commodityMarkets;
    private Float bearBullRatio;
    private HashMap<String, Investor> investors;
    private HashMap<String, Company> companies;
    private HashMap<String, InvestmentFound> investmentFounds;
    private HashMap<String, Commodity> commodities;
    private HashMap<String, Currency> currencies;
    private HashMap<String, Index> indexes;
    private HashMap<String, Share> shares;

    public ControlPanel() {
        this.stockMarkets = new HashMap<>();
        this.currencyMarkets = new HashMap<>();
        this.commodityMarkets = new HashMap<>();
        this.bearBullRatio = 1.0f;
        this.investors = new HashMap<>();
        this.companies = new HashMap<>();
        this.commodities = new HashMap<>();
        this.currencies = new HashMap<>();
        this.indexes = new HashMap<>();
        this.shares = new HashMap<>();
        this.investmentFounds = new HashMap<>();
    }

    public static ControlPanel getInstance()
    {
        if(ControlPanel.instance == null)
        {
            ControlPanel.instance = new ControlPanel();
        }
        return ControlPanel.instance;
    }

    public Boolean marketExist(String name)
    {
        return stockMarkets.containsKey(name) || currencyMarkets.containsKey(name) || commodityMarkets.containsKey(name);
    }

    public Boolean currencyExist(String name)
    {
        return currencies.containsKey(name);
    }

    public Boolean commodityExist(String name)
    {
        return commodities.containsKey(name);
    }
    public boolean investorExist(String name) {
        return investors.containsKey(name);
    }

    public boolean investmentFoundExist(String name) {
        return investmentFounds.containsKey(name);
    }
    public boolean companyExist(String name)
    {
        return companies.containsKey(name);
    }
    public boolean indexExist(String name) {
        return indexes.containsKey(name);
    }
    public boolean shareExist(String name) {
        return shares.containsKey(name);
    }
    public void addStockMarket(StockMarket stockMarket){
        this.stockMarkets.put(stockMarket.getName(), stockMarket);
    }

    public void addCurrencyMarket(CurrencyMarket currencyMarket){
        this.currencyMarkets.put(currencyMarket.getName(), currencyMarket);
    }

    public void addCommodityMarket(CommodityMarket commodityMarket){
        this.commodityMarkets.put(commodityMarket.getName(), commodityMarket);
    }

    public void addCurrency(Currency currency){
        if(!currencyExist(currency.getName()))
        {
            this.currencies.put(currency.getName(), currency);
        }
    }

    public void addCommodity(Commodity commodity){
        if(!commodityExist(commodity.getName()))
        {
            this.commodities.put(commodity.getName(), commodity);
        }
    }

    public void addInvestor(Investor investor)
    {
        if(!investorExist(investor.getName())) {
            this.investors.put(investor.getName(), investor);
        }
    }

    public void addCompany(Company company) {
        if(!companyExist(company.getName()))
        {
            this.companies.put(company.getName(), company);
        }
    }

    public void addInvestmentFound(InvestmentFound investmentFound)
    {
        if(!investmentFoundExist(investmentFound.getName()))
        {
            this.investmentFounds.put(investmentFound.getName(), investmentFound);
        }
    }

    public void addIndex(Index index) {
        if(!getInstance().indexExist(index.getName()))
        {
            this.indexes.put(index.getName(), index);
        }
    }

    public void addShare(Share share) {
        if(!getInstance().shareExist(share.getName()))
        {
            this.shares.put(share.getName(), share);
        }
    }


    public Currency getCurrency(String name)
    {
        return currencies.get(name);
    }
    public void forceBuyOut(){}

    public Company getCompany(String name) {
        return companies.get(name);
    }

    public Commodity getCommodity(String name) {
        return commodities.get(name);
    }

    public Index getIndex(String name) {
        return indexes.get(name);
    }

    public Share getShare(String name) {
        return shares.get(name);
    }

    public static void removeInstance()
    {
        instance = null;
    }

    public void removeStockMarket(String stockMarketName){
        this.stockMarkets.remove(stockMarketName);
    }

    public void removeCurrencyMarket(String currencyMarketName){
        this.currencyMarkets.remove(currencyMarketName);
    }

    public void removeCommodityMarket(String commodityMarketName){
        this.commodityMarkets.remove(commodityMarketName);
    }

    public void removeCurrency(String currencyName){
        this.currencies.remove(currencyName);
    }

    public void removeCommodity(String commodityName){
        this.commodities.remove(commodityName);
    }
    public void removeShare(String shareName) {
        this.shares.remove(shareName);
    }

    public void removeIndex(String indexName) {
        this.indexes.remove(indexName);
    }

    public void removeCompany(String companyName) {
        this.companies.remove(companyName);
    }

    public void removeInvestor(String investorName) {
        this.investors.remove(investorName);
    }

    public void removeInvestmentFound(String investmentFoundName)
    {
        this.investmentFounds.remove(investmentFoundName);
    }
}
