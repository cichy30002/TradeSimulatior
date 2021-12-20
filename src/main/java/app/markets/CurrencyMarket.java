package app.markets;


import app.valuables.Valuable;

import java.util.Collection;
import java.util.List;

public class CurrencyMarket extends Market{
    private List<Integer> listOfBuySellPrices;

    public CurrencyMarket(String name, float marginFee, String currency, Collection<Valuable> collectionOfProducts, List<Integer> listOfBuySellPrices) {
        super(name, marginFee, currency, collectionOfProducts);
        this.listOfBuySellPrices = listOfBuySellPrices;
    }

    public List<Integer> getListOfBuySellPrices() {
        return listOfBuySellPrices;
    }
}
