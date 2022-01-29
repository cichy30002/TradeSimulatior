package main.markets;

import app.controls.ControlPanel;
import app.exceptions.MarketCollectionException;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CommodityMarket;
import app.valuables.Commodity;
import app.valuables.Currency;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CommodityMarketTest {
    CommodityMarket commodityMarket;
    Commodity commodity1;
    Commodity commodity2;
    Currency currency;
    ArrayList<String> commodities;
    ArrayList<String> prices;

    @Test
    public void constructorTest()
    {
        makeCommodityMarket();

        assertThrows(WrongMarketParamException.class, ()-> new CommodityMarket("blyskotki", 1f, "zloty", commodities));
        assertDoesNotThrow(()-> new CommodityMarket("blyskotkiw", 1f, "zloty", commodities));
        ControlPanel.getInstance().removeCommodityMarket("blyskotki2");
        assertThrows(WrongMarketParamException.class, ()-> new CommodityMarket("blyskotki2", -1f, "zloty", commodities));
        assertThrows(WrongMarketParamException.class, ()-> new CommodityMarket("blyskotki2", 1f, "xd", commodities));
        assertThrows(WrongMarketParamException.class, ()-> new CommodityMarket("", 1f, "zloty", commodities));

        assertThrows(WrongMarketParamException.class, ()-> new CommodityMarket("blyskotki", 1f, "zloty", new ArrayList<>()));

        clearCommodityMarket();
    }
    void makeCommodityMarket()
    {
        ArrayList<String> countries = new ArrayList<>();
        countries.add("Poland");
        try {
            currency = new Currency("zloty", 100, countries);
            commodity1 = new Commodity("gold", 44587, "ounce", 30000, 50000);
            commodity2 = new Commodity("silver", 4000, "ounce", 3000, 5000);
        } catch (WrongValuableParamException e) {
            System.out.println("Failed makeCurrencyMarket");
        }
        commodities = new ArrayList<>();
        commodities.add(commodity1.getName());
        commodities.add(commodity2.getName());
        prices = new ArrayList<>();
        prices.add("1");
        prices.add("2");
        try {
            commodityMarket = new CommodityMarket("blyskotki", 0.02f, "zloty", commodities);
        } catch (WrongMarketParamException e) {
            System.out.println("Failed makeCurrencyMarket");
        }
    }
    void clearCommodityMarket()
    {
        ControlPanel.getInstance().removeCurrency(currency.getName());
        ControlPanel.getInstance().removeCommodity(commodity1.getName());
        ControlPanel.getInstance().removeCommodity(commodity2.getName());
        ControlPanel.getInstance().removeCommodityMarket(commodityMarket.getName());
    }
    @Test
    public void updateTest()
    {
        makeCommodityMarket();

        try {
            assertTrue(455 < commodityMarket.getProductPriceBuy(commodity1.getName()));
        } catch (MarketCollectionException e) {
            e.printStackTrace();
        }
        clearCommodityMarket();
    }
}
