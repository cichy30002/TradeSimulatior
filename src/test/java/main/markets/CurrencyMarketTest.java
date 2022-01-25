package main.markets;

import app.controls.ControlPanel;
import app.exceptions.MarketCollectionException;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CurrencyMarket;
import app.valuables.Currency;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyMarketTest {
    CurrencyMarket currencyMarket;
    Currency currency1;
    Currency currency2;
    Currency currency3;
    ArrayList<String> currencies;
    ArrayList<String> prices;

    @Test
    public void constructorTest()
    {
        makeCurrencyMarket();

        assertThrows(WrongMarketParamException.class, ()-> new CurrencyMarket("monetki", 2.0f, "zloty", currencies));
        assertDoesNotThrow(()-> new CurrencyMarket("monetki2", 2.0f, "zloty", currencies));
        ControlPanel.getInstance().removeCurrencyMarket("monetki2");
        assertThrows(WrongMarketParamException.class, ()-> new CurrencyMarket("monetki2", -2.0f, "zloty", currencies));
        assertThrows(WrongMarketParamException.class, ()-> new CurrencyMarket("monetki2", 2.0f, "xd", currencies));
        assertThrows(WrongMarketParamException.class, ()-> new CurrencyMarket("", 2.0f, "zloty", currencies));

        assertThrows(WrongMarketParamException.class, ()-> new CurrencyMarket("monetki2", 2.0f, "zloty", new ArrayList<>()));

        clearCurrencyMarket();
    }
    void makeCurrencyMarket()
    {
        ArrayList<String> countries = new ArrayList<>();
        countries.add("Poland");
        try {
            currency1 = new Currency("zloty", 20, countries);
            currency2 = new Currency("ruble", 307, countries);
            currency3 = new Currency("euro", 40, countries);
        } catch (WrongValuableParamException e) {
            System.out.println("Failed makeCurrencyMarket");
        }
        currencies = new ArrayList<>();
        currencies.add(currency1.getName());
        currencies.add(currency2.getName());
        currencies.add(currency3.getName());
        prices = new ArrayList<>();
        prices.add("1");
        prices.add("1");
        prices.add("1");
        try {
            currencyMarket = new CurrencyMarket("monetki", 0.05f, "zloty", currencies);
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
    }
    @Test
    public void updateTest()
    {
        makeCurrencyMarket();
        try {
            assertTrue(17 <= currencyMarket.getProductPrice(currency2.getName()));
        } catch (MarketCollectionException e) {
            e.printStackTrace();
        }
        clearCurrencyMarket();
    }
}
