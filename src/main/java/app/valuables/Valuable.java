package app.valuables;

import app.controls.ControlPanel;
import app.exceptions.WrongValuableParamException;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Valuable {
    private final String name;
    private volatile Integer price;
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
        this.setPrice(Math.max(1, calculateUpdatedPrice()));
    }

     Integer calculateUpdatedPrice()
    {
        int randomChange = (int) (this.price*0.1) + 1;
        if(ControlPanel.getInstance().getBullBearRatio() > 0)
        {
            randomChange = ThreadLocalRandom.current().nextInt((int)(randomChange*-1*(1-ControlPanel.getInstance().getBullBearRatio())) -1, randomChange + 1);
        }else
        {
            randomChange = ThreadLocalRandom.current().nextInt((int)(randomChange*-1*(1-ControlPanel.getInstance().getBullBearRatio())) -1, (int)(randomChange*-1*(1+ControlPanel.getInstance().getBullBearRatio())) + 1);
        }
        return this.price + randomChange;
    }

    /**
     * Method used to show how prices changed over time
     * @return list of prices
     */
    public ArrayList<Integer> getPriceHistory()
    {
        return new ArrayList<>(this.priceHistory);
    }

    /**
     * abstract method - each valuable needs to keep track how many times it was bought
     * @param amount
     */
    public abstract void bought(Integer amount);

    /**
     * @param amount
     * @return true/false wether 
     */
    public boolean canBuy(Integer amount)
    {
        return true;
    }
}
