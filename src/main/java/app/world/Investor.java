package app.world;

import app.controls.ControlPanel;
import app.exceptions.TransactionException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;


public class Investor extends MarketClient
{
    public Investor(String name)
    {
        super(name);
        ControlPanel.getInstance().addInvestor(this);
    }

    /**
     * Method which performs investor super ability: getting cash out of nowhere!
     * Each usage of this ability gives at random 1-100 of random currency available in simulation.
     * @throws TransactionException
     */
    public void bonusFunds() throws TransactionException {
        String currencyName = getRandomCurrency();
        int amount = ThreadLocalRandom.current().nextInt(1,100);
        this.addFunds(currencyName, amount);
    }

    private String getRandomCurrency() {
        ArrayList<String> currencies = ControlPanel.getInstance().getAllCurrencies();
        return currencies.get(ThreadLocalRandom.current().nextInt(currencies.size()));
    }

    @Override
    protected Integer call() throws Exception {
        while(ControlPanel.getInstance().getSimulationState())
        {
            tryToMakeAction();
            randomSleep();
        }
        System.out.println(getName() +"died");
        return 0;
    }

    private void tryToMakeAction() {
        int randomActionID = ThreadLocalRandom.current().nextInt(10);
        if(randomActionID == 0)
        {
            try {
                bonusFunds();
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }else if(randomActionID<=2)
        {
            tryFundsTransaction();
        }else {
            tryToMakeTransaction();
        }
    }

    private synchronized void tryFundsTransaction() {
        Collection<InvestmentFund> investmentFundsCol = ControlPanel.getInstance().getAllInvestmentFunds();
        ArrayList<InvestmentFund> investmentFunds = new ArrayList<>(investmentFundsCol);
        ArrayList<Pair<String, Integer>> wallet = getWallet();
        Pair<String, Integer> randomPosition = wallet.get(ThreadLocalRandom.current().nextInt(wallet.size()));
        int randomAmount = randomPosition.getValue()<=1 ?  1 :  ThreadLocalRandom.current().nextInt(1,randomPosition.getValue());
        investmentFunds.get(ThreadLocalRandom.current().nextInt(investmentFunds.size())).contributeToFund(
                this, ControlPanel.getInstance().getValuable(randomPosition.getKey()),
                randomAmount);
    }


}
