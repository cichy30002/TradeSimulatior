package app.controls;

import app.exceptions.WrongMarketParamException;
import app.exceptions.WrongValuableParamException;
import app.markets.CommodityMarket;
import app.markets.CurrencyMarket;
import app.markets.StockMarket;
import app.valuables.Commodity;
import app.valuables.Currency;
import app.valuables.Index;
import app.valuables.Share;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileInput {
    public static void readFromBasicFile()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("BasicInput.txt"))){
            //TODO: read companies and investors first
            readInput(reader, "Valuable", "Currency");
            readInput(reader, "Valuable", "Commodity");
            readInput(reader, "Valuable", "Share");
            readInput(reader, "Valuable", "Index");

            readInput(reader, "Market", "Currency");
            readInput(reader, "Market", "Commodity");
            readInput(reader, "Market", "Stock");
        } catch (IOException | WrongValuableParamException e) {
            e.printStackTrace();
        }
    }



    private static void readInput(BufferedReader reader, String type, String specificType) throws IOException, WrongValuableParamException {
        int noValuables = Integer.parseInt(reader.readLine());
        for (int i=0; i<noValuables; i++)
        {
            String[] params = reader.readLine().split(" ");
            switch (type) {
                case "Valuable" -> createValuable(params, specificType);
                case "Market" -> createMarket(params, specificType);
                default -> System.out.println("failed switch in file input");
            }

        }
    }

    private static void createMarket(String[] params, String type) {
        switch(type)
        {
            case "Currency":
                try {
                    CurrencyMarket newCurrencyMarket = new CurrencyMarket(params[0], Float.parseFloat(params[1]), params[2],
                            new ArrayList<>(List.of(params[3].split(";"))), new ArrayList<>(List.of(params[4].split(";"))));
                }catch (WrongMarketParamException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "Commodity":
                try {
                    CommodityMarket newCommodityMarket = new CommodityMarket(params[0], Float.parseFloat(params[1]), params[2],
                            new ArrayList<>(List.of(params[3].split(";"))), new ArrayList<>(List.of(params[4].split(";"))));
                } catch (WrongMarketParamException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "Stock":
                try{
                    StockMarket newStockMarket = new StockMarket(params[0], Float.parseFloat(params[1]), params[2],
                            new ArrayList<>(List.of(params[3].split(";"))), params[4], params[5], params[6],
                            new ArrayList<>(List.of(params[7].split(";"))));
                } catch (WrongMarketParamException e) {
                    System.out.println(e.getMessage());
                }
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
