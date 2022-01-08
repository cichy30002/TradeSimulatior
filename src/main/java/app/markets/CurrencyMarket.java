package app.markets;


import app.valuables.Currency;
import app.valuables.Valuable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrencyMarket extends Market{
    private List<Integer> listOfBuySellPrices;
    private List<Currency> collectionOfProducts;
    public CurrencyMarket(String name, float marginFee, String currency, List<Currency> collectionOfProducts, List<Integer> listOfBuySellPrices) {
        super(name, marginFee, currency);
        this.listOfBuySellPrices = listOfBuySellPrices;
        this.collectionOfProducts = collectionOfProducts;
    }

    public List<Integer> getListOfBuySellPrices() {
        return listOfBuySellPrices;
    }
}
