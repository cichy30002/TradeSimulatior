package main.world;

import app.controls.ControlPanel;
import app.exceptions.TransactionException;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CommodityMarket;
import app.valuables.Commodity;
import app.valuables.Currency;
import app.world.InvestmentFund;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvestmentFundTest {
    CommodityMarket commodityMarket;
    Currency currency;
    Commodity commodity1;
    Commodity commodity2;
    Commodity commodity3;
    ArrayList<String> commodities;

    @Test
    public void constructorTest()
    {
        assertTrue(clientIDCheck());
    }

    private boolean clientIDCheck()
    {
        ArrayList<String> clientIDs = new ArrayList<>();
        InvestmentFund tmp;
        for(int i =0; i < 1000;i++)
        {
            tmp = new InvestmentFund(String.valueOf(i), "x", "y");
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
        InvestmentFund investmentFund = new InvestmentFund("test", "x", "y");
        assertThrows(TransactionException.class, () -> investmentFund.addFunds("xd", 10));
        assertThrows(TransactionException.class, () -> investmentFund.addFunds(currency.getName(), -10));
        assertDoesNotThrow(() -> investmentFund.addFunds(currency.getName(), 10));
        assertEquals(investmentFund.getAvailableValuableAmount("zloty"), 10);
        assertThrows(TransactionException.class, () -> investmentFund.transactionBuy("xd", commodity1, 10, commodityMarket));
        assertThrows(TransactionException.class, () -> investmentFund.transactionBuy("zloty", currency, 10, commodityMarket));
        assertThrows(TransactionException.class, () -> investmentFund.transactionBuy("zloty", commodity3, 10, commodityMarket));
        assertThrows(TransactionException.class, () -> investmentFund.transactionBuy("zloty", commodity1, -10, commodityMarket));
        assertThrows(TransactionException.class, () -> investmentFund.transactionBuy("zloty", commodity1, 0, commodityMarket));

        clearCurrencyMarket();
    }
    void makeCurrencyMarket()
    {
        ArrayList<String> countries = new ArrayList<>();
        countries.add("Poland");
        try {
            currency = new Currency("zloty", 20, countries);
            commodity1 = new Commodity("gold", 1, "ounce", 1, 5);
            commodity2 = new Commodity("silver", 2, "ounce", 1, 5);
            commodity3 = new Commodity("platinum", 3, "ounce", 1, 5);
        } catch (WrongValuableParamException e) {
            System.out.println("Failed makeCurrencyMarket" + e.getMessage());
        }
        commodities = new ArrayList<>();
        commodities.add(commodity1.getName());
        commodities.add(commodity2.getName());
        try {
            commodityMarket = new CommodityMarket("kamyki3", 0.2f, currency.getName(), commodities);
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
        InvestmentFund investmentFund = new InvestmentFund("test", "x", "y");
        assertThrows(TransactionException.class, () -> investmentFund.addFunds("xd", 10));
        assertDoesNotThrow(() -> investmentFund.addFunds(currency.getName(), 10));
        assertEquals(investmentFund.getAvailableValuableAmount("zloty"), 10);
        assertThrows(TransactionException.class, () -> investmentFund.transactionBuy("zloty", commodity1, 11, commodityMarket));
        assertEquals(investmentFund.getAvailableValuableAmount("zloty"), 10);
        assertDoesNotThrow(() -> investmentFund.transactionBuy("zloty", commodity1, 1, commodityMarket));
        assertEquals(investmentFund.getAvailableValuableAmount("zloty"), 9);
        assertEquals(investmentFund.getAvailableValuableAmount(commodity1.getName()), 1);

        assertDoesNotThrow(() -> investmentFund.transactionSell(commodity1,1,commodityMarket));
        assertEquals(investmentFund.getAvailableValuableAmount("zloty"), 10);
        assertEquals(investmentFund.getAvailableValuableAmount(commodity1.getName()), 0);

        clearCurrencyMarket();
    }
}
