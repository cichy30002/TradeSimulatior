package app.valuables;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.world.Company;

public class Share extends Valuable{
    private final Company company;

    public Share(String name, Integer price)  throws WrongValuableParamException {
        super(name, price);
        this.company = findYourCompany();
    }

    private Company findYourCompany() throws WrongValuableParamException {
        Company yourCompany = ControlPanel.getInstance().findCompanyByName(getName());
        if(yourCompany==null){
            throw new WrongValuableParamException("Can't create share for company that does not exist!");
        }
        return yourCompany;
    }

    public Company getCompany() {
        return company;
    }
}
