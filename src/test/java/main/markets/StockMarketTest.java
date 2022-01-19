package main.markets;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.StockMarket;
import app.valuables.Currency;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StockMarketTest {
    @Test
    public void constructorTest()
    {
        ControlPanel.getInstance().removeCurrency("zloty");
        assertThrows(WrongMarketParamException.class, () -> new StockMarket("polish Stock3", 1.0f, "zloty", new ArrayList<>(), "Poland", "Warsaw", "idk", new ArrayList<>()));
        try {
            if(!ControlPanel.getInstance().currencyExist("zloty")) {
                ControlPanel.getInstance().addCurrency(new Currency("zloty", 1, new ArrayList<>()));
            }
        }catch(WrongValuableParamException e)
        {
            System.out.println(e.getMessage());
        }
        assertDoesNotThrow(()->new StockMarket("polish stock3", 1.0f, "zloty", new ArrayList<>(), "Poland", "Warsaw", "idk", new ArrayList<>()));
        assertThrows(WrongMarketParamException.class, () -> new StockMarket("polish stock3", 1.0f, "zloty", new ArrayList<>(), "Poland", "Warsaw", "idk", new ArrayList<>()));
    }
}
