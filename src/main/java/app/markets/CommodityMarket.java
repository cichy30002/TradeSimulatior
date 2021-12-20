package app.markets;

import app.valuables.Valuable;

import java.util.Collection;
import java.util.List;

public class CommodityMarket extends Market{
    private List<Integer> listOfPrices;

    public CommodityMarket(String name, float marginFee, String currency, Collection<Valuable> collectionOfProducts, List<Integer> listOfPrices) {
        super(name, marginFee, currency, collectionOfProducts);
        this.listOfPrices = listOfPrices;
    }

    public List<Integer> getListOfPrices() {
        return listOfPrices;
    }
}
