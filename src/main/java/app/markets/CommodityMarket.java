package app.markets;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.valuables.Commodity;
import app.valuables.Currency;

import java.util.ArrayList;
import java.util.List;

public class CommodityMarket extends Market{
    private List<Integer> listOfPrices;
    private List<Commodity> collectionOfProducts;
    public CommodityMarket(String name, float marginFee, String currency, ArrayList<String> listOfProductNames, List<String> listOfPrices) throws WrongMarketParamException {
        super(name, marginFee, currency);
        this.listOfPrices = parseList(listOfPrices);
        this.collectionOfProducts = findCommodities(listOfProductNames);
        ControlPanel.getInstance().addCommodityMarket(this);
    }

    private List<Commodity> findCommodities(List<String> listOfProductsNames) throws WrongMarketParamException {
        List<Commodity> result = new ArrayList<>();
        for(String commodityName : listOfProductsNames)
        {
            Commodity nextCommodity = ControlPanel.getInstance().getCommodity(commodityName);
            if(nextCommodity == null)
            {
                throw new WrongMarketParamException("Tried to add currency that does not exist to currency market: " + commodityName);
            }
            result.add(nextCommodity);
        }
        if(result.size() == 0)
        {
            throw new WrongMarketParamException("Tried to make commodity market without commodities");
        }
        return result;

    }
    public List<Integer> getListOfPrices() {
        return listOfPrices;
    }
}
