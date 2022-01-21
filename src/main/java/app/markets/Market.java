package app.markets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.controls.ControlPanel;
import app.exceptions.MarketCollectionException;
import app.exceptions.WrongMarketParamException;

public abstract class Market {
    private final String name;
    private float marginFee;
    private final String currency;
    protected HashMap<String, Integer> productsWithPrices;
    protected Market(String name, float marginFee, String currency) throws WrongMarketParamException {
        if(ControlPanel.getInstance().marketExist(name))
        {
            throw new WrongMarketParamException("That market already exist!");
        }
        if(name.length()==0 || name.length()>20)
        {
            throw new WrongMarketParamException("Wrong markets name");
        }
        this.name = name;
        if(marginFee < 0f)
        {
            throw new WrongMarketParamException("Wrong market fee value");
        }
        this.marginFee = marginFee;
        if(!ControlPanel.getInstance().currencyExist(currency))
        {
            throw new WrongMarketParamException("Currency " + currency + " does not exist!");
        }
        this.currency = currency;
        this.productsWithPrices = new HashMap<>();
    }


    public String getName(){
        return this.name;
    }


    public String getCurrency() {
        return currency;
    }
    protected List<Integer> parseList(List<String> listOfStrings)
    {
        List<Integer> result = new ArrayList<>();
        for(String s : listOfStrings)
        {
            result.add(Integer.parseInt(s));
        }
        return result;
    }
    protected void deepAdd(String toAdd, Integer price) throws MarketCollectionException {
        if(productsWithPrices.containsKey(toAdd))
        {
            throw new MarketCollectionException("Tred to add currency that already is in the market");
        }
        productsWithPrices.put(toAdd, price);
    }

    protected void deepRemove(String toRemoveName) throws MarketCollectionException {
        if(!ControlPanel.getInstance().currencyExist(toRemoveName))
        {
            throw new MarketCollectionException("Tried to remove currency which does not exist: "+ toRemoveName);
        }
        if(!this.productsWithPrices.containsKey(toRemoveName))
        {
            throw new MarketCollectionException("Tried to remove currency which is not in the market: " + toRemoveName);
        }
        productsWithPrices.remove(toRemoveName);
    }

    public boolean isProductInMarket(String valuableName)
    {
        return productsWithPrices.containsKey(valuableName);
    }

    public Integer getProductPrice(String productName) throws MarketCollectionException {
        if(!isProductInMarket(productName))
        {
            throw new MarketCollectionException("Tried to get price of product that is ont in the market: " + productName);
        }
        return productsWithPrices.get(productName);
    }
}
