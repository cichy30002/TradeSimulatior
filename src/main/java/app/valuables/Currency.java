package app.valuables;

import app.controls.ControlPanel;
import app.exceptions.WrongValuableParamException;

import java.util.List;

public class Currency extends Valuable{
    private List<String> legalCountries;

    public Currency(String name, Integer price, List<String> countries) throws WrongValuableParamException {
        super(name, price);
        this.legalCountries = countries;
        ControlPanel.getInstance().addCurrency(this);
    }

    public List<String> getLegalCountries() {
        return legalCountries;
    }
}
