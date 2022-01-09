package app.valuables;

import app.exceptions.WrongValuableParamException;
import app.world.Company;

import java.util.List;

public class Index extends Valuable{
    private List<Company> listOfCompanies;

    public Index(String name, Integer price, List<Company> listOfCompanies)  throws WrongValuableParamException {
        super(name, price);
        if(listOfCompanies.size() == 0)
        {
            throw new WrongValuableParamException("Index consist of no companies!");
        }
        this.listOfCompanies = listOfCompanies;
    }

    public void update(){}
}
