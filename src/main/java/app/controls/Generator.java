package app.controls;

import app.exceptions.AppInputException;
import app.exceptions.TransactionException;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.markets.StockMarket;
import app.valuables.Commodity;
import app.valuables.Currency;
import app.valuables.Index;
import app.valuables.Share;
import app.world.Company;
import app.world.InvestmentFund;
import app.world.Investor;
import app.world.MarketClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    private final Random RNG;
    private ArrayList<String> countries;
    private ArrayList<String> addresses;
    private HashMap<String, ArrayList<String>> cities;
    private final float chanceForValuable;
    private final int maxWalletAmount;
    private final float chanceForWallet;
    private final float fundChance;
    private int assetCounter;

    public Generator()
    {
        RNG = new Random();
        makeAddresses();
        chanceForValuable = 0.7f;
        chanceForWallet = 0.3f;
        fundChance = 0.5f;
        maxWalletAmount = 10000;
        assetCounter = 0;
    }

    private void makeAddresses() {
        countries = new ArrayList<>();
        //countries.addAll(Arrays.asList("Poland", "UK", "Belgium", "Switzerland", "Germany", "France", "USA"));
        countries.add("Poland");
        addresses = new ArrayList<>();
        addresses.add("laczna 43");
        cities = new HashMap<>();
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("Lipinki Luzyckie");
        cities.put("Poland", tmp);
    }

    /**
     * Generate new semi-random stock market.
     * @param name
     * @param marginFee
     * @param currency
     * @throws AppInputException
     */
    public void generateStockMarket(String name, String marginFee, String currency) throws AppInputException
    {
        if(marginFee.length() == 0)
        {
            marginFee = String.valueOf(RNG.nextFloat()/2);
        }
        if(currency.length() == 0)
        {
            currency = ControlPanel.getInstance().getAllCurrencies().get(RNG.nextInt(ControlPanel.getInstance().getAllCurrencies().size()));
        }
        checkInputMarket(name, marginFee, currency);
        float marginFeeFloat = Float.parseFloat(marginFee);
        ArrayList<String> listOfShares = generateListOfValuable("Share");
        String country = generateCountry();
        String city = generateCity(country);
        String address = generateAddress(city);
        ArrayList<String> listOfIndexes = generateListOfValuable("Index");
        try {
            StockMarket stockMarket = new StockMarket(name, marginFeeFloat, currency, listOfShares, country, city, address, listOfIndexes);
        } catch (WrongMarketParamException e) {
            e.printStackTrace();
        }
    }

    private void checkInputMarket(String name, String marginFee, String currency) throws AppInputException {
        if(ControlPanel.getInstance().marketExist(name))
        {
            throw new AppInputException("That market already exist!", "name");
        }
        if(name.length()==0 || name.length()>20)
        {
            throw new AppInputException("Wrong markets name", "name");
        }
        float marginFeeFloat;
        try {
            marginFeeFloat = Float.parseFloat(marginFee);
        }catch (NullPointerException | NumberFormatException e)
        {
            throw new AppInputException("Margin fee is not a number!", "marginFee");
        }
        if(marginFeeFloat < 0f)
        {
            throw new AppInputException("Wrong market fee value", "marginFee");
        }
        if(!ControlPanel.getInstance().currencyExist(currency))
        {
            throw new AppInputException("Currency " + currency + " does not exist!", "currency");
        }
    }

    private String generateAddress(String city) {
        return addresses.get(RNG.nextInt(addresses.size()));
    }

    private String generateCity(String country) {
        return cities.get(country).get(RNG.nextInt(cities.get(country).size()));
    }

    private String generateCountry() {
        return countries.get(RNG.nextInt(countries.size()));
    }

    private ArrayList<String> generateListOfValuable(String type) {
        ArrayList<String> valuables;
        switch (type)
        {
            case "Currency" -> valuables = ControlPanel.getInstance().getAllCurrencies();
            case "Commodity" -> valuables = ControlPanel.getInstance().getAllCommodities();
            case "Share" -> valuables = ControlPanel.getInstance().getAllShares();
            case "Index" -> valuables = ControlPanel.getInstance().getAllIndexes();
            default -> throw new Error("wrong generate list of valuable params");
        }
        ArrayList<String> result = new ArrayList<>();
        for(String valuable : valuables)
        {
            if(chanceForValuable > RNG.nextFloat())
            {
                result.add(valuable);
            }
        }
         return result;
    }

    /**
     * Generate new semi-random currency market.
     * @param name
     * @param marginFee
     * @param currency
     * @throws AppInputException
     */
    public void generateCurrencyMarket(String name, String marginFee, String currency) throws AppInputException
    {
        if(marginFee.length() == 0)
        {
            marginFee = String.valueOf(RNG.nextFloat()/2);
        }
        if(currency.length() == 0)
        {
            currency = ControlPanel.getInstance().getAllCurrencies().get(RNG.nextInt(ControlPanel.getInstance().getAllCurrencies().size()));
        }
        checkInputMarket(name, marginFee, currency);
        float marginFeeFloat = Float.parseFloat(marginFee);
        ArrayList<String> listOfCurrencies = generateListOfValuable("Currency");
        try{
            CurrencyMarket currencyMarket = new CurrencyMarket(name, marginFeeFloat, currency, listOfCurrencies);
        }catch (WrongMarketParamException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generate new semi-random commodity market.
     * @param name
     * @param marginFee
     * @param currency
     * @throws AppInputException
     */
    public void generateCommodityMarket(String name, String marginFee, String currency) throws AppInputException
    {
        if(marginFee.length() == 0)
        {
            marginFee = String.valueOf(RNG.nextFloat()/2);
        }
        if(currency.length() == 0)
        {
            currency = ControlPanel.getInstance().getAllCurrencies().get(RNG.nextInt(ControlPanel.getInstance().getAllCurrencies().size()));
        }
        checkInputMarket(name, marginFee, currency);
        float marginFeeFloat = Float.parseFloat(marginFee);
        ArrayList<String> listOfCommodities = generateListOfValuable("Commodity");
        try{
            CommodityMarket commodityMarket = new CommodityMarket(name, marginFeeFloat, currency, listOfCommodities);
        }catch (WrongMarketParamException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generate new semi-random investor.
     * @param name
     * @throws AppInputException
     */
    public void generateInvestor(String name)throws AppInputException
    {
        if(ControlPanel.getInstance().investorExist(name))
        {
            throw new AppInputException("That investor already exist!");
        }
        if(name.length()==0 || name.length()>20)
        {
            throw new AppInputException("Wrong investors name");
        }
        Investor investor = new Investor(name);
        fillMarketClientWallet(investor);
        ControlPanel.getInstance().getSimulation().simulateNewTask(investor);
    }

    protected void fillMarketClientWallet(MarketClient client) {
        for(String currency : ControlPanel.getInstance().getAllCurrencies())
        {
            if(RNG.nextFloat()<chanceForWallet)
            {
                try {
                    client.addFunds(currency, RNG.nextInt(1,maxWalletAmount));
                } catch (TransactionException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Generate new semi-random company.
     * @param name
     * @throws AppInputException
     */
    public void generateCompany(String name)throws AppInputException
    {
        if(ControlPanel.getInstance().companyExist(name))
        {
            throw new AppInputException("That company already exist!");
        }
        if(name.length()==0 || name.length()>20)
        {
            throw new AppInputException("Wrong company name");
        }
        Company company = generateCompanyWithRandomParams(name);
        fillMarketClientWallet(company);
        ControlPanel.getInstance().getSimulation().simulateNewTask(company);
    }

    private Company generateCompanyWithRandomParams(String name) throws AppInputException{
        String ipoDate = "11.09.2001";
        int ipoShareValue = RNG.nextInt(100, 10000);
        int openingPrice = (int) Math.ceil((0.5f+ RNG.nextFloat()) * ipoShareValue);
        Integer minPrice = (int) Math.ceil((1f- RNG.nextFloat()/4) * openingPrice);
        Integer maxPrice = (int) Math.ceil((1f+ RNG.nextFloat()/4) * openingPrice);
        Float profit = 2137f;
        Float revenue = 2248f;
        Float capital = 123456f;
        Integer tradingVolume = RNG.nextInt(100) * 100;
        Integer totalSales = 456789;
        increaseAssetCounter();
        try {
            return new Company(name, ipoDate, ipoShareValue, openingPrice, minPrice, maxPrice, profit, revenue,
                    capital, tradingVolume, totalSales);
        }catch (WrongValuableParamException e)
        {
            throw new AppInputException(e.getMessage());
        }

    }

    /**
     * Generate new semi-random investment fund.
     * @param name
     * @throws AppInputException
     */
    public void generateInvestmentFund(String name)throws AppInputException
    {
        if(ControlPanel.getInstance().investmentFundExist(name))
        {
            throw new AppInputException("That investment fund already exist!");
        }
        if(name.length()==0 || name.length()>20)
        {
            throw new AppInputException("Wrong investment fund name");
        }
        InvestmentFund investmentFund = generateInvestmentFundWithRandomParams(name);
        ControlPanel.getInstance().getSimulation().simulateNewTask(investmentFund);
    }

    private InvestmentFund generateInvestmentFundWithRandomParams(String name) {
        String managerName = "mr.";
        String managerSurname = "Manager";

        return new InvestmentFund(name, managerName, managerSurname);
    }

    /**
     * Generate new semi-random currency.
     * @param name
     * @param price
     * @throws AppInputException
     */
    public void generateCurrency(String name, String price) throws AppInputException
    {
        if(ControlPanel.getInstance().currencyExist(name))
        {
            throw new AppInputException("That currency already exist!", "name");
        }
        if(name.length()==0 || name.length()>20)
        {
            throw new AppInputException("Wrong currency name", "name");
        }
        int priceInt = checkPrice(price);

        ArrayList<String> countries = new ArrayList<>();
        countries.add(generateCountry());
        try {
            Currency currency = new Currency(name, priceInt, countries);
        } catch (WrongValuableParamException e) {
            e.printStackTrace();
        }
        increaseAssetCounter();
    }

    private int checkPrice(String price) throws AppInputException{
        int result;
        if(price.length()==0)
        {
            return RNG.nextInt(1000);
        }
        try {
            result = Integer.parseInt(price);
        }catch (NullPointerException | NumberFormatException e)
        {
            throw new AppInputException("Price is not a number!", "price");
        }
        if(result <= 0)
        {
            throw new AppInputException("Wrong price value", "price");
        }
        return result;
    }

    /**
     * Generate new semi-random commodity.
     * @param name
     * @param price
     * @param tradingUnit
     * @throws AppInputException
     */
    public void generateCommodity(String name, String price, String tradingUnit) throws AppInputException
    {
        if(ControlPanel.getInstance().commodityExist(name))
        {
            throw new AppInputException("That commodity already exist!", "name");
        }
        if(name.length()==0 || name.length()>20)
        {
            throw new AppInputException("Wrong commodity name", "name");
        }
        int priceInt = checkPrice(price);
        if(tradingUnit.length()==0 || tradingUnit.length()>20)
        {
            throw new AppInputException("Wrong trading unit", "tradingUnit");
        }
        Integer minPrice = (int) (priceInt * (1f - RNG.nextFloat()/2));
        Integer maxPrice = (int) (priceInt * (1f + RNG.nextFloat()/2));
        try {
            Commodity commodity = new Commodity(name, priceInt, tradingUnit, minPrice, maxPrice);
        } catch (WrongValuableParamException e) {
            System.out.println("Failed commodity generation " + e.getMessage());
        }
        increaseAssetCounter();
    }

    /**
     * Generate new semi-random index.
     * @param name
     * @param market
     * @param noCompanies
     * @throws AppInputException
     */
    public void generateIndex(String name, String market, String noCompanies) throws AppInputException
    {
        if(ControlPanel.getInstance().indexExist(name))
        {
            throw new AppInputException("That index already exist!", "name");
        }
        if(name.length()==0 || name.length()>20)
        {
            throw new AppInputException("Wrong index name", "name");
        }
        int priceInt = 1;
        ArrayList<String> listOfCompaniesNames = new ArrayList<>();
        StockMarket stockMarket = ControlPanel.getInstance().getStockMarket(market);
        if(stockMarket==null)
        {
            throw new AppInputException("Wrong market name!", "market");
        }
        int noCompaniesInt;
        try {
            noCompaniesInt = Integer.parseInt(noCompanies);
        }catch (NumberFormatException e)
        {
            throw new AppInputException("Number of companies is not a number!", "noCompanies");
        }
        ArrayList<Share> listOfShares = new ArrayList<>(stockMarket.getListOfShares());
        if(listOfShares.size()/2 < noCompaniesInt)
        {
            throw new AppInputException("Wrong number of companies! (max 50% of markets companies)", "noCompanies");
        }
        int i = 0;
        while(listOfCompaniesNames.size() < noCompaniesInt)
        {
            if(RNG.nextFloat() < (float)noCompaniesInt/listOfShares.size())
            {
                listOfCompaniesNames.add(listOfShares.get(i).getCompany().getName());
            }
            i++;
            if(i==listOfShares.size())
            {
                i=0;
            }
        }
        try {
            Index index = new Index(name, priceInt, listOfCompaniesNames);
        } catch (WrongValuableParamException e) {
            System.out.println("Failed commodity generation " + e.getMessage());
        }
        increaseAssetCounter();
    }
    private void increaseAssetCounter()
    {
        this.assetCounter++;
        if(this.assetCounter%9 == 0)
        {
            try {
                generateInvestmentFund("automaticFound" + ThreadLocalRandom.current().nextInt(1000));
            } catch (AppInputException e) {
                e.printStackTrace();
            }
        }else if(this.assetCounter%3 == 0)
        {
            try {
                generateInvestor("automaticInvestor" + ThreadLocalRandom.current().nextInt(1000));
            } catch (AppInputException e) {
                e.printStackTrace();
            }
        }
    }
}
