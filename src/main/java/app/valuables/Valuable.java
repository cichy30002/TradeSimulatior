package app.valuables;

import app.exceptions.WrongValuableParamException;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Valuable {
    private final String name;
    private Integer price;
    private final ArrayList<Integer> priceHistory;

    protected Valuable(String name, Integer price) throws WrongValuableParamException {
        if(name.length()==0 || name.length()>20)
        {
            throw new WrongValuableParamException("Wrong valuable name: " + name);
        }
        this.name = name;
        if(price <= 0)
        {
            throw new WrongValuableParamException("Wrong valuable price: " + price);
        }
        this.priceHistory = new ArrayList<>();
        this.setPrice(price);
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
    void setPrice(Integer newPrice)
    {
        this.price = newPrice;
        this.priceHistory.add(this.price);
    }

    public void updatePrice()
    {
        this.setPrice(calculateUpdatedPrice());
    }

     Integer calculateUpdatedPrice()
    {
        return this.price + ThreadLocalRandom.current().nextInt((int) (-1*this.price*0.1), (int) (this.price*0.1));
    }

    public ArrayList<Integer> getPriceHistory()
    {
        return new ArrayList<>(this.priceHistory);
    }

    public abstract void bought(Integer amount);

    public boolean canBuy(Integer amount)
    {
        return true;
    }
}
