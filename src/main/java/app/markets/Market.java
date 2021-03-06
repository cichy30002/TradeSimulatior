package app.markets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import app.controls.ControlPanel;
import app.exceptions.MarketCollectionException;
import app.exceptions.WrongMarketParamException;
import app.valuables.Valuable;
import javafx.util.Pair;

public abstract class Market {
    private final String name;
    private float marginFee;
    private final String currency;
    protected HashMap<String, Integer> productsWithPrices;
    protected Market(String name, float marginFee, String currency) throws WrongMarketParamException {
        if(ControlPanel.getInstance().marketExist(name))
        {
            throw new WrongMarketParamException("That market already exist: "+ name);
        }
        if(name.length()==0 || name.length()>20)
        {
            throw new WrongMarketParamException("Wrong markets name:" + name);
        }
        this.name = name;
        if(marginFee < 0f)
        {
            throw new WrongMarketParamException("Wrong margin fee value: " + marginFee);
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
    public Float getMarginFee(){return this.marginFee;}

    /**
     * Method used to get all product names and their prices to show it on GUI.
     * @return ArrayList<Pair<String, Integer>> list of all product in market with their sell price
     */
    public ArrayList<Pair<String, Integer>> getProductsWithPrices()
    {
        ArrayList<Pair<String, Integer>> result = new ArrayList<>();
        for(String product: productsWithPrices.keySet())
        {
            result.add(new Pair<>(product, productsWithPrices.get(product)));
        }
        return result;
    }
    public String getCurrency() {
        return currency;
    }

    protected void deepAdd(String toAdd, Integer price) throws MarketCollectionException {
        if(productsWithPrices.containsKey(toAdd))
        {
            throw new MarketCollectionException("Tried to add currency that already is in the market");
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

    /**
     * Method saying whether you can buy given product in this market.
     * @param valuableName
     * @return
     */
    public boolean isProductInMarket(String valuableName)
    {
        return productsWithPrices.containsKey(valuableName);
    }

    public Integer getProductPriceBuy(String productName) throws MarketCollectionException {
        if(!isProductInMarket(productName))
        {
            throw new MarketCollectionException("Tried to get price of product that is ont in the market: " + productName);
        }
        return productsWithPrices.get(productName);
    }

    /**
     * Method that calculate sell price for given product and returns it.
     * @param productName
     * @return sell price for given product
     * @throws MarketCollectionException
     */
    public Integer getProductPriceSell(String productName) throws MarketCollectionException {
        if(!isProductInMarket(productName))
        {
            throw new MarketCollectionException("Tried to get price of product that is ont in the market: " + productName);
        }
        return calculateSellPrice(productName);
    }

    private Integer calculateSellPrice(String productName)
    {
        int buyPrice = productsWithPrices.get(productName);
        int result = (int) (buyPrice*(1f-getMarginFee())/(1f+getMarginFee()));
        return Math.max(1, result);
    }

    /**
     * Method used to change prices to new ones.
     */
    public synchronized void updatePrices()
    {
        productsWithPrices.replaceAll((p, v) -> calculateUpdatedPrice(ControlPanel.getInstance().getValuable(p)));
    }

    private Integer calculateUpdatedPrice(Valuable valuable)
    {
        Integer currencyValue = ControlPanel.getInstance().getCurrency(currency).getPrice();
        Float productPriceWithTaxes = generatePrice(valuable);
        float productPriceInCurrency = productPriceWithTaxes/currencyValue;
        return (int)Math.ceil(productPriceInCurrency);
    }
    //generate price with random tax between 1f+marginFee and 1f+3*marginFee rounded up
    Float generatePrice(Valuable valuable) {
        Random RNG = new Random();
        float randomTax = 1f+getMarginFee() + RNG.nextFloat() * (2*getMarginFee());
        return valuable.getPrice()*randomTax;
    }

}
