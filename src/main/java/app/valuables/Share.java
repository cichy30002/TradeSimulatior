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
        ControlPanel.getInstance().addShare(this);
    }

    /**
     * Updates share's company variables that keep track how many times share was bought.
     * @param amount
     */
    @Override
    public void bought(Integer amount) {
        this.company.increaseTotalSales(amount);
        this.company.increaseTradingVolume(amount);
        this.company.increaseSoldShares(amount);
    }

    /**
     * @param amount amount of shares it ask for
     * @return if there still are available shares
     */
    @Override
    public boolean canBuy(Integer amount)
    {
        return company.getSoldShares() + amount <= company.getNumberOfShares();
    }
    private Company findYourCompany() throws WrongValuableParamException {
        Company yourCompany = ControlPanel.getInstance().getCompany(getName());
        if(yourCompany==null){
            throw new WrongValuableParamException("Can't create share for company that does not exist!");
        }
        return yourCompany;
    }

    public Company getCompany() {
        return company;
    }
}
