package app.markets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import app.valuables.Valuable;

public abstract class Market {
    private final String name;
    private float marginFee;
    private final String currency;
    private Collection<Valuable> collectionOfProducts;

    protected Market(String name, float marginFee, String currency) {
        //TODO check if name is unique
        this.name = name;
        this.marginFee = marginFee;
        //TODO check if currency exist
        this.currency = currency;
    }

    public void add(Valuable toAdd){
        collectionOfProducts.add(toAdd);
    }

    public void remove(Valuable toRemove){
        collectionOfProducts.remove(toRemove);
    }

    public String getName(){
        return this.name;
    }


    public String getCurrency() {
        return currency;
    }
}
