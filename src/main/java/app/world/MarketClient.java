package app.world;

import app.valuables.Currency;
import app.valuables.Valuable;

import java.util.ArrayList;
import java.util.List;

public abstract class MarketClient {
    private static Integer lastID = 1000;
    private String name;
    private String clientID;
    private List<Valuable> wallet;


    protected MarketClient(String name) {
        this.name = name;
        MarketClient.lastID++;
        this.clientID = MarketClient.lastID.toString();
        this.wallet = new ArrayList<>();
    }

    public void transaction(Currency currency, Valuable valuableToBuy){

    }

    public String getName() {
        return this.name;
    }
}
