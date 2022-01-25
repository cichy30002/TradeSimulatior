package main.world;

import app.controls.ControlPanel;
import app.exceptions.TransactionException;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.StockMarket;
import app.valuables.Commodity;
import app.valuables.Currency;
import app.valuables.Index;
import app.valuables.Share;
import app.world.Company;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyTest {
    StockMarket stockMarket;
    Company company1;
    Company company2;
    Share share1;
    Share share2;
    Share share3;
    Index index;
    Currency currency;
    Commodity commodity;
    ArrayList<String> shares;
    ArrayList<String> indexes;

    @Test
    public void constructorTest()
    {
        assertTrue(clientIDCheck());
    }

    private boolean clientIDCheck()
    {
        ArrayList<String> clientIDs = new ArrayList<>();
        Company tmp;
        for(int i =0; i < 1000;i++)
        {
            tmp = new Company(String.valueOf(i), "13.09.1775", 30, 40, 50, 30, 687.9f, 789.0f,56.8f, 300, 132.4f);
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
        makeStockMarket();
        assertThrows(TransactionException.class, () -> company1.addFunds("xd", 10));
        assertThrows(TransactionException.class, () -> company1.addFunds("zloty", -10));
        assertDoesNotThrow(() -> company1.addFunds(currency.getName(), 10));
        assertEquals(company1.getAvailableValuableAmount("zloty"), 10);
        assertThrows(TransactionException.class, () -> company1.transactionBuy("xd", share1, 10, stockMarket));
        assertThrows(TransactionException.class, () -> company1.transactionBuy("zloty", commodity, 10, stockMarket));
        assertThrows(TransactionException.class, () -> company1.transactionBuy("zloty", share1, -10, stockMarket));
        assertThrows(TransactionException.class, () -> company1.transactionBuy("zloty", share1, 0, stockMarket));

        clearStockMarket();
    }
    void makeStockMarket()
    {
        company1  = new Company("CD Projekt SA", "13.09.1775", 30, 40, 50, 30, 687.9f, 789.0f,56.8f, 300, 132.4f);
        company2  = new Company("not CD Projekt SA", "13.09.1775", 30, 40, 50, 30, 687.9f, 789.0f,56.8f, 300, 132.4f);
        ArrayList<String> companies = new ArrayList<>();
        companies.add("CD Projekt SA");
        companies.add("not CD Projekt SA");
        try {
            share1 = new Share("CD Projekt SA", 50);
            share2 = new Share("not CD Projekt SA", 30);
            index = new Index("CD Projekt SA x2", 200, companies);
            ArrayList<String> countries = new ArrayList<>();
            countries.add("Poland");
            currency = new Currency("zloty", 20, countries);
            commodity = new Commodity("gold", 123456, "ounce", 123000, 124000);
        } catch (WrongValuableParamException e) {
            System.out.println("Failed makeStockMarket");
        }
        shares= new ArrayList<>();
        shares.add(share1.getName());
        shares.add(share2.getName());
        indexes = new ArrayList<>();
        indexes.add(index.getName());
        ArrayList<String> sharePrices = new ArrayList<>();
        sharePrices.add("1");
        sharePrices.add("2");
        ArrayList<String> indexPrices = new ArrayList<>();
        indexPrices.add("1");
        try {
            stockMarket = new StockMarket("polish Stock", 1.0f, currency.getName(), shares, "Poland", "Warsaw", "idk", indexes);
        } catch (WrongMarketParamException e) {
            System.out.println("Failed makeStockMarket");
        }
    }
    void clearStockMarket()
    {
        ControlPanel.getInstance().removeCompany(company1.getName());
        ControlPanel.getInstance().removeCompany(company2.getName());
        ControlPanel.getInstance().removeShare(share1.getName());
        ControlPanel.getInstance().removeShare(share2.getName());
        ControlPanel.getInstance().removeIndex(index.getName());
        ControlPanel.getInstance().removeCurrency(currency.getName());

        ControlPanel.getInstance().removeStockMarket(stockMarket.getName());
        ControlPanel.getInstance().removeCommodity(commodity.getName());
    }
    @Test
    public void transactionTest()
    {
        makeStockMarket();
        assertThrows(TransactionException.class, () -> company1.addFunds("xd", 10));
        assertDoesNotThrow(() -> company1.addFunds(currency.getName(), 10));
        assertEquals(company1.getAvailableValuableAmount("zloty"), 10);
        assertThrows(TransactionException.class, () -> company1.transactionBuy("zloty", share1, 11, stockMarket));
        assertEquals(company1.getAvailableValuableAmount("zloty"), 10);
        assertDoesNotThrow(() -> company1.transactionBuy("zloty", share2, 1, stockMarket));
        assertTrue(company1.getAvailableValuableAmount("zloty") <= 8);
        assertEquals(company1.getAvailableValuableAmount(share2.getName()), 1);

        assertDoesNotThrow(() -> company1.transactionSell( share2, 1, stockMarket));
        assertEquals(company1.getAvailableValuableAmount("zloty"), 10);
        assertEquals(company1.getAvailableValuableAmount(share2.getName()), 0);
        clearStockMarket();
    }
}
