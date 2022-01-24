package app.javaFX;

import app.controls.ControlPanel;
import app.controls.FileInput;
import app.markets.Market;
import app.valuables.Valuable;
import app.world.Company;
import app.world.InvestmentFound;
import app.world.Investor;
import app.world.MarketClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class ApplicationController
{
    private static TabPane mainTabs;

    public static void Init(Scene scene)
    {
        FileInput.readFromBasicFile();
        mainTabs = (TabPane) scene.lookup("#MainTabs");
        initMarketsList(scene);
        initInvestorsList(scene);
        initCompaniesList(scene);
        initInvestorFoundsList(scene);
    }

    private static void initMarketsList(Scene scene)
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(ControlPanel.getInstance().getAllMarketNames());
        ListView list = (ListView) scene.lookup("#MarketList");
        list.setItems(items);

        list.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (ov, old_val, new_val) -> mainTabs.getTabs().add(makeMarketTab(new_val)));
    }
    private static Tab makeMarketTab(String marketName)
    {
        Tab tab = new Tab();
        tab.setText(marketName);
        Market market = ControlPanel.getInstance().getMarket(marketName);

        VBox vBox = prepareMarketTabContent(market);
        tab.setContent(vBox);
        return tab;
    }
    private static VBox prepareMarketTabContent(Market market)
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Name: " + market.getName());
        items.add("Currency: "+ market.getCurrency());
        items.add("Margin Fee: " + market.getMarginFee());
        ListView listView = new ListView(items);

        TableView table = new TableView();
        TableColumn name = new TableColumn("Valuable name");
        name.setCellValueFactory(new PropertyValueFactory<ProductWithPrice, String>("productName"));
        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<ProductWithPrice, String>("price"));

        ObservableList<ProductWithPrice> data = FXCollections.observableArrayList();
        for(Pair<String, Integer> pair : market.getProductsWithPrices())
        {
            data.add(new ProductWithPrice(pair.getKey(), pair.getValue()));
        }

        table.setItems(data);
        table.getColumns().addAll(name, price);
        return new VBox(listView, table);
    }
    private static void initInvestorsList(Scene scene)
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(ControlPanel.getInstance().getAllInvestorNames());
        ListView list = (ListView) scene.lookup("#InvestorList");
        list.setItems(items);
        list.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (ov, old_val, new_val) -> mainTabs.getTabs().add(makeInvestorTab(new_val)));
    }

    private static Tab makeInvestorTab(String investorName) {
        Tab tab = new Tab();
        tab.setText(investorName);

        Investor investor = ControlPanel.getInstance().getInvestor(investorName);
        VBox vBox = prepareInvestorTabContent(investor);

        tab.setContent(vBox);
        return tab;
    }

    private static VBox prepareInvestorTabContent(Investor investor) {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Name: " + investor.getName());
        items.add("ClientID: " + investor.getClientID());
        ListView listView = new ListView(items);


        TableView table = makeMarketClientWallet(investor);

        return new VBox(listView, table);
    }

    private static void initCompaniesList(Scene scene)
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(ControlPanel.getInstance().getAllCompanyNames());
        ListView list = (ListView) scene.lookup("#CompanyList");
        list.setItems(items);
        list.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (ov, old_val, new_val) -> mainTabs.getTabs().add(makeCompanyTab(new_val)));
    }

    private static Tab makeCompanyTab(String companyName) {
        Tab tab = new Tab();
        tab.setText(companyName);

        Company company = ControlPanel.getInstance().getCompany(companyName);
        VBox vBox = prepareCompanyTabContent(company);

        tab.setContent(vBox);
        return tab;
    }

    private static VBox prepareCompanyTabContent(Company company) {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Name: " + company.getName());
        items.add("ClientID: " + company.getClientID());
        items.add("IPO Date: " + company.getIPODate());
        items.add("IPO Share value: " + company.getIPOShareValue());
        items.add("Opening price: " + String.format("%.2f",company.getOpeningPrice()/100.0));
        items.add("Maximum price: " + String.format("%.2f",company.getMaxPrice()/100.0));
        items.add("Minimum price: " + String.format("%.2f",company.getMinPrice()/100.0));
        items.add("Profit: " + company.getProfit());
        items.add("Revenue: " + company.getRevenue());
        items.add("Capital: " + company.getCapital());
        items.add("Trading volume: " + company.getTradingVolume());
        items.add("Total sales: " + company.getTotalSales());
        ListView listView = new ListView(items);

        TableView table = makeMarketClientWallet(company);

        return new VBox(listView, table);
    }
    private static void initInvestorFoundsList(Scene scene)
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(ControlPanel.getInstance().getAllInvestmentFoundNames());
        ListView list = (ListView) scene.lookup("#InvestmentFoundList");
        list.setItems(items);
        list.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (ov, old_val, new_val) -> mainTabs.getTabs().add(makeInvestmentFoundTab(new_val)));
    }

    private static Tab makeInvestmentFoundTab(String investmentFoundName) {
        Tab tab = new Tab();
        tab.setText(investmentFoundName);

        InvestmentFound investmentFound = ControlPanel.getInstance().getInvestmentFound(investmentFoundName);
        VBox vBox = prepareInvestmentFoundTabContent(investmentFound);

        tab.setContent(vBox);
        return tab;
    }

    private static VBox prepareInvestmentFoundTabContent(InvestmentFound investmentFound) {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Name: " + investmentFound.getName());
        items.add("ClientID: " + investmentFound.getClientID());
        items.add("Manager: " + investmentFound.getManagerName() + " " + investmentFound.getManagerSurname());
        items.add("Investors needed: " + investmentFound.getInvestorsNeeded());
        items.add("To buy list: " + investmentFound.getToBuy().toString());
        ListView listView = new ListView(items);

        TableView table = makeMarketClientWallet(investmentFound);

        return new VBox(listView, table);
    }

    public static class ProductWithPrice
    {
        private final SimpleStringProperty productName;
        private final SimpleStringProperty price;
        public ProductWithPrice(String productName, Integer price)
        {
            this.productName = new SimpleStringProperty( productName);
            this.price = new SimpleStringProperty(String.format("%.2f",price/100.0));

        }

        public String getProductName() {
            return productName.get();
        }

        public SimpleStringProperty productNameProperty() {
            return productName;
        }

        public String getPrice() {
            return price.get();
        }

        public SimpleStringProperty priceProperty() {
            return price;
        }
    }
    private static TableView makeMarketClientWallet(MarketClient marketClient) {
        TableView table = new TableView();
        TableColumn name = new TableColumn("Valuable name");
        name.setCellValueFactory(new PropertyValueFactory<WalletData, String>("valuableName"));
        TableColumn amount = new TableColumn("Amount");
        amount.setCellValueFactory(new PropertyValueFactory<WalletData, String>("amount"));
        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<WalletData, String>("price"));
        TableColumn totalPrice = new TableColumn("Total price");
        totalPrice.setCellValueFactory(new PropertyValueFactory<WalletData, String>("totalPrice"));

        ObservableList<WalletData> data = FXCollections.observableArrayList();
        for(Pair<String, Integer> pair : marketClient.getWallet())
        {
            data.add(new WalletData(ControlPanel.getInstance().getValuable(pair.getKey()), pair.getValue()));
        }

        table.setItems(data);
        table.getColumns().addAll(name,amount, price, totalPrice);
        return table;
    }
    public static class WalletData
    {
        private final SimpleStringProperty valuableName;
        private final SimpleStringProperty amount;
        private final SimpleStringProperty price;
        private final SimpleStringProperty totalPrice;

        public WalletData(Valuable valuable, Integer amount)
        {
            this.valuableName = new SimpleStringProperty(valuable.getName());
            this.amount = new SimpleStringProperty(amount.toString());
            this.price = new SimpleStringProperty(String.format("%.2f",valuable.getPrice()/100.0));
            this.totalPrice = new SimpleStringProperty(String.format("%.2f",valuable.getPrice()/100.0*amount));
        }

        public String getValuableName() {
            return valuableName.get();
        }

        public SimpleStringProperty valuableNameProperty() {
            return valuableName;
        }

        public String getAmount() {
            return amount.get();
        }

        public SimpleStringProperty amountProperty() {
            return amount;
        }

        public String getPrice() {
            return price.get();
        }

        public SimpleStringProperty priceProperty() {
            return price;
        }

        public String getTotalPrice() {
            return totalPrice.get();
        }

        public SimpleStringProperty totalPriceProperty() {
            return totalPrice;
        }
    }
}