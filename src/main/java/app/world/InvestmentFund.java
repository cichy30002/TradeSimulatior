package app.world;

import app.controls.ControlPanel;
import app.valuables.Valuable;

import java.util.HashMap;

public class InvestmentFund extends MarketClient{
    private HashMap<Investor, Float> clients;
    private final String managerName;
    private final String managerSurname;
    private volatile Integer totalBudget;

    public InvestmentFund(String name, String managerName, String managerSurname) {
        super(name);
        this.managerName = managerName;
        this.managerSurname = managerSurname;
        this.clients = new HashMap<>();
        ControlPanel.getInstance().addInvestmentFund(this);

    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerSurname() {
        return managerSurname;
    }

    public void setTotalBudget(Integer totalBudget)
    {
        this.totalBudget = totalBudget;
    }
    public Integer getTotalBudget()
    {
        return this.totalBudget;
    }

    @Override
    protected Integer call() throws Exception {
        while(ControlPanel.getInstance().getSimulationState())
        {
            tryToMakeTransaction();
            Thread.sleep(1000);
        }
        System.out.println(getName() +"died");
        return 0;
    }

    /**
     * Method allowing investors to give their money to VeRy ExPeRiEnCeD and TrUsTeD manager who will take care of their
     * properties and hopefully increase it. The lack of "withdrawFromFund" method suggest that it is 100% legit no scam.
     * @param newInvestor investor who wants to lose their money
     * @param valuable type of money they wanna lose
     * @param amount amount of money they wanna lose
     */
    public synchronized void contributeToFund(Investor newInvestor, Valuable valuable, Integer amount)
    {
        if(clients.containsKey(newInvestor)) return;
        HashMap<Investor, Integer> tmp = new HashMap<>();
        for(Investor investor : clients.keySet())
        {
            tmp.put(investor, (int)(clients.get(investor) * totalBudget));
        }
        Integer valueToAdd = valuable.getPrice() * amount;
        tmp.put(newInvestor, valueToAdd);
        System.out.println("xd2" + newInvestor.getName());
        this.setTotalBudget(this.getTotalBudget() + valueToAdd);

        clients.clear();
        System.out.println("xd1");
        for(Investor investor : tmp.keySet())
        {
            clients.put(investor, (float)tmp.get(investor)/(float)totalBudget);
        }
        System.out.println(newInvestor.getName() + " contributed to fund " + this.getName());
    }

}
