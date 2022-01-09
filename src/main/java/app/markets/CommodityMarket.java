package app.markets;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.valuables.Commodity;

import java.util.ArrayList;
import java.util.List;

public class CommodityMarket extends Market{
    private List<Integer> listOfPrices;
    private List<Commodity> collectionOfProducts;
    public CommodityMarket(String name, float marginFee, String currency, ArrayList<Commodity> collectionOfProducts, List<Integer> listOfPrices) throws WrongMarketParamException {
        super(name, marginFee, currency);
        this.listOfPrices = listOfPrices;
        this.collectionOfProducts = collectionOfProducts;
        ControlPanel.getInstance().addCommodityMarket(this);
    }

    public List<Integer> getListOfPrices() {
        return listOfPrices;
    }
}
