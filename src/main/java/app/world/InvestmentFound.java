package app.world;

import app.valuables.Valuable;

import java.util.List;

public class InvestmentFound extends MarketClient{
    private List<Valuable> toBuy;
    private Integer investorsNeeded;
    private String managerName;
    private String managerSurname;

    public InvestmentFound(String name, List<Valuable> toBuy, Integer investorsNeeded, String managerName, String managerSurname) {
        super(name);
        this.toBuy = toBuy;
        this.investorsNeeded = investorsNeeded;
        this.managerName = managerName;
        this.managerSurname = managerSurname;
    }
}
