package app.world;

import app.controls.ControlPanel;
import app.exceptions.TransactionException;
import app.valuables.Valuable;

import java.util.ArrayList;
import java.util.List;

public class InvestmentFound extends MarketClient{
    private List<Valuable> toBuy;
    private Integer investorsNeeded;
    private final String managerName;
    private final String managerSurname;

    public InvestmentFound(String name, List<String> toBuyNames, Integer investorsNeeded, String managerName, String managerSurname) {
        super(name);
        this.toBuy = findValuables(toBuyNames);
        this.investorsNeeded = investorsNeeded;
        this.managerName = managerName;
        this.managerSurname = managerSurname;
        ControlPanel.getInstance().addInvestmentFound(this);
    }
    private List<Valuable> findValuables( List<String> names)
    {
        List<Valuable> result = new ArrayList<>();
        for(String name : names)
        {
            Valuable nextValuable = ControlPanel.getInstance().getCurrency(name);
            if(nextValuable == null)
            {
                nextValuable = ControlPanel.getInstance().getCommodity(name);
            }
            if(nextValuable == null)
            {
                nextValuable = ControlPanel.getInstance().getShare(name);
            }
            if(nextValuable == null)
            {
                nextValuable = ControlPanel.getInstance().getIndex(name);
            }
            if(nextValuable == null)
            {
                //throw exception
            }
            result.add(nextValuable);
        }
        return result;
    }

    public List<String> getToBuy() {
        ArrayList<String> result  = new ArrayList<>();
        for(Valuable valuable:toBuy)
        {
            result.add(valuable.getName());
        }
        return result;
    }

    public Integer getInvestorsNeeded() {
        return investorsNeeded;
    }

    public void setInvestorsNeeded(Integer investorsNeeded) {
        this.investorsNeeded = investorsNeeded;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerSurname() {
        return managerSurname;
    }
}
