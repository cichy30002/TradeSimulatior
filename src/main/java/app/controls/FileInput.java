package app.controls;

import app.exceptions.TransactionException;
import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.markets.StockMarket;
import app.valuables.Commodity;
import app.valuables.Currency;
import app.valuables.Index;
import app.valuables.Share;
import app.world.Company;
import app.world.InvestmentFund;
import app.world.Investor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileInput {
    /**
     * Reads input from special file named "BasicInput.txt".
     * Create all types of instances and add them to simulation
     */
    public static void readFromBasicFile()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/inputFiles/BasicInput.txt"))){
            readInput(reader, "MarketClient", "Company");
            readInput(reader, "MarketClient", "Investor");

            readInput(reader, "Valuable", "Currency");
            readInput(reader, "Valuable", "Commodity");
            readInput(reader, "Valuable", "Index");

            readInput(reader, "Market", "Currency");
            readInput(reader, "Market", "Commodity");
            readInput(reader, "Market", "Stock");

            readInput(reader, "MarketClient", "InvestmentFund");
        } catch (IOException | WrongValuableParamException | WrongMarketParamException e) {
            e.printStackTrace();
        }
        for(Investor investor : ControlPanel.getInstance().getAllInvestors())
        {
            ControlPanel.getInstance().getGenerator().fillMarketClientWallet(investor);
        }
        for(Company company : ControlPanel.getInstance().getAllCompanies())
        {
            ControlPanel.getInstance().getGenerator().fillMarketClientWallet(company);
        }
    }



    private static void readInput(BufferedReader reader, String type, String specificType) throws IOException, WrongValuableParamException, WrongMarketParamException {

        int noValuables = Integer.parseInt(reader.readLine());
        for (int i=0; i<noValuables; i++)
        {
            String[] params = reader.readLine().split(" ");
            switch (type) {
                case "Valuable" -> createValuable(params, specificType);
                case "Market" -> createMarket(params, specificType);
                case "MarketClient" -> createMarketClient(params, specificType);
                default -> System.out.println("failed switch in file input");
            }

        }
    }

    private static void createMarketClient(String[] params, String type) {
        switch (type)
        {
            case "Company":
                try {
                Company company = new Company(params[0], params[1], Integer.parseInt(params[2]), Integer.parseInt(params[3]),
                        Integer.parseInt(params[4]), Integer.parseInt(params[5]), Float.parseFloat(params[6]), Float.parseFloat(params[7]),
                        Float.parseFloat(params[8]), Integer.parseInt(params[9]), Integer.parseInt(params[10]));
                }catch (WrongValuableParamException e)
                {
                    System.out.println(e.getMessage());
                }
                break;
            case "Investor":
                Investor investor = new Investor(params[0]);
                break;
            case "InvestmentFund":
                InvestmentFund investmentFund = new InvestmentFund(params[0],params[1],params[2]);
                break;
            default:
                System.out.println("failed switch in file input");
                break;

        }
    }

    private static void createMarket(String[] params, String type) throws WrongMarketParamException {
        switch(type)
        {
            case "Currency":
                CurrencyMarket newCurrencyMarket = new CurrencyMarket(params[0], Float.parseFloat(params[1]), params[2],
                        new ArrayList<>(List.of(params[3].split(";"))));
                break;
            case "Commodity":

                CommodityMarket newCommodityMarket = new CommodityMarket(params[0], Float.parseFloat(params[1]), params[2],
                        new ArrayList<>(List.of(params[3].split(";"))));
                break;
            case "Stock":
                StockMarket newStockMarket = new StockMarket(params[0], Float.parseFloat(params[1]), params[2],
                        new ArrayList<>(List.of(params[3].split(";"))), params[4], params[5], params[6],
                        new ArrayList<>(List.of(params[7].split(";"))));
                break;
            default:
                System.out.println("failed switch in file input");
                break;
        }
    }

    private static void createValuable(String[] params, String type) throws WrongValuableParamException {
        switch(type)
        {
            case "Currency":
                Currency newCurrency = new Currency(params[0], Integer.parseInt(params[1]), new ArrayList<>(List.of(params[2].split(";"))));
                break;
            case "Commodity":
                Commodity newCommodity = new Commodity(params[0], Integer.parseInt(params[1]), params[2], Integer.parseInt(params[3]), Integer.parseInt(params[4]));
                break;
            case "Share":
                Share newShare = new Share(params[0], Integer.parseInt(params[1]));
                break;
            case "Index":
                Index index = new Index(params[0], Integer.parseInt(params[1]), new ArrayList<>(List.of(params[2].split(";"))));
                break;
            default:
                System.out.println("failed switch in file input");
                break;
        }
    }

}
