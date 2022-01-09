package main.controls;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.markets.StockMarket;
import app.valuables.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ControlPanelTest {

    StockMarket firstStockMarket;
    StockMarket secondStockMarket;
    StockMarket thirdStockMarket;
    CurrencyMarket firstCurrencyMarket;
    CurrencyMarket secondCurrencyMarket;
    CommodityMarket firstCommodityMarket;
    CommodityMarket secondCommodityMarket;
    @Test
    void ConstructorTest(){
        assertNotEquals(ControlPanel.getInstance(), null);
        assertEquals(ControlPanel.getInstance(), ControlPanel.getInstance());

    }
    @Test
    void MarketCollectionsTest()
    {
        AddFewMarkets();

        assertTrue(ControlPanel.getInstance().MarketExist("polish Stock"));
        assertTrue(ControlPanel.getInstance().MarketExist("russian Stock"));
        assertTrue(ControlPanel.getInstance().MarketExist("abc currency market"));
        assertTrue(ControlPanel.getInstance().MarketExist("123 currency market"));
        assertTrue(ControlPanel.getInstance().MarketExist("abc commodity market"));
        assertTrue(ControlPanel.getInstance().MarketExist("123 commodity market"));

        assertFalse(ControlPanel.getInstance().MarketExist("xd"));
        assertFalse(ControlPanel.getInstance().MarketExist("polish Stock2"));
        assertFalse(ControlPanel.getInstance().MarketExist("polish3 Stock"));
        RemoveFewMarkets();
    }
    void AddFewMarkets()
    {
        ControlPanel.getInstance().removeCurrency(ControlPanel.getInstance().getCurrency("zloty"));
        ControlPanel.getInstance().removeCurrency(ControlPanel.getInstance().getCurrency("ruble"));
        try {
            if(!ControlPanel.getInstance().CurrencyExist("zloty")) {
                ControlPanel.getInstance().addCurrency(new Currency("zloty", 1, new ArrayList<>()));
            }
            if(!ControlPanel.getInstance().CurrencyExist("ruble")) {
                ControlPanel.getInstance().addCurrency(new Currency("ruble", 1, new ArrayList<>()));
            }
        }catch(WrongValuableParamException e)
        {
            System.out.println(e.getMessage());
        }
        try {
            firstStockMarket = new StockMarket("polish Stock", 1.0f, "zloty", new ArrayList<>(), "Poland", "Warsaw", "idk", new ArrayList<>());
            secondStockMarket = new StockMarket("polish2 Stock", 1.0f, "zloty", new ArrayList<>(), "Poland", "Warsaw", "idk", new ArrayList<>());
            thirdStockMarket = new StockMarket("russian Stock", 5.0f, "ruble", new ArrayList<>(), "Russia", "Moscow", "idk", new ArrayList<>());

            firstCurrencyMarket = new CurrencyMarket("abc currency market", 1.0f, "zloty", new ArrayList<>(), new ArrayList<>());
            secondCurrencyMarket = new CurrencyMarket("123 currency market", 1.2f, "ruble", new ArrayList<>(), new ArrayList<>());

            firstCommodityMarket = new CommodityMarket("abc commodity market", 1.0f, "zloty", new ArrayList<>(), new ArrayList<>());
            secondCommodityMarket = new CommodityMarket("123 commodity market", 1.0f, "zloty", new ArrayList<>(), new ArrayList<>());
        }catch (WrongMarketParamException e)
        {
            System.out.println(e.getMessage());
        }
        ControlPanel.getInstance().addStockMarket(firstStockMarket);
        ControlPanel.getInstance().addStockMarket(secondStockMarket);
        ControlPanel.getInstance().addStockMarket(thirdStockMarket);

        ControlPanel.getInstance().addCurrencyMarket(firstCurrencyMarket);
        ControlPanel.getInstance().addCurrencyMarket(secondCurrencyMarket);

        ControlPanel.getInstance().addCommodityMarket(firstCommodityMarket);
        ControlPanel.getInstance().addCommodityMarket(secondCommodityMarket);
    }
    void RemoveFewMarkets()
    {
        ControlPanel.getInstance().removeStockMarket(firstStockMarket);
        ControlPanel.getInstance().removeStockMarket(secondStockMarket);
        ControlPanel.getInstance().removeStockMarket(thirdStockMarket);

        ControlPanel.getInstance().removeCurrencyMarket(firstCurrencyMarket);
        ControlPanel.getInstance().removeCurrencyMarket(secondCurrencyMarket);

        ControlPanel.getInstance().removeCommodityMarket(firstCommodityMarket);
        ControlPanel.getInstance().removeCommodityMarket(secondCommodityMarket);
    }
}
