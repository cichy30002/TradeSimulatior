package app.markets;

import java.util.Collection;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.valuables.Valuable;

public abstract class Market {
    private final String name;
    private float marginFee;
    private final String currency;
    private Collection<Valuable> collectionOfProducts;

    protected Market(String name, float marginFee, String currency) throws WrongMarketParamException {
        if(ControlPanel.getInstance().MarketExist(name))
        {
            throw new WrongMarketParamException("That market already exist!");
        }
        this.name = name;
        this.marginFee = marginFee;
        if(!ControlPanel.getInstance().CurrencyExist(currency))
        {
            throw new WrongMarketParamException("Currency " + currency + " does not exist!");
        }
        this.currency = currency;
    }

    public void add(Valuable toAdd){
        collectionOfProducts.add(toAdd);
    }

    public void remove(Valuable toRemove){
        collectionOfProducts.remove(toRemove);
    }

    public String getName(){
        return this.name;
    }


    public String getCurrency() {
        return currency;
    }
}
