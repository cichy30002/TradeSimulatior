package app.valuables;

import app.exceptions.wrongValuableParamException;

public abstract class Valuable {
    private final String name;
    private Integer price;


    protected Valuable(String name, Integer price) throws wrongValuableParamException {
        if(name.length()==0 || name.length()>20)
        {
            throw new wrongValuableParamException("Wrong valuable name: " + name);
        }
        this.name = name;
        if(price <= 0)
        {
            throw new wrongValuableParamException("Wrong valuable price: " + price);
        }
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
