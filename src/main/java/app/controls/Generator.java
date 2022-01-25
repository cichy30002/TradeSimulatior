package app.controls;

import app.exceptions.AppInputException;

public class Generator {
    public Generator()
    {

    }
    public void generateStockMarket(String name, String marginFee, String currency) throws AppInputException
    {
        System.out.println("making a new stock market: " + name);
    }
    public void generateCurrencyMarket(String name, String marginFee, String currency) throws AppInputException
    {
        System.out.println("making a new currency market: " + name);
    }
    public void generateCommodityMarket(String name, String marginFee, String currency) throws AppInputException
    {
        System.out.println("making a new commodity market:" + name);
    }

    public void generateInvestor(String name)throws AppInputException
    {
        System.out.println("making a new investor:" + name);
    }

    public void generateCompany(String name)throws AppInputException
    {
        System.out.println("making a new company:" + name);
    }

    public void generateInvestmentFound(String name)throws AppInputException
    {
        System.out.println("making a new investment found:" + name);
    }

    public void generateCurrency(String name, String price) throws AppInputException
    {
        System.out.println("making a new currency:" + name);
    }

    public void generateCommodity(String name, String price, String tradingUnit) throws AppInputException
    {
        System.out.println("making a new commodity:" + name);
    }

    public void generateIndex(String name, String price, String market, String noCompanies) throws AppInputException
    {
        System.out.println("making a new index:" + name);
    }
}
