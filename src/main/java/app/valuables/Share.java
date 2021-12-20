package app.valuables;

import app.exceptions.wrongValuableParamException;
import app.world.Company;

public class Share extends Valuable{
    private final Company company;

    public Share(String name, Integer price, Company company)  throws wrongValuableParamException {
        super(name, price);
        if(company == null)
        {
            throw new wrongValuableParamException("company is null");
        }
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }
}
