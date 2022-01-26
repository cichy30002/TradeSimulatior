package main.controls;

import app.controls.ControlPanel;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.markets.StockMarket;
import app.valuables.*;
import app.world.Company;
import app.world.InvestmentFound;
import app.world.Investor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ControlPanelTest {

    StockMarket firstStockMarket;
    StockMarket secondStockMarket;
    StockMarket thirdStockMarket;
    CurrencyMarket firstCurrencyMarket;
    CurrencyMarket secondCurrencyMarket;
    CommodityMarket firstCommodityMarket;
    CommodityMarket secondCommodityMarket;

    Currency firstCurrency;
    Currency secondCurrency;
    Commodity firstCommodity;
    Commodity secondCommodity;
    Company firstCompany;
    Company secondCompany;
    Share firstShare;
    Share secondShare;
    Index firstIndex;
    Index secondIndex;
    Investor secondInvestor;
    Investor firstInvestor;
    InvestmentFound firstInvestmentFound;
    InvestmentFound secondInvestmentFound;
    Company company;
    Share share;
    ArrayList<String> shares;
    Currency currency1;
    Currency currency2;
    ArrayList<String> currencies;
    Commodity commodity;
    ArrayList<String> commodities;
    ArrayList<String> prices;
    ArrayList<String> prices1;

    @Test
    void constructorTest(){
        assertNotEquals(ControlPanel.getInstance(), null);
        assertEquals(ControlPanel.getInstance(), ControlPanel.getInstance());
    }
    @Test
    void marketCollectionsTest()
    {
        addFewMarkets();

        assertTrue(ControlPanel.getInstance().marketExist("polish Stock"));
        assertTrue(ControlPanel.getInstance().marketExist("russian Stock"));
        assertTrue(ControlPanel.getInstance().marketExist("abc currency market"));
        assertTrue(ControlPanel.getInstance().marketExist("123 currency market"));
        assertTrue(ControlPanel.getInstance().marketExist("abc commodity market"));
        assertTrue(ControlPanel.getInstance().marketExist("123 commodity market"));

        assertFalse(ControlPanel.getInstance().marketExist("xd"));
        assertFalse(ControlPanel.getInstance().marketExist("polish Stock2"));
        assertFalse(ControlPanel.getInstance().marketExist("polish3 Stock"));
        removeFewMarkets();
    }
    void addFewMarkets()
    {
        addValuablesForMarkets();
        try {
            firstStockMarket = new StockMarket("polish Stock", 1.0f, "zloty", shares, "Poland", "Warsaw", "idk", new ArrayList<>());
            secondStockMarket = new StockMarket("polish2 Stock", 1.0f, "zloty", shares, "Poland", "Warsaw", "idk", new ArrayList<>());
            thirdStockMarket = new StockMarket("russian Stock", 5.0f, "ruble", shares, "Russia", "Moscow", "idk", new ArrayList<>());

            firstCurrencyMarket = new CurrencyMarket("abc currency market", 1.0f, "zloty", currencies);
            secondCurrencyMarket = new CurrencyMarket("123 currency market", 1.2f, "ruble", currencies);

            firstCommodityMarket = new CommodityMarket("abc commodity market", 1.0f, "zloty", commodities);
            secondCommodityMarket = new CommodityMarket("123 commodity market", 1.0f, "zloty", commodities);
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

        ControlPanel.getInstance().removeCurrency("zloty");
        ControlPanel.getInstance().removeCurrency("ruble");
    }

    private void addValuablesForMarkets() {
        try{
            company  = new Company("CD Projekt SA", "13.09.1775", 30, 40, 50, 30, 687.9f, 789.0f,56.8f, 300, 132.4f);
        }catch (WrongValuableParamException e)
        {
            System.out.println(e.getMessage());
        }
        try {
            ArrayList<String> countries = new ArrayList<>();
            countries.add("Poland");
            currency1 = new Currency("zloty", 20, countries);
            currency2 = new Currency("ruble", 20, countries);
            share = new Share("CD Projekt SA", 50);
            commodity = new Commodity("silver", 4000, "ounce", 3000, 5000);
        } catch (WrongValuableParamException e) {
            System.out.println("Failed addSharesForStockMarket");
        }
        shares = new ArrayList<>();
        shares.add(share.getName());
        currencies = new ArrayList<>();
        currencies.add(currency1.getName());
        currencies.add(currency2.getName());
        commodities = new ArrayList<>();
        commodities.add(commodity.getName());
        prices = new ArrayList<>();
        prices.add("1");
        prices.add("1");
        prices1 = new ArrayList<>();
        prices1.add("1");
    }

    void removeFewMarkets()
    {
        ControlPanel.getInstance().removeStockMarket(firstStockMarket.getName());
        ControlPanel.getInstance().removeStockMarket(secondStockMarket.getName());
        ControlPanel.getInstance().removeStockMarket(thirdStockMarket.getName());

        ControlPanel.getInstance().removeCurrencyMarket(firstCurrencyMarket.getName());
        ControlPanel.getInstance().removeCurrencyMarket(secondCurrencyMarket.getName());

        ControlPanel.getInstance().removeCommodityMarket(firstCommodityMarket.getName());
        ControlPanel.getInstance().removeCommodityMarket(secondCommodityMarket.getName());
        removeValuablesForMarkets();
    }

    private void removeValuablesForMarkets() {
        ControlPanel.getInstance().removeShare(share.getName());
        ControlPanel.getInstance().removeCompany(company.getName());
        ControlPanel.getInstance().removeCurrency(currency1.getName());
        ControlPanel.getInstance().removeCurrency(currency2.getName());
        ControlPanel.getInstance().removeCommodity(commodity.getName());
    }

    @Test
    void valuableCollectionsTest()
    {
        addFewValuables();

        assertTrue(ControlPanel.getInstance().currencyExist("zloty"));
        assertTrue(ControlPanel.getInstance().currencyExist("ruble"));

        assertTrue(ControlPanel.getInstance().commodityExist("gold"));
        assertTrue(ControlPanel.getInstance().commodityExist("silver"));

        assertTrue(ControlPanel.getInstance().shareExist("Orlen"));
        assertTrue(ControlPanel.getInstance().shareExist("Lotos"));

        assertTrue(ControlPanel.getInstance().indexExist("pap3"));
        assertTrue(ControlPanel.getInstance().indexExist("pap4"));

        assertFalse(ControlPanel.getInstance().currencyExist("xd"));
        assertFalse(ControlPanel.getInstance().commodityExist("xd"));
        assertFalse(ControlPanel.getInstance().shareExist("xd"));
        assertFalse(ControlPanel.getInstance().indexExist("xd"));

        removeFewValuables();
    }
    private void addFewValuables() {
        try{
            firstCurrency = new Currency("zloty", 1, new ArrayList<>());
            secondCurrency = new Currency("ruble", 1, new ArrayList<>());

            firstCommodity = new Commodity("gold", 1800, "ounce", 100, 10000);
            secondCommodity = new Commodity("silver", 1800, "ounce", 100, 10000);

            firstCompany = new Company("Orlen", "", 1, 1, 10, 1, 1f,2f, 3f, 2, 4f);
            secondCompany = new Company("Lotos", "", 1, 1, 10, 1, 1f,2f, 3f, 2, 4f);
            firstShare = new Share("Orlen", 56);
            secondShare = new Share("Lotos", 56);

            ArrayList<String> firstIndexList = new ArrayList<>();
            firstIndexList.add("Orlen");
            firstIndex = new Index("pap3", 10, firstIndexList);
            firstIndexList.add("Lotos");
            secondIndex = new Index("pap4", 10, firstIndexList);
        } catch (WrongValuableParamException e) {
            e.printStackTrace();
        }

    }

    private void removeFewValuables() {
        ControlPanel.getInstance().removeCurrency(firstCurrency.getName());
        ControlPanel.getInstance().removeCurrency(secondCurrency.getName());

        ControlPanel.getInstance().removeCommodity(firstCommodity.getName());
        ControlPanel.getInstance().removeCommodity(secondCommodity.getName());

        ControlPanel.getInstance().removeShare(firstShare.getName());
        ControlPanel.getInstance().removeShare(secondShare.getName());
        ControlPanel.getInstance().removeCompany(firstCompany.getName());
        ControlPanel.getInstance().removeCompany(secondCompany.getName());

        ControlPanel.getInstance().removeIndex(firstIndex.getName());
        ControlPanel.getInstance().removeIndex(secondIndex.getName());
    }
    @Test
    void marketClientsCollectionsTest()
    {
        addFewMarketClients();

        assertTrue(ControlPanel.getInstance().investorExist("Jan_Kiwlenko"));
        assertTrue(ControlPanel.getInstance().investorExist("Jakub_Cichy"));

        assertTrue(ControlPanel.getInstance().companyExist("Orlen"));
        assertTrue(ControlPanel.getInstance().companyExist("Lotos"));

        assertTrue(ControlPanel.getInstance().investmentFoundExist("great_idea"));
        assertTrue(ControlPanel.getInstance().investmentFoundExist("bad_idea"));

        assertFalse(ControlPanel.getInstance().investorExist("xd"));
        assertFalse(ControlPanel.getInstance().companyExist("xd"));
        assertFalse(ControlPanel.getInstance().investmentFoundExist("xd"));

        removeFewMarketClients();
    }
    private void addFewMarketClients() {
        firstInvestor = new Investor("Jan_Kiwlenko");
        secondInvestor = new Investor("Jakub_Cichy");

        try{
            firstCompany = new Company("Orlen", "", 1, 1, 10, 1, 1f,2f, 3f, 2, 4f);
            secondCompany = new Company("Lotos", "", 1, 1, 10, 1, 1f,2f, 3f, 2, 4f);
        }catch (WrongValuableParamException e)
        {
            System.out.println(e.getMessage());
        }
        try {
            firstShare = new Share("Orlen", 56);
            secondShare = new Share("Lotos", 56);
        } catch (WrongValuableParamException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<>();
        list.add("Orlen");
        firstInvestmentFound = new InvestmentFound("great_idea", list, 3, "x", "y");
        list.add("Lotos");
        secondInvestmentFound = new InvestmentFound("bad_idea", list, 10, "dsf", "sdgf");
    }

    private void removeFewMarketClients() {
        ControlPanel.getInstance().removeInvestor(firstInvestor.getName());
        ControlPanel.getInstance().removeInvestor(secondInvestor.getName());
        ControlPanel.getInstance().removeCompany(firstCompany.getName());
        ControlPanel.getInstance().removeCompany(secondCompany.getName());
        ControlPanel.getInstance().removeShare(firstShare.getName());
        ControlPanel.getInstance().removeShare(secondShare.getName());
        ControlPanel.getInstance().removeInvestmentFound(firstInvestmentFound.getName());
        ControlPanel.getInstance().removeInvestmentFound(secondInvestmentFound.getName());
    }


}
