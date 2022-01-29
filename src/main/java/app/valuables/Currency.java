package app.valuables;

import app.controls.ControlPanel;
import app.exceptions.WrongValuableParamException;

import java.util.List;

public class Currency extends Valuable{
    private List<String> legalCountries;
    private Integer tradingVolume;

    public Currency(String name, Integer price, List<String> countries) throws WrongValuableParamException {
        super(name, price);
        this.legalCountries = countries;
        this.tradingVolume = 0;
        ControlPanel.getInstance().addCurrency(this);
    }

    public List<String> getLegalCountries() {
        return legalCountries;
    }

    @Override
    public void bought(Integer amount) {
        increaseTradingVolume(amount);
    }
    public Integer getTradingVolume() {
        return tradingVolume;
    }

    public void resetTradingVolume() {
        this.tradingVolume = 0;
    }

    public void increaseTradingVolume(Integer amount)
    {
        this.tradingVolume+=amount;
    }
}
