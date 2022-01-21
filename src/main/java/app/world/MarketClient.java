package app.world;

import app.controls.ControlPanel;
import app.exceptions.MarketCollectionException;
import app.exceptions.TransactionException;
import app.markets.Market;
import app.valuables.Currency;
import app.valuables.Valuable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class MarketClient {
    private static Integer lastID = 1000;
    private String name;
    private String clientID;
    private HashMap<String, Integer> wallet;


    protected MarketClient(String name) {
        this.name = name;
        MarketClient.lastID++;
        this.clientID = MarketClient.lastID.toString();
        this.wallet = new HashMap<>();
    }

    public void transactionBuy(String currencyName, Valuable valuableToBuy, Integer amount, Market market) throws TransactionException {
        if(!ControlPanel.getInstance().currencyExist(currencyName) || !Objects.equals(market.getCurrency(), currencyName))
        {
            throw new TransactionException("Tried to make transaction with currency not matching markets currency");
        }
        if(!ControlPanel.getInstance().valuableExist(valuableToBuy.getName()))
        {
            throw new TransactionException("Tried to buy valuable that does not exist");
        }
        if(!market.isProductInMarket(valuableToBuy.getName()))
        {
            throw new TransactionException("Tried to buy valuable which is not available in this market: " + valuableToBuy.getName());
        }
        if(amount <= 0)
        {
            throw new TransactionException("Tried to buy invalid amount!");
        }
        Integer availableMoney = getAvailableValuableAmount(currencyName);
        try {
            if(availableMoney < market.getProductPrice(valuableToBuy.getName()) * amount)
            {
                throw new TransactionException("Not enough founds in clients wallet!");
            }
        } catch (MarketCollectionException e) {
            e.printStackTrace();
        }
        addToWallet(valuableToBuy.getName(), amount);
        try {
            removeFromWallet(market.getCurrency(), market.getProductPrice(valuableToBuy.getName()) * amount);
        } catch (MarketCollectionException e) {
            e.printStackTrace();
        }
    }

    public void transactionSell(Valuable valuableToSell, Integer amount, Market market) throws TransactionException {
        if(!ControlPanel.getInstance().valuableExist(valuableToSell.getName()))
        {
            throw new TransactionException("Tried to buy valuable that does not exist");
        }
        if(!market.isProductInMarket(valuableToSell.getName()))
        {
            throw new TransactionException("Tried to buy valuable which is not available in this market: " + valuableToSell.getName());
        }
        if(amount <= 0)
        {
            throw new TransactionException("Tried to buy invalid amount!");
        }
        Integer availableAmountOfGivenValuable = getAvailableValuableAmount(valuableToSell.getName());

        if(availableAmountOfGivenValuable < amount)
        {
            throw new TransactionException("Not enough founds in clients wallet!");
        }

        removeFromWallet(valuableToSell.getName(), amount);
        try {
            addToWallet(market.getCurrency(), market.getProductPrice(valuableToSell.getName()) * amount);
        } catch (MarketCollectionException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public String getClientID()
    {
        return this.clientID;
    }

    protected void addToWallet(String valuableName, Integer amount) throws TransactionException {
        if(!ControlPanel.getInstance().valuableExist(valuableName))
        {
            throw new TransactionException("Failed adding to wallet - valuable does not exist: " + valuableName);
        }
        if(amount <= 0)
        {
            throw new TransactionException("Failed adding to wallet - wrong amount: " + amount);
        }
        if(wallet.containsKey(valuableName))
        {
            wallet.put(valuableName,amount + wallet.get(valuableName));
        }
        else
        {
            wallet.put(valuableName, amount);
        }
    }

    protected void removeFromWallet(String valuableName, Integer amount) throws TransactionException {
        if(!wallet.containsKey(valuableName))
        {
            throw new TransactionException("Tried to remove from wallet valuable which is not in a wallet: " + valuableName);
        }
        if(wallet.get(valuableName) < amount)
        {
            throw new TransactionException("Tried to remove from wallet more " + valuableName + " than there are");
        }
        wallet.put(valuableName, wallet.get(valuableName) - amount);
        if(wallet.get(valuableName) == 0)
        {
            wallet.remove(valuableName);
        }
    }

    public Integer getAvailableValuableAmount(String valuableName)
    {
        return wallet.getOrDefault(valuableName, 0);
    }
    public void addFunds(String currencyName, Integer amount) throws TransactionException {
        this.addToWallet(currencyName, amount);
    }
}
