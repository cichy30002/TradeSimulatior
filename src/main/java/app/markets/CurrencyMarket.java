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
    private List<Currency> collectionOfProducts;
    public CurrencyMarket(String name, float marginFee, String currency, List<Currency> collectionOfProducts, List<Integer> listOfBuySellPrices) throws WrongMarketParamException {
        super(name, marginFee, currency);
        this.listOfBuySellPrices = listOfBuySellPrices;
        this.collectionOfProducts = collectionOfProducts;
        ControlPanel.getInstance().addCurrencyMarket(this);
    }

    public List<Integer> getListOfBuySellPrices() {
        return listOfBuySellPrices;
    }
}
