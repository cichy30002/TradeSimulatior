package app.markets;


import app.controls.ControlPanel;
import app.exceptions.MarketCollectionException;
import app.exceptions.WrongMarketParamException;
import app.valuables.Currency;
import app.valuables.Valuable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CurrencyMarket extends Market{

    public CurrencyMarket(String name, float marginFee, String currency, List<String> listOfProductsNames) throws WrongMarketParamException {
        super(name, marginFee, currency);
        this.productsWithPrices = makeProductsWithPrices(findCurrencies(listOfProductsNames));
        updatePrices();
        ControlPanel.getInstance().addCurrencyMarket(this);
    }

    private HashMap<String, Integer> makeProductsWithPrices(List<Currency> currencies){
        HashMap<String,Integer> result = new HashMap<>();
        for(Currency currency: currencies)
        {
            result.put(currency.getName(), 0);
        }
        return result;
    }

    private List<Currency> findCurrencies(List<String> listOfProductsNames) throws WrongMarketParamException {
        List<Currency> result = new ArrayList<>();
        for(String currencyName : listOfProductsNames)
        {
            Currency nextCurrency = ControlPanel.getInstance().getCurrency(currencyName);
            if(nextCurrency == null)
            {
                throw new WrongMarketParamException("Tried to add currency that does not exist to currency market: " + currencyName);
            }
            result.add(nextCurrency);
        }
        if(result.size() == 0)
        {
            throw new WrongMarketParamException("Tried to make currency market without currencies");
        }
        return result;
    }

    public void add(Currency toAdd, Integer price) throws MarketCollectionException {
        this.deepAdd(toAdd.getName(), price);
    }

    public void remove(String toRemoveName) throws MarketCollectionException {
        this.deepRemove(toRemoveName);
    }


}
