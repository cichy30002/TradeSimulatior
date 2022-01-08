package main.controls;

import app.controls.ControlPanel;
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
        firstStockMarket = new StockMarket("polish Stock", 1.0f, "zloty", new ArrayList<Share>(), "Poland", "Warsaw", "idk", new ArrayList<Index>());
        secondStockMarket = new StockMarket("polish2 Stock", 1.0f, "zloty", new ArrayList<Share>(), "Poland", "Warsaw", "idk", new ArrayList<Index>());
        thirdStockMarket = new StockMarket("russian Stock", 5.0f, "ruble", new ArrayList<Share>(), "Russia", "Moscow", "idk", new ArrayList<Index>());

        firstCurrencyMarket = new CurrencyMarket("abc currency market", 1.0f, "zloty", new ArrayList<Currency>(), new ArrayList<Integer>());
        secondCurrencyMarket = new CurrencyMarket("123 currency market", 1.2f, "ruble", new ArrayList<Currency>(), new ArrayList<Integer>());

        firstCommodityMarket = new CommodityMarket("abc commodity market", 1.0f, "zloty", new ArrayList<Commodity>(), new ArrayList<Integer>());
        secondCommodityMarket = new CommodityMarket("123 commodity market", 1.0f, "zloty", new ArrayList<Commodity>(), new ArrayList<Integer>());

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
