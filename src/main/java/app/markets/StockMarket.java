package app.markets;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.valuables.Index;
import app.valuables.Share;

import java.util.ArrayList;
import java.util.List;

public class StockMarket extends Market{
    private final String country;
    private final String city;
    private final String address;
    private List<Index> listOfIndexes;
    private List<Share> listOfProducts;

    public StockMarket(String name, float marginFee, String currency, List<String> listOfProductsNames, String country, String city, String address, List<String> listOfIndexesNames) throws WrongMarketParamException {
        super(name, marginFee, currency);
        this.country = country;
        this.city = city;
        this.address = address;
        this.listOfIndexes = findIndexes(listOfIndexesNames);
        this.listOfProducts = findShares(listOfProductsNames);
        ControlPanel.getInstance().addStockMarket(this);
    }

    private List<Share> findShares(List<String> listOfProductsNames) throws WrongMarketParamException {
        List<Share> result = new ArrayList<>();
        for(String shareName : listOfProductsNames)
        {
            Share nextShare = ControlPanel.getInstance().getShare(shareName);
            if(nextShare == null)
            {
                throw new WrongMarketParamException("Tried to add share that does not exist to stock market: " + shareName);
            }
            result.add(nextShare);
        }
        if(result.size() == 0)
        {
            throw new WrongMarketParamException("Tried to init StockMarket without shares");
        }
        return result;

    }
    private List<Index> findIndexes(List<String> listOfProductsNames) throws WrongMarketParamException {
        List<Index> result = new ArrayList<>();
        for(String indexName : listOfProductsNames)
        {
            Index nextIndex = ControlPanel.getInstance().getIndex(indexName);
            if(nextIndex == null)
            {
                throw new WrongMarketParamException("Tried to add index that does not exist to stock market: " + indexName);
            }
            result.add(nextIndex);
        }
        return result;

    }
    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public List<Index> getListOfIndexes() {
        return listOfIndexes;
    }

    public void addIndex(Index toAdd)
    {
        listOfIndexes.add(toAdd);
    }

    public void removeIndex(Index toRemove)
    {
        listOfIndexes.remove(toRemove);
    }
}
