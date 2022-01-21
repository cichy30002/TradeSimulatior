package main.world;

import app.controls.ControlPanel;
import app.exceptions.TransactionException;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.valuables.Commodity;
import app.valuables.Currency;
import app.world.InvestmentFound;
import app.world.Investor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvestmentFoundTest {
    CommodityMarket commodityMarket;
    Currency currency;
    Commodity commodity1;
    Commodity commodity2;
    Commodity commodity3;
    ArrayList<String> commodities;
    ArrayList<String> prices;

    @Test
    public void constructorTest()
    {
        assertTrue(clientIDCheck());
    }

    private boolean clientIDCheck()
    {
        ArrayList<String> clientIDs = new ArrayList<>();
        InvestmentFound tmp;
        for(int i =0; i < 1000;i++)
        {
            tmp = new InvestmentFound(String.valueOf(i), new ArrayList<>(), 10, "x", "y");
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
        InvestmentFound investmentFound = new InvestmentFound("test", commodities, 3, "x", "y");
        assertThrows(TransactionException.class, () -> investmentFound.addFunds("xd", 10));
        assertThrows(TransactionException.class, () -> investmentFound.addFunds(currency.getName(), -10));
        assertDoesNotThrow(() -> investmentFound.addFunds(currency.getName(), 10));
        assertEquals(investmentFound.getAvailableValuableAmount("zloty"), 10);
        assertThrows(TransactionException.class, () -> investmentFound.transaction("xd", commodity1, 10, commodityMarket));
        assertThrows(TransactionException.class, () -> investmentFound.transaction("zloty", currency, 10, commodityMarket));
        assertThrows(TransactionException.class, () -> investmentFound.transaction("zloty", commodity3, 10, commodityMarket));
        assertThrows(TransactionException.class, () -> investmentFound.transaction("zloty", commodity1, -10, commodityMarket));
        assertThrows(TransactionException.class, () -> investmentFound.transaction("zloty", commodity1, 0, commodityMarket));

        clearCurrencyMarket();
    }
    void makeCurrencyMarket()
    {
        ArrayList<String> countries = new ArrayList<>();
        countries.add("Poland");
        try {
            currency = new Currency("zloty", 20, countries);
            commodity1 = new Commodity("gold", 123456, "ounce", 123000, 124000);
            commodity2 = new Commodity("silver", 12345, "ounce", 12300, 12400);
            commodity3 = new Commodity("platinum", 1234567, "ounce", 1230000, 1240000);
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
            commodityMarket = new CommodityMarket("kamyki", 2f, currency.getName(), commodities, prices);
        } catch (WrongMarketParamException e) {
            System.out.println("Failed makeCommodityMarket: " + e.getMessage());
        }
    }
    void clearCurrencyMarket()
    {
        ControlPanel.getInstance().removeCurrency(currency.getName());

        ControlPanel.getInstance().removeCommodityMarket(commodityMarket.getName());
        ControlPanel.getInstance().removeCommodity(commodity1.getName());
        ControlPanel.getInstance().removeCommodity(commodity2.getName());
        ControlPanel.getInstance().removeCommodity(commodity3.getName());
    }
    @Test
    public void transactionTest()
    {
        makeCurrencyMarket();
        InvestmentFound investmentFound = new InvestmentFound("test", commodities, 3, "x", "y");
        assertThrows(TransactionException.class, () -> investmentFound.addFunds("xd", 10));
        assertDoesNotThrow(() -> investmentFound.addFunds(currency.getName(), 10));
        assertEquals(investmentFound.getAvailableValuableAmount("zloty"), 10);
        assertThrows(TransactionException.class, () -> investmentFound.transaction("zloty", commodity1, 11, commodityMarket));
        assertEquals(investmentFound.getAvailableValuableAmount("zloty"), 10);
        assertDoesNotThrow(() -> investmentFound.transaction("zloty", commodity1, 1, commodityMarket));
        assertEquals(investmentFound.getAvailableValuableAmount("zloty"), 9);
        assertEquals(investmentFound.getAvailableValuableAmount(commodity1.getName()), 1);

        clearCurrencyMarket();
    }
}
