package app.controls;

import app.exceptions.AppInputException;
import app.exceptions.WrongMarketParamException;
import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.markets.StockMarket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Generator {
    private final Random RNG;
    private ArrayList<String> countries;
    private ArrayList<String> addresses;
    private HashMap<String, ArrayList<String>> cities;
    private float chanceForValuable;

    public Generator()
    {
        RNG = new Random();
        makeAddresses();
        chanceForValuable = 0.7f;
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
        checkInput(name, marginFee, currency);
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

    private void checkInput(String name, String marginFee, String currency) throws AppInputException {
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
        checkInput(name, marginFee, currency);
        float marginFeeFloat = Float.parseFloat(marginFee);
        ArrayList<String> listOfCurrencies = generateListOfValuable("Currency");
        try{
            CurrencyMarket currencyMarket = new CurrencyMarket(name, marginFeeFloat, currency, listOfCurrencies);
        }catch (WrongMarketParamException e)
        {
            System.out.println(e.getMessage());
        }
    }
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
        checkInput(name, marginFee, currency);
        float marginFeeFloat = Float.parseFloat(marginFee);
        ArrayList<String> listOfCommodities = generateListOfValuable("Commodity");
        try{
            CommodityMarket commodityMarket = new CommodityMarket(name, marginFeeFloat, currency, listOfCommodities);
        }catch (WrongMarketParamException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void generateInvestor(String name)throws AppInputException
    {
        System.out.println("making a new investor:" + name);
    }

    public void generateCompany(String name)throws AppInputException
    {
        System.out.println("making a new company:" + name);
    }

    public void generateInvestmentFound(String name)throws AppInputException
    {
        System.out.println("making a new investment found:" + name);
    }

    public void generateCurrency(String name, String price) throws AppInputException
    {
        System.out.println("making a new currency:" + name);
    }

    public void generateCommodity(String name, String price, String tradingUnit) throws AppInputException
    {
        System.out.println("making a new commodity:" + name);
    }

    public void generateIndex(String name, String price, String market, String noCompanies) throws AppInputException
    {
        System.out.println("making a new index:" + name);
    }
}
