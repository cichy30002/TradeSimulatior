package app.valuables;

import app.controls.ControlPanel;
import app.exceptions.WrongValuableParamException;
import app.world.Company;

import java.util.ArrayList;
import java.util.List;

public class Index extends Valuable{
    private List<Company> listOfCompanies;

    public Index(String name, Integer price, List<String> listOfCompaniesNames)  throws WrongValuableParamException {
        super(name, price);
        if(listOfCompaniesNames.size() == 0)
        {
            throw new WrongValuableParamException("Index consist of no companies!");
        }
        this.listOfCompanies = new ArrayList<>();
        for(String companyName : listOfCompaniesNames)
        {
            Company nextCompany = ControlPanel.getInstance().getCompany(companyName);
            if(nextCompany == null)
            {
                throw new WrongValuableParamException("Index list of companies names consist of wrong company name: " + companyName);
            }
            this.listOfCompanies.add(nextCompany);
        }
        ControlPanel.getInstance().addIndex(this);
    }

    /**
     * Sets the index price to sum of shares prices
     */
    @Override
    public void updatePrice()
    {
        Integer result = 0;
        for (Company company: listOfCompanies)
        {
            result += company.getShare().getPrice();
        }
        this.setPrice(result);
    }

    /**
     * do nothin'
     * @param amount
     */
    @Override
    public void bought(Integer amount) {
    }
}
