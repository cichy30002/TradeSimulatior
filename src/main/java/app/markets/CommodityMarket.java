package app.markets;

import app.valuables.Commodity;
import app.valuables.Valuable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommodityMarket extends Market{
    private List<Integer> listOfPrices;
    private List<Commodity> collectionOfProducts;
    public CommodityMarket(String name, float marginFee, String currency, ArrayList<Commodity> collectionOfProducts, List<Integer> listOfPrices) {
        super(name, marginFee, currency);
        this.listOfPrices = listOfPrices;
        this.collectionOfProducts = collectionOfProducts;
    }

    public List<Integer> getListOfPrices() {
        return listOfPrices;
    }
}
