package main.world;

import app.controls.ControlPanel;
import app.exceptions.TransactionException;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CurrencyMarket;
import app.valuables.Commodity;
import app.valuables.Currency;
import app.world.Investor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class InvestorTest {
    CurrencyMarket currencyMarket;
    Currency currency1;
    Currency currency2;
    Currency currency3;
    Commodity commodity;
    ArrayList<String> currencies;
    ArrayList<String> prices;

    @Test
    public void constructorTest()
    {
        assertTrue(clientIDCheck());
    }

    private boolean clientIDCheck()
    {
        ArrayList<String> clientIDs = new ArrayList<>();
        Investor tmp;
        for(int i =0; i < 1000;i++)
        {
            tmp = new Investor(String.valueOf(i));
            if(clientIDs.contains(tmp.getClientID()))
            {
                return false;
            }
            clientIDs.add(tmp.getClientID());
        }
        return true;
    }

    @Test
    public void walletTest()
    {
        makeCurrencyMarket();
        Investor investor = new Investor("test");
        assertThrows(TransactionException.class, () -> investor.bonusFounds("xd", 10));
        assertThrows(TransactionException.class, () -> investor.bonusFounds(currency1.getName(), -10));
        assertDoesNotThrow(() -> investor.bonusFounds(currency1.getName(), 10));
        assertEquals(investor.getAvailableValuableAmount("zloty"), 10);
        assertThrows(TransactionException.class, () -> investor.transactionBuy("xd", currency1, 10, currencyMarket));
        assertThrows(TransactionException.class, () -> investor.transactionBuy("ruble", currency1, 10, currencyMarket));
        assertThrows(TransactionException.class, () -> investor.transactionBuy("zloty", commodity, 10, currencyMarket));
        assertThrows(TransactionException.class, () -> investor.transactionBuy("zloty", currency3, 10, currencyMarket));
        assertThrows(TransactionException.class, () -> investor.transactionBuy("zloty", currency1, -10, currencyMarket));
        assertThrows(TransactionException.class, () -> investor.transactionBuy("zloty", currency1, 0, currencyMarket));

        clearCurrencyMarket();
    }
    void makeCurrencyMarket()
    {
        ArrayList<String> countries = new ArrayList<>();
        countries.add("Poland");
        try {
            currency1 = new Currency("zloty", 20, countries);
            currency2 = new Currency("ruble", 30, countries);
            currency3 = new Currency("euro", 40, countries);
            commodity = new Commodity("gold", 123456, "ounce", 123000, 124000);
        } catch (WrongValuableParamException e) {
            System.out.println("Failed makeCurrencyMarket");
        }
        currencies = new ArrayList<>();
        currencies.add(currency1.getName());
        currencies.add(currency2.getName());
        prices = new ArrayList<>();
        prices.add("1");
        prices.add("2");
        try {
            currencyMarket = new CurrencyMarket("monetki", 2.0f, "zloty", currencies, prices);
        } catch (WrongMarketParamException e) {
            System.out.println("Failed makeCurrencyMarket");
        }
    }
    void clearCurrencyMarket()
    {
        ControlPanel.getInstance().removeCurrency(currency1.getName());
        ControlPanel.getInstance().removeCurrency(currency2.getName());
        ControlPanel.getInstance().removeCurrency(currency3.getName());
        ControlPanel.getInstance().removeCurrencyMarket(currencyMarket.getName());
        ControlPanel.getInstance().removeCommodity(commodity.getName());
    }
    @Test
    public void transactionTest()
    {
        makeCurrencyMarket();
        Investor investor = new Investor("test");
        assertThrows(TransactionException.class, () -> investor.bonusFounds("xd", 10));
        assertDoesNotThrow(() -> investor.bonusFounds(currency1.getName(), 10));
        assertEquals(investor.getAvailableValuableAmount("zloty"), 10);
        assertThrows(TransactionException.class, () -> investor.transactionBuy("zloty", currency1, 11, currencyMarket));
        assertEquals(investor.getAvailableValuableAmount("zloty"), 10);
        assertDoesNotThrow(() -> investor.transactionBuy("zloty", currency2, 1, currencyMarket));
        assertEquals(investor.getAvailableValuableAmount("zloty"), 8);
        assertEquals(investor.getAvailableValuableAmount("ruble"), 1);

        assertDoesNotThrow(() -> investor.transactionSell(currency2, 1, currencyMarket));
        assertEquals(investor.getAvailableValuableAmount("zloty"), 10);
        assertEquals(investor.getAvailableValuableAmount("ruble"), 0);

        clearCurrencyMarket();
    }
}
