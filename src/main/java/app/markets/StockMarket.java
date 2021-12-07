package app.markets;

import app.valuables.Index;

import java.util.List;

public class StockMarket extends Market{
    private String country;
    private String city;
    private String address;
    private List<Index> listOfIndexes;

    public Index getIndex(){
        return null;
    }
}
