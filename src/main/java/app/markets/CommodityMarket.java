package app.markets;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.valuables.Commodity;
import app.valuables.Currency;
import app.valuables.Valuable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CommodityMarket extends Market{
    private List<Integer> listOfPrices;
    public CommodityMarket(String name, float marginFee, String currency, ArrayList<String> listOfCommodityNames) throws WrongMarketParamException {
        super(name, marginFee, currency);
        this.productsWithPrices = makeProductsWithPrices(findCommodities(listOfCommodityNames));
        updatePrices();
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

    private HashMap<String, Integer> makeProductsWithPrices(List<Commodity> commodities){
        HashMap<String,Integer> result = new HashMap<>();
        for (Commodity commodity : commodities) {
            result.put(commodity.getName(), 0);
        }
        return result;
    }
}
