package app.valuables;

import app.exceptions.WrongValuableParamException;

import java.util.List;

public class Currency extends Valuable{
    private List<String> legalCountries;

    public Currency(String name, Integer price, List<String> countries) throws WrongValuableParamException {
        super(name, price);
        this.legalCountries = countries;
    }

    public List<String> getLegalCountries() {
        return legalCountries;
    }
}
