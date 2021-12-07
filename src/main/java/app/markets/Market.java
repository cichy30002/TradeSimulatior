package app.markets;

import java.util.Collection;
import app.valuables.Valuable;

public abstract class Market {
    private String name;
    private float marginFee;
    private String currency;
    private Collection<Valuable> collectionOfProducts;

    public void add(){}
    public void remove(){}
    public String getName(){
        return null;
    }
    public  String getCurrency(){
        return null;
    }

}
