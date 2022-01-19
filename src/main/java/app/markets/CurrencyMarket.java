package app.markets;


import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.valuables.Currency;
import app.valuables.Valuable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrencyMarket extends Market{
    private List<Integer> listOfBuySellPrices;
    private List<Currency> listOfProducts;
    public CurrencyMarket(String name, float marginFee, String currency, List<String> listOfProductsNames, List<String> listOfBuySellPrices) throws WrongMarketParamException {
        super(name, marginFee, currency);
        this.listOfProducts = findCurrencies(listOfProductsNames);
        this.listOfBuySellPrices = parseList(listOfBuySellPrices);

        ControlPanel.getInstance().addCurrencyMarket(this);
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


    public List<Integer> getListOfBuySellPrices() {
        return listOfBuySellPrices;
    }
}
