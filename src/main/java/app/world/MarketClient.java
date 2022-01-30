package app.world;

import app.controls.ControlPanel;
import app.exceptions.MarketCollectionException;
import app.exceptions.TransactionException;
import app.markets.Market;
import app.valuables.Valuable;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public abstract class MarketClient extends Task<Integer> {
    private static Integer lastID = 1000;
    private final String name;
    private final String clientID;
    private final HashMap<String, Integer> wallet;
    private ObservableList<Pair<String,Integer>> observableWallet;


    protected MarketClient(String name) {
        this.name = name;
        MarketClient.lastID++;
        this.clientID = MarketClient.lastID.toString();
        this.wallet = new HashMap<>();
    }

    /**
     * Method used to perform buy transaction of given valuable on given marget, using given currency.
     * It checks all possible errors and raise TransactionException before performing transaction.
     * @param currencyName
     * @param valuableToBuy
     * @param amount
     * @param market
     * @throws TransactionException
     */
    public void transactionBuy(String currencyName, Valuable valuableToBuy, Integer amount, Market market) throws TransactionException {
        if(isCurrencyMatchingMarket(currencyName, market))
        {
            throw new TransactionException("Tried to make transaction with currency not matching markets currency");
        }
        transactionValuableAndAmountChecks(valuableToBuy, amount, market);
        Integer availableMoney = getAvailableValuableAmount(currencyName);
        canAffordTransaction(valuableToBuy, amount, market, availableMoney);
        if(!valuableToBuy.canBuy(amount))
        {
            return;
        }
        performBuy(valuableToBuy, amount, market);
    }

    private void performBuy(Valuable valuableToBuy, Integer amount, Market market) throws TransactionException {
        addToWallet(valuableToBuy.getName(), amount);
        try {
            removeFromWallet(market.getCurrency(), market.getProductPriceBuy(valuableToBuy.getName()) * amount);
        } catch (MarketCollectionException e) {
            e.printStackTrace();
        }
        valuableToBuy.bought(amount);
    }

    private void canAffordTransaction(Valuable valuableToBuy, Integer amount, Market market, Integer availableMoney) throws TransactionException {
        try {
            if(availableMoney < market.getProductPriceBuy(valuableToBuy.getName()) * amount)
            {
                throw new TransactionException("Not enough funds in clients wallet!");
            }
        } catch (MarketCollectionException e) {
            e.printStackTrace();
        }
    }

    private boolean isCurrencyMatchingMarket(String currencyName, Market market) {
        return !ControlPanel.getInstance().currencyExist(currencyName) || !Objects.equals(market.getCurrency(), currencyName);
    }

    /**
     * Method used to perform sell transaction of given valuable on given marget.
     * It checks all possible errors and raise TransactionException before performing transaction.
     * @param valuableToSell
     * @param amount
     * @param market
     * @throws TransactionException
     */
    public void transactionSell(Valuable valuableToSell, Integer amount, Market market) throws TransactionException {
        transactionValuableAndAmountChecks(valuableToSell, amount, market);
        Integer availableAmountOfGivenValuable = getAvailableValuableAmount(valuableToSell.getName());

        if(availableAmountOfGivenValuable < amount)
        {
            throw new TransactionException("Not enough funds in clients wallet!");
        }

        performSell(valuableToSell, amount, market);
    }

    private void performSell(Valuable valuableToSell, Integer amount, Market market) throws TransactionException {
        removeFromWallet(valuableToSell.getName(), amount);
        try {
            addToWallet(market.getCurrency(), market.getProductPriceSell(valuableToSell.getName()) * amount);
        } catch (MarketCollectionException e) {

            e.printStackTrace();
        }
        valuableToSell.bought(amount);
    }

    private void transactionValuableAndAmountChecks(Valuable valuable, Integer amount, Market market) throws TransactionException
    {
        if(!ControlPanel.getInstance().valuableExist(valuable.getName()))
        {
            throw new TransactionException("Tried to buy valuable that does not exist");
        }
        if(!market.isProductInMarket(valuable.getName()))
        {
            throw new TransactionException("Tried to buy valuable which is not available in this market: " + valuable.getName());
        }
        if(amount <= 0)
        {
            throw new TransactionException("Tried to buy invalid amount!");
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

    /**
     * Returns total amount of given valuable that is currently available in MarketClient's wallet
     * @param valuableName
     * @return
     */
    public Integer getAvailableValuableAmount(String valuableName)
    {
        return wallet.getOrDefault(valuableName, 0);
    }

    /**
     * Method used to add money (currencies) to wallet. Its main purpose is to set initial budgets and to perform
     * bonus funds action for investor.
     * @param currencyName
     * @param amount
     * @throws TransactionException
     */
    public void addFunds(String currencyName, Integer amount) throws TransactionException {
        this.addToWallet(currencyName, amount);
    }

    /**
     * Returns wallet copy, main purpose is to show wallet on GUI.
     * @return wallet copy represented as ArrayList<Pair<String, Integer>>
     */
    public ArrayList<Pair<String, Integer>> getWallet()
    {
        ArrayList<Pair<String, Integer>> result = new ArrayList<>();
        for(String valuable: wallet.keySet())
        {
            result.add(new Pair<>(valuable, wallet.get(valuable)));
        }
        return result;
    }

    /**
     * Checks if the wallet contains any amount of given valuable
     * @param valuableName
     * @return
     */
    public Boolean isValuableInWallet(String valuableName)
    {
        return wallet.containsKey(valuableName) && wallet.get(valuableName) > 0;
    }

    void tryTransactionBuy(Market randomMarket)
    {
        try {
            Pair<String, Integer> randomProduct = findRandomProduct(randomMarket);
            int randomAmount = findRandomAmount(randomMarket, randomProduct);
            if(randomAmount == 0)return;
            transactionBuy(randomMarket.getCurrency(), ControlPanel.getInstance().getValuable(randomProduct.getKey()),
                    randomAmount, randomMarket );
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    private int findRandomAmount(Market randomMarket, Pair<String, Integer> randomProduct) {
        int maxAmountToBuy = (int)Math.floor((float)getAvailableValuableAmount(randomMarket.getCurrency())/(float)randomProduct.getValue());
        if(maxAmountToBuy == 0) return 0;
        if(maxAmountToBuy == 1) return 1;
        return ThreadLocalRandom.current().nextInt(1,maxAmountToBuy);
    }

    private Pair<String, Integer> findRandomProduct(Market randomMarket) {
        ArrayList<Pair<String, Integer>> productsWithPrices = randomMarket.getProductsWithPrices();
        return productsWithPrices.get(ThreadLocalRandom.current().nextInt(productsWithPrices.size()));
    }

    void tryTransactionSell(Market randomMarket)
    {
        try{
            ArrayList<Pair<String, Integer>> possibleToSell = getAllProductAvailableToSell(randomMarket);
            if(possibleToSell.size() == 0) return;

            Pair<String, Integer> randomProduct = possibleToSell.get(ThreadLocalRandom.current().nextInt(possibleToSell.size()));
            int randomAmountToSell = getRandomAmountToSell(randomProduct);
            transactionSell(ControlPanel.getInstance().getValuable(randomProduct.getKey()),
                    randomAmountToSell, randomMarket);
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }
    private int getRandomAmountToSell(Pair<String, Integer> randomProduct)
    {
        int maxAmountToSell = getAvailableValuableAmount(randomProduct.getKey());
        if(maxAmountToSell == 1) return 1;
        return ThreadLocalRandom.current().nextInt(1,maxAmountToSell);
    }
    private ArrayList<Pair<String, Integer>> getAllProductAvailableToSell(Market randomMarket) {
        ArrayList<Pair<String, Integer>> possibleToSell = new ArrayList<>();
        ArrayList<Pair<String, Integer>> productsWithPrices = randomMarket.getProductsWithPrices();
        for (Pair<String, Integer> product : productsWithPrices)
        {
            if(isValuableInWallet(product.getKey()))
            {
                possibleToSell.add(product);
            }
        }
        return possibleToSell;
    }

    synchronized void  tryToMakeTransaction() {
        ArrayList<String> marketNames = ControlPanel.getInstance().getAllMarketNames();
        Market randomMarket = ControlPanel.getInstance().getMarket(marketNames.get(ThreadLocalRandom.current().nextInt(marketNames.size())));
        if(isValuableInWallet(randomMarket.getCurrency()))
        {
            if(ThreadLocalRandom.current().nextFloat() > 0.5)
            {
                tryTransactionBuy(randomMarket);
            }
            else
            {
                tryTransactionSell(randomMarket);
            }
        }
        else
        {
            tryTransactionSell(randomMarket);
        }
    }
    void randomSleep() throws InterruptedException {
        int count = ControlPanel.getInstance().countMarketClients();
        int trans = ControlPanel.getInstance().getTransactionsPerSecond();
        float randomMultiplier = 0.75f + ThreadLocalRandom.current().nextFloat()/2;
        Thread.sleep((int)(count/trans*1000*randomMultiplier));
    }
}
