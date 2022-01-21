package main.markets;

import app.controls.ControlPanel;
import app.exceptions.MarketCollectionException;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.StockMarket;
import app.valuables.Currency;
import app.valuables.Index;
import app.valuables.Share;
import app.world.Company;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockMarketTest {
    StockMarket stockMarket;
    Share share1;
    Share share2;
    Company company1;
    Company company2;
    Index index;
    Currency currency;
    ArrayList<String> shares;
    ArrayList<String> indexes;


    @Test
    public void constructorTest()
    {
        makeStockMarket();

        assertThrows(WrongMarketParamException.class, ()-> new StockMarket("polish Stock", 1.0f, currency.getName(), shares, "Poland", "Warsaw", "idk", indexes));
        assertDoesNotThrow(()-> new StockMarket("polish Stock2", 1.0f, currency.getName(), shares, "Poland", "Warsaw", "idk", indexes));
        ControlPanel.getInstance().removeStockMarket("polish Stock2");
        assertThrows(WrongMarketParamException.class, ()-> new StockMarket("polish Stock2", -1.0f, currency.getName(), shares, "Poland", "Warsaw", "idk", indexes));
        assertThrows(WrongMarketParamException.class, ()-> new StockMarket("polish Stock2", 1.0f, "xd", shares, "Poland", "Warsaw", "idk", indexes));
        assertThrows(WrongMarketParamException.class, ()-> new StockMarket("", 1.0f, currency.getName(), shares, "Poland", "Warsaw", "idk", indexes));

        assertThrows(WrongMarketParamException.class, ()-> new StockMarket("polish Stock2", 1.0f, currency.getName(), new ArrayList<>(), "Poland", "Warsaw", "idk", indexes));

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
            share2 = new Share("not CD Projekt SA", 34);
            index = new Index("CD Projekt SA x2", 200, companies);
            ArrayList<String> countries = new ArrayList<>();
            countries.add("Poland");
            currency = new Currency("zloty", 5, countries);
        } catch (WrongValuableParamException e) {
            System.out.println("Failed makeStockMarket");
        }
        shares= new ArrayList<>();
        shares.add(share1.getName());
        shares.add(share2.getName());
        indexes = new ArrayList<>();
        indexes.add(index.getName());

        try {
            stockMarket = new StockMarket("polish Stock", 0.08f, currency.getName(), shares, "Poland", "Warsaw", "idk", indexes);
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
    }
    @Test
    public void updateTest()
    {
        makeStockMarket();

        try {
            assertEquals(1, stockMarket.getProductPrice(share2.getName()));
            stockMarket.updatePrices();
            assertEquals(8, stockMarket.getProductPrice(share2.getName()));
        } catch (MarketCollectionException e) {
            e.printStackTrace();
        }
        clearStockMarket();
    }
}
