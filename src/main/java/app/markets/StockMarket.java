package app.markets;

import app.valuables.Index;
import app.valuables.Valuable;

import java.util.Collection;
import java.util.List;

public class StockMarket extends Market{
    private final String country;
    private final String city;
    private final String address;
    private List<Index> listOfIndexes;


    public StockMarket(String name, float marginFee, String currency, Collection<Valuable> collectionOfProducts, String country, String city, String address, List<Index> listOfIndexes) {
        super(name, marginFee, currency, collectionOfProducts);
        this.country = country;
        this.city = city;
        this.address = address;
        this.listOfIndexes = listOfIndexes;
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
