package main.markets;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CurrencyMarket;
import app.markets.StockMarket;
import app.valuables.Currency;
import app.valuables.Index;
import app.valuables.Share;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyMarketTest {
    @Test
    public void constructorTest()
    {
        ControlPanel.getInstance().removeCurrency(ControlPanel.getInstance().getCurrency("zloty"));
        assertThrows(WrongMarketParamException.class, () -> new CurrencyMarket("abcd currency market", 1.0f, "zloty", new ArrayList<>(), new ArrayList<>()));
        try {
            if(!ControlPanel.getInstance().CurrencyExist("zloty")) {
                ControlPanel.getInstance().addCurrency(new Currency("zloty", 1, new ArrayList<>()));
            }
        }catch(WrongValuableParamException e)
        {
            System.out.println(e.getMessage());
        }
        assertDoesNotThrow(()->new CurrencyMarket("abcd currency market", 1.0f, "zloty", new ArrayList<>(), new ArrayList<>()));
        assertThrows(WrongMarketParamException.class, () -> new CurrencyMarket("abcd currency market", 1.0f, "zloty", new ArrayList<>(), new ArrayList<>()));
    }
}
