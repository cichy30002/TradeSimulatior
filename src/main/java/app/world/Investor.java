package app.world;

import app.controls.ControlPanel;
import app.exceptions.TransactionException;
import app.valuables.Currency;


public class Investor extends MarketClient
{


    public Investor(String name)
    {
        super(name);
        ControlPanel.getInstance().addInvestor(this);
    }

    public void bonusFounds(String currencyName, Integer amount) throws TransactionException {
        this.addFunds(currencyName, amount);
    }
}
