package app.javaFX;

import app.controls.ControlPanel;
import app.controls.FileInput;
import app.exceptions.AppInputException;
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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class ApplicationController
{
    private static TabPane mainTabs;
    private static Scene mainScene;

    public static void Init(Scene scene)
    {
        mainScene = scene;
        FileInput.readFromBasicFile();
        mainTabs = (TabPane) mainScene.lookup("#MainTabs");
        updateMarketsList();
        initInvestorsList();
        initCompaniesList();
        initInvestorFoundsList();
    }

    private static void updateMarketsList()
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(ControlPanel.getInstance().getAllMarketNames());
        ListView list = (ListView) mainScene.lookup("#MarketList");
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
        name.setPrefWidth(300);
        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<ProductWithPrice, String>("price"));
        price.setPrefWidth(200);

        ObservableList<ProductWithPrice> data = FXCollections.observableArrayList();
        for(Pair<String, Integer> pair : market.getProductsWithPrices())
        {
            data.add(new ProductWithPrice(pair.getKey(), pair.getValue()));
        }

        table.setItems(data);
        table.getColumns().addAll(name, price);
        return new VBox(listView, table);
    }
    private static void initInvestorsList()
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(ControlPanel.getInstance().getAllInvestorNames());
        ListView list = (ListView) mainScene.lookup("#InvestorList");
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

    private static void initCompaniesList()
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(ControlPanel.getInstance().getAllCompanyNames());
        ListView list = (ListView) mainScene.lookup("#CompanyList");
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
    private static void initInvestorFoundsList()
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(ControlPanel.getInstance().getAllInvestmentFoundNames());
        ListView list = (ListView) mainScene.lookup("#InvestmentFoundList");
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

    @FXML
    public void transactionPerSecondConfirmClicked(Event e)
    {
        TextField textField = (TextField)mainScene.lookup("#TransactionsPerSecond");
        Label message = (Label) mainScene.lookup("#TransactionsPerSecondMessage");
        message.setText("");
        try {
            ControlPanel.getInstance().setTransactionsPerSecond(textField.getText());
            message.setText("Correctly changed transactions per second to: " + ControlPanel.getInstance().getTransactionsPerSecond());
        }catch(Exception exception)
        {
            message.setText(exception.getMessage());
        }
        textField.clear();
        textField.setText(ControlPanel.getInstance().getTransactionsPerSecond().toString());
    }

    @FXML
    public void bullBearRatioConfirmClicked(Event e)
    {
        TextField textField = (TextField)mainScene.lookup("#BullBearRatio");
        Label message = (Label) mainScene.lookup("#BullBearRatioMessage");
        message.setText("");
        try {
            ControlPanel.getInstance().setBullBearRatio(textField.getText());
            message.setText("Correctly changed bull-bear ratio to: " + ControlPanel.getInstance().getBullBearRatio());
        }catch(Exception exception)
        {
            message.setText(exception.getMessage());
        }
        textField.clear();
        textField.setText(ControlPanel.getInstance().getBullBearRatio().toString());
    }
    @FXML
    public void addCurrencyMarketClicked(ActionEvent actionEvent) {
        addMarket("Currency");
    }
    @FXML
    public void addStockMarketClicked(ActionEvent actionEvent) {
        addMarket("Stock");
    }
    @FXML
    public void addCommodityMarketClicked(ActionEvent actionEvent) {
        addMarket("Commodity");
    }
    private void addMarket(String type)
    {
        TextField nameField = (TextField) mainScene.lookup("#" + type + "MarketName");
        Label nameMessage = (Label) mainScene.lookup("#" + type + "MarketNameMessage");
        nameMessage.setText("");
        TextField marginFeeField = (TextField) mainScene.lookup("#" + type + "MarketMarginFee");
        Label marginFeeMessage = (Label) mainScene.lookup("#" + type + "MarketMarginFeeMessage");
        marginFeeMessage.setText("");
        TextField currencyField = (TextField) mainScene.lookup("#" + type + "MarketCurrency");
        Label currencyMessage = (Label) mainScene.lookup("#" + type + "MarketCurrencyMessage");
        currencyMessage.setText("");
        try{
            switch (type)
            {
                case "Stock" -> ControlPanel.getInstance().getGenerator().generateStockMarket(nameField.getText(), marginFeeField.getText(), currencyField.getText());
                case "Currency" -> ControlPanel.getInstance().getGenerator().generateCurrencyMarket(nameField.getText(), marginFeeField.getText(), currencyField.getText());
                case "Commodity" -> ControlPanel.getInstance().getGenerator().generateCommodityMarket(nameField.getText(), marginFeeField.getText(), currencyField.getText());
            }

            nameField.clear();
            marginFeeField.clear();
            currencyField.clear();
        }catch (AppInputException exception)
        {
            switch (exception.field) {
                case "name" -> nameMessage.setText(exception.getMessage());
                case "currency" -> currencyMessage.setText(exception.getMessage());
                case "marginFee" -> marginFeeMessage.setText(exception.getMessage());
                default -> nameMessage.setText("invalid exception: " + exception.getMessage());
            }
        }
        updateMarketsList();
    }
    @FXML
    public void addInvestorClicked(ActionEvent actionEvent) {
        addMarketClient("Investor");
    }
    @FXML
    public void addCompanyClicked(ActionEvent actionEvent) {
        addMarketClient("Company");
    }
    @FXML
    public void addInvestmentFoundClicked(ActionEvent actionEvent) {
        addMarketClient("InvestmentFound");
    }
    private void addMarketClient(String type)
    {
        TextField field = (TextField) mainScene.lookup("#" + type + "Name");
        Label message = (Label) mainScene.lookup("#" + type + "NameMessage");
        message.setText("");
        try{
            switch (type)
            {
                case "Investor" -> ControlPanel.getInstance().getGenerator().generateInvestor(field.getText());
                case "Company" -> ControlPanel.getInstance().getGenerator().generateCompany(field.getText());
                case "InvestmentFound" -> ControlPanel.getInstance().getGenerator().generateInvestmentFound(field.getText());
            }
            field.clear();
        }catch (AppInputException exception)
        {
            message.setText(exception.getMessage());
        }
    }
    @FXML
    public void addCurrencyClicked(ActionEvent actionEvent) {
        TextField nameField = (TextField)mainScene.lookup("#CurrencyName");
        Label nameMessage = (Label) mainScene.lookup("#CurrencyNameMessage");
        nameMessage.setText("");
        TextField priceField = (TextField)mainScene.lookup("#CurrencyPrice");
        Label priceMessage = (Label) mainScene.lookup("#CurrencyPriceMessage");
        nameMessage.setText("");
        try{
            ControlPanel.getInstance().getGenerator().generateCurrency(nameField.getText(), priceField.getText());
        }catch (AppInputException exception)
        {
            switch (exception.field) {
                case "name" -> nameMessage.setText(exception.getMessage());
                case "price" -> priceMessage.setText(exception.getMessage());
                default -> nameMessage.setText("invalid exception: " + exception.getMessage());
            }
        }

    }
    @FXML
    public void addCommodityClicked(ActionEvent actionEvent) {
        TextField nameField = (TextField)mainScene.lookup("#CommodityName");
        Label nameMessage = (Label) mainScene.lookup("#CommodityNameMessage");
        nameMessage.setText("");
        TextField priceField = (TextField)mainScene.lookup("#CommodityPrice");
        Label priceMessage = (Label) mainScene.lookup("#CommodityPriceMessage");
        nameMessage.setText("");
        TextField unitField = (TextField)mainScene.lookup("#CommodityUnit");
        Label unitMessage = (Label) mainScene.lookup("#CommodityUnitMessage");
        nameMessage.setText("");
        try{
            ControlPanel.getInstance().getGenerator().generateCommodity(nameField.getText(), priceField.getText(), unitField.getText());
        }catch (AppInputException exception)
        {
            switch (exception.field) {
                case "name" -> nameMessage.setText(exception.getMessage());
                case "price" -> priceMessage.setText(exception.getMessage());
                case "unit" -> unitMessage.setText(exception.getMessage());
                default -> nameMessage.setText("invalid exception: " + exception.getMessage());
            }
        }
    }
    @FXML
    public void addIndexClicked(ActionEvent actionEvent) {
        TextField nameField = (TextField)mainScene.lookup("#IndexName");
        Label nameMessage = (Label) mainScene.lookup("#IndexNameMessage");
        nameMessage.setText("");
        TextField priceField = (TextField)mainScene.lookup("#IndexPrice");
        Label priceMessage = (Label) mainScene.lookup("#IndexPriceMessage");
        nameMessage.setText("");
        TextField marketField = (TextField)mainScene.lookup("#IndexMarketName");
        Label marketMessage = (Label) mainScene.lookup("#IndexMarketNameMessage");
        nameMessage.setText("");
        TextField noCompaniesField = (TextField)mainScene.lookup("#IndexNOCompanies");
        Label noCompaniesMessage = (Label) mainScene.lookup("#IndexNOCompaniesMessage");
        nameMessage.setText("");
        try{
            ControlPanel.getInstance().getGenerator().generateIndex(nameField.getText(), priceField.getText(), marketField.getText(), noCompaniesField.getText());
        }catch (AppInputException exception)
        {
            switch (exception.field) {
                case "name" -> nameMessage.setText(exception.getMessage());
                case "price" -> priceMessage.setText(exception.getMessage());
                case "market" -> marketMessage.setText(exception.getMessage());
                case "noCompanies" -> noCompaniesMessage.setText(exception.getMessage());
                default -> nameMessage.setText("invalid exception: " + exception.getMessage());
            }
        }
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
        name.setPrefWidth(200);
        TableColumn amount = new TableColumn("Amount");
        amount.setCellValueFactory(new PropertyValueFactory<WalletData, String>("amount"));
        amount.setPrefWidth(200);
        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<WalletData, String>("price"));
        price.setPrefWidth(200);
        TableColumn totalPrice = new TableColumn("Total price");
        totalPrice.setCellValueFactory(new PropertyValueFactory<WalletData, String>("totalPrice"));
        totalPrice.setPrefWidth(200);

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