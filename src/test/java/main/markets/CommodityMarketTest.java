package main.markets;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CommodityMarket;
import app.valuables.Currency;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommodityMarketTest {
    @Test
    public void constructorTest()
    {
        ControlPanel.getInstance().removeCurrency(ControlPanel.getInstance().getCurrency("zloty"));
        assertThrows(WrongMarketParamException.class, () -> new CommodityMarket("abcd commodity market", 1.0f, "zloty", new ArrayList<>(), new ArrayList<>()));
        try {
            if(!ControlPanel.getInstance().currencyExist("zloty")) {
                ControlPanel.getInstance().addCurrency(new Currency("zloty", 1, new ArrayList<>()));
            }
        }catch(WrongValuableParamException e)
        {
            System.out.println(e.getMessage());
        }
        assertDoesNotThrow(()->new CommodityMarket("abcd commodity market", 1.0f, "zloty", new ArrayList<>(), new ArrayList<>()));
        assertThrows(WrongMarketParamException.class, () -> new CommodityMarket("abcd commodity market", 1.0f, "zloty", new ArrayList<>(), new ArrayList<>()));
    }
}
