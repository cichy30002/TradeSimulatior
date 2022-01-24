package app.valuables;

import app.exceptions.WrongValuableParamException;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Valuable {
    private final String name;
    private Integer price;


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
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public void updatePrice()
    {
        this.price = calculateUpdatedPrice();
    }

    private Integer calculateUpdatedPrice()
    {
        return this.price + ThreadLocalRandom.current().nextInt(-1, 2);
    }
}
