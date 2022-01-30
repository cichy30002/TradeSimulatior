package app.controls;

import app.exceptions.AppInputException;
import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.markets.Market;
import app.markets.StockMarket;
import app.valuables.*;
import app.world.Company;
import app.world.InvestmentFund;
import app.world.Investor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ControlPanel {
    private static ControlPanel instance;
    private final HashMap<String, StockMarket> stockMarkets;
    private final HashMap<String, CurrencyMarket> currencyMarkets;
    private final HashMap<String, CommodityMarket> commodityMarkets;
    private final ObservableList<String> marketNames;
    private volatile Float bullBearRatio;
    private volatile Integer transactionsPerSecond;
    private final HashMap<String, Investor> investors;
    private final ObservableList<String> investorNames;
    private final HashMap<String, Company> companies;
    private final ObservableList<String> companyNames;
    private final HashMap<String, InvestmentFund> investmentFunds;
    private final ObservableList<String> investmentFundNames;
    private final HashMap<String, Commodity> commodities;
    private final ObservableList<String> commodityNames;
    private final HashMap<String, Currency> currencies;
    private final ObservableList<String> currencyNames;
    private final HashMap<String, Index> indexes;
    private final ObservableList<String> indexNames;
    private final HashMap<String, Share> shares;
    private final ObservableList<String> shareNames;
    private final Generator generator;
    private final Simulation simulation;
    private Boolean simulationState;

    public ControlPanel() {
        this.stockMarkets = new HashMap<>();
        this.currencyMarkets = new HashMap<>();
        this.commodityMarkets = new HashMap<>();
        this.marketNames = FXCollections.observableArrayList();

        this.bullBearRatio = 0.3f;
        this.transactionsPerSecond = 5;

        this.investors = new HashMap<>();
        this.investorNames = FXCollections.observableArrayList();
        this.companies = new HashMap<>();
        this.companyNames = FXCollections.observableArrayList();
        this.investmentFunds = new HashMap<>();
        this.investmentFundNames = FXCollections.observableArrayList();

        this.commodities = new HashMap<>();
        this.commodityNames = FXCollections.observableArrayList();
        this.currencies = new HashMap<>();
        this.currencyNames = FXCollections.observableArrayList();
        this.indexes = new HashMap<>();
        this.indexNames = FXCollections.observableArrayList();
        this.shares = new HashMap<>();
        this.shareNames = FXCollections.observableArrayList();

        this.generator = new Generator();
        this.simulation = new Simulation();
        this.simulationState = false;
    }

    public static ControlPanel getInstance()
    {
        if(ControlPanel.instance == null)
        {
            ControlPanel.instance = new ControlPanel();
        }
        return ControlPanel.instance;
    }

    public boolean marketExist(String name)
    {
        return stockMarkets.containsKey(name) || currencyMarkets.containsKey(name) || commodityMarkets.containsKey(name);
    }
    public boolean valuableExist(String name)
    {
        return commodities.containsKey(name) || currencies.containsKey(name) || indexes.containsKey(name) || shares.containsKey(name);
    }

    public boolean currencyExist(String name)
    {
        return currencies.containsKey(name);
    }

    public boolean commodityExist(String name)
    {
        return commodities.containsKey(name);
    }
    public boolean investorExist(String name) {
        return investors.containsKey(name);
    }

    public boolean investmentFundExist(String name) {
        return investmentFunds.containsKey(name);
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
        this.marketNames.add(stockMarket.getName());
    }

    public void addCurrencyMarket(CurrencyMarket currencyMarket){
        this.currencyMarkets.put(currencyMarket.getName(), currencyMarket);
        this.marketNames.add(currencyMarket.getName());
    }

    public void addCommodityMarket(CommodityMarket commodityMarket){
        this.commodityMarkets.put(commodityMarket.getName(), commodityMarket);
        this.marketNames.add(commodityMarket.getName());
    }

    public void addCurrency(Currency currency){
        if(!currencyExist(currency.getName()))
        {
            this.currencies.put(currency.getName(), currency);
            this.currencyNames.add(currency.getName());
        }
    }

    public void addCommodity(Commodity commodity){
        if(!commodityExist(commodity.getName()))
        {
            this.commodities.put(commodity.getName(), commodity);
            this.commodityNames.add(commodity.getName());
        }
    }

    public void addInvestor(Investor investor)
    {
        if(!investorExist(investor.getName())) {
            this.investors.put(investor.getName(), investor);
            this.investorNames.add(investor.getName());
        }
    }

    public void addCompany(Company company) {
        if(!companyExist(company.getName()))
        {
            this.companies.put(company.getName(), company);
            this.companyNames.add(company.getName());
        }
    }

    public void addInvestmentFund(InvestmentFund investmentFund)
    {
        if(!investmentFundExist(investmentFund.getName()))
        {
            this.investmentFunds.put(investmentFund.getName(), investmentFund);
            this.investmentFundNames.add(investmentFund.getName());
        }
    }

    public void addIndex(Index index) {
        if(!getInstance().indexExist(index.getName()))
        {
            this.indexes.put(index.getName(), index);
            this.indexNames.add(index.getName());
        }
    }

    public void addShare(Share share) {
        if(!getInstance().shareExist(share.getName()))
        {
            this.shares.put(share.getName(), share);
            this.shareNames.add(share.getName());
        }
    }


    public Valuable getValuable(String name) {
        if(currencyExist(name))
        {
            return currencies.get(name);
        }
        if(commodityExist(name))
        {
            return commodities.get(name);
        }
        if(indexExist(name))
        {
            return indexes.get(name);
        }
        if(shareExist(name))
        {
            return shares.get(name);
        }
        return null;
    }
    public Currency getCurrency(String name)
    {
        return currencies.get(name);
    }

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
        this.marketNames.remove(stockMarketName);
    }

    public void removeCurrencyMarket(String currencyMarketName){
        this.currencyMarkets.remove(currencyMarketName);
        this.marketNames.remove(currencyMarketName);
    }

    public void removeCommodityMarket(String commodityMarketName){
        this.commodityMarkets.remove(commodityMarketName);
        this.marketNames.remove(commodityMarketName);
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
        this.companyNames.remove(companyName);
    }

    public void removeInvestor(String investorName) {
        this.investors.remove(investorName);
        this.investorNames.remove(investorName);
    }

    public void removeInvestmentFund(String investmentFundName)
    {
        this.investmentFunds.remove(investmentFundName);
        this.investmentFundNames.remove(investmentFundName);
    }

    public ArrayList<String> getAllMarketNames() {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(currencyMarkets.keySet());
        result.addAll(commodityMarkets.keySet());
        result.addAll(stockMarkets.keySet());
        return result;
    }

    public ArrayList<String> getAllInvestorNames() {
        return new ArrayList<>(investors.keySet());
    }

    public ArrayList<String> getAllCompanyNames() {
        return new ArrayList<>(companies.keySet());
    }

    public ArrayList<String> getAllInvestmentFundNames() {
        return new ArrayList<>(investmentFunds.keySet());
    }

    public Market getMarket(String marketName) {
        if(currencyMarkets.containsKey(marketName))
        {
            return currencyMarkets.get(marketName);
        }
        if(commodityMarkets.containsKey(marketName))
        {
            return  commodityMarkets.get(marketName);
        }
        if(stockMarkets.containsKey(marketName))
        {
            return stockMarkets.get(marketName);
        }
        return null;
    }

    public Investor getInvestor(String investorName) {
        return investors.get(investorName);
    }

    public InvestmentFund getInvestmentFund(String investmentFundName) {
        return investmentFunds.get(investmentFundName);
    }
    public StockMarket getStockMarket(String market) {
        return this.stockMarkets.get(market);
    }
    public void setTransactionsPerSecond(String value) throws AppInputException {
        int transactions;
        try{
            transactions = Integer.parseInt(value);
        }catch(NumberFormatException e)
        {
            throw new AppInputException("Input is not a correct number!");
        }
        if(transactions <= 0 || transactions >= 40)
        {
            throw new AppInputException("Number is out of range (0, 40)");
        }
        this.transactionsPerSecond = transactions;
    }

    public Integer getTransactionsPerSecond()
    {
        return this.transactionsPerSecond;
    }
    public void setBullBearRatio(String value) throws AppInputException
    {
        float ratio;
        try {
            ratio = Float.parseFloat(value);
        }catch(NullPointerException | NumberFormatException e2)
        {
            throw new AppInputException("Input is not a correct number!");
        }
        if(ratio < -1f || ratio > 1f)
        {
            throw new AppInputException("Number is out of range (-1, 1)");
        }
        this.bullBearRatio = ratio;
    }

    public Float getBullBearRatio() {
        return this.bullBearRatio;
    }

    public Generator getGenerator() {
        return generator;
    }

    public ArrayList<String> getAllCurrencies() {
        return new ArrayList<>(currencies.keySet());
    }
    public ArrayList<String> getAllCommodities() {
        return new ArrayList<>(commodities.keySet());
    }
    public ArrayList<String> getAllShares() {
        return new ArrayList<>(shares.keySet());
    }
    public ArrayList<String> getAllIndexes() {
        return new ArrayList<>(indexes.keySet());
    }

    public ArrayList<String> getAllValuables() {
        ArrayList<String> result = new ArrayList<>(getAllCurrencies());
        result.addAll(getAllCommodities());
        result.addAll(getAllIndexes());
        result.addAll(getAllShares());
        return result;
    }
    public ObservableList<String> getMarketNames() {
        return this.marketNames;
    }

    public ObservableList<String> getInvestorNames() {
        return investorNames;
    }

    public ObservableList<String> getCompanyNames() {
        return companyNames;
    }

    public ObservableList<String> getInvestmentFundNames() {
        return investmentFundNames;
    }

    public ObservableList<String> getShareNames() {
        return shareNames;
    }

    public ObservableList<String> getIndexNames() {
        return indexNames;
    }

    public ObservableList<String> getCurrencyNames() {
        return currencyNames;
    }

    public ObservableList<String> getCommodityNames() {
        return commodityNames;
    }

    public Collection<Investor> getAllInvestors() {
        return this.investors.values();
    }

    public Collection<Company> getAllCompanies() {
        return this.companies.values();
    }

    public Collection<InvestmentFund> getAllInvestmentFunds() {
        return this.investmentFunds.values();
    }

    public Boolean getSimulationState() {
        return simulationState;
    }

    public void setSimulationState(Boolean simulationState) {
        this.simulationState = simulationState;
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    /**
     * @return return number of market clients (investors, companies and funds) in simulation
     */
    public int countMarketClients() {
        return investors.keySet().size() + companies.keySet().size() + investmentFunds.keySet().size();
    }
}
