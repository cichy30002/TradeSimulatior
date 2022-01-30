package app.javaFX;

import app.controls.ControlPanel;
import app.controls.FileInput;
import app.exceptions.AppInputException;
import app.markets.Market;
import app.valuables.Valuable;
import app.world.Company;
import app.world.InvestmentFund;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class ApplicationController
{
    private static TabPane mainTabs;
    private static Scene mainScene;

    /**
     * Initialize all javafx stuff to start application.
     * @param scene
     */
    public static void Init(Scene scene)
    {
        mainScene = scene;
        FileInput.readFromBasicFile();
        mainTabs = (TabPane) mainScene.lookup("#MainTabs");
        initLists();
    }

    private static void initLists() {
        initMarketsList();
        initInvestorsList();
        initCompaniesList();
        initInvestorFundsList();
        initShareList();
        initIndexList();
        initCommodityList();
        initCurrencyList();
    }

    private static void initShareList()
    {
        ObservableList<String> items = ControlPanel.getInstance().getShareNames();
        ListView list = (ListView) mainScene.lookup("#ShareList");
        list.setItems(items);

        list.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (ov, old_val, new_val) -> mainTabs.getTabs().add(makePlotTab(new_val)));
    }

    private static void initIndexList()
    {
        ObservableList<String> items = ControlPanel.getInstance().getIndexNames();
        ListView list = (ListView) mainScene.lookup("#IndexList");
        list.setItems(items);

        list.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (ov, old_val, new_val) -> mainTabs.getTabs().add(makePlotTab(new_val)));
    }

    private static void initCurrencyList()
    {
        ObservableList<String> items = ControlPanel.getInstance().getCurrencyNames();
        ListView list = (ListView) mainScene.lookup("#CurrencyList");
        list.setItems(items);

        list.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (ov, old_val, new_val) -> mainTabs.getTabs().add(makePlotTab(new_val)));
    }
    private static void initCommodityList()
    {
        ObservableList<String> items = ControlPanel.getInstance().getCommodityNames();
        ListView list = (ListView) mainScene.lookup("#CommodityList");
        list.setItems(items);

        list.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (ov, old_val, new_val) -> mainTabs.getTabs().add(makePlotTab(new_val)));
    }
    private static Tab makePlotTab(String valuableName) {
        Tab tab = new Tab();
        tab.setText(valuableName + "plot");

        tab.setContent(drawSinglePlot(valuableName, ControlPanel.getInstance().getValuable(valuableName).getPriceHistory()));
        return tab;
    }
    private static LineChart<Number, Number> drawSinglePlot(String valuableName, ArrayList<Integer> data)
    {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(valuableName);
        XYChart.Series series = new XYChart.Series();
        series.setName("Price");
        for(int i=0;i<data.size();i++)
        {
            series.getData().add(new XYChart.Data(i, data.get(i)));
        }

        lineChart.getData().add(series);
        return lineChart;
    }

    /**
     * Makes new tab with multi valuable plot and adds it to main window.
     * @param actionEvent
     */
    public void makeMultiPlot(ActionEvent actionEvent) {
        TextField field = (TextField) mainScene.lookup("#ValuableNames");
        Label message = (Label) mainScene.lookup("#ValuableNamesMessage");
        message.setText("");
        ArrayList<String> valuableNames = new ArrayList<>(Arrays.asList(field.getText().split(";")));
        try {
            mainTabs.getTabs().add(makeMultiPlotTab(valuableNames));
            field.clear();
        }catch (AppInputException e)
        {
            message.setText(e.getMessage());
        }
    }

    private Tab makeMultiPlotTab(ArrayList<String> valuableNames) throws AppInputException {
        Tab tab = new Tab();
        tab.setText("Multi plot");
        for (String valuableName : valuableNames)
        {
            if(!ControlPanel.getInstance().valuableExist(valuableName))
            {
                throw new AppInputException("One of valuables does not exist: " + valuableName);
            }
        }
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Multi-plot");
        for(String valuableName:valuableNames)
        {
            ArrayList<Integer> data = ControlPanel.getInstance().getValuable(valuableName).getPriceHistory();
            XYChart.Series series = new XYChart.Series();
            ArrayList<Float> percentageData = changeDataToPercentScale(data);

            for(int i=0;i<percentageData.size();i++)
            {
                series.getData().add(new XYChart.Data(i, percentageData.get(i)));
            }
            series.setName(valuableName);
            lineChart.getData().add(series);
        }
        tab.setContent(lineChart);
        return tab;
    }

    private ArrayList<Float> changeDataToPercentScale(ArrayList<Integer> data)
    {
        ArrayList<Float> result = new ArrayList<>();
        for(int i= 1; i<data.size();i++)
        {
            result.add(((float)data.get(i)/(float)data.get(i-1) - 1) * 100);
        }
        return result;
    }
    private static void initMarketsList()
    {
        ObservableList<String> items = ControlPanel.getInstance().getMarketNames();
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
        ObservableList<String> items = ControlPanel.getInstance().getInvestorNames();
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
        ObservableList<String> items = ControlPanel.getInstance().getCompanyNames();
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
        items.add("Opening price: " + company.getOpeningPrice());
        items.add("Maximum price: " + company.getMaxPrice());
        items.add("Minimum price: " + company.getMinPrice());
        items.add("Profit: " + company.getProfit());
        items.add("Revenue: " + company.getRevenue());
        items.add("Capital: " + company.getCapital());
        items.add("Trading volume: " + company.getTradingVolume());
        items.add("Total sales: " + company.getTotalSales());
        ListView listView = new ListView(items);

        TableView table = makeMarketClientWallet(company);

        return new VBox(listView, table);
    }
    private static void initInvestorFundsList()
    {
        ObservableList<String> items = ControlPanel.getInstance().getInvestmentFundNames();
        ListView list = (ListView) mainScene.lookup("#InvestmentFundList");
        list.setItems(items);
        list.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (ov, old_val, new_val) -> mainTabs.getTabs().add(makeInvestmentFundTab(new_val)));
    }

    private static Tab makeInvestmentFundTab(String investmentFundName) {
        Tab tab = new Tab();
        tab.setText(investmentFundName);

        InvestmentFund investmentFund = ControlPanel.getInstance().getInvestmentFund(investmentFundName);
        VBox vBox = prepareInvestmentFundTabContent(investmentFund);

        tab.setContent(vBox);
        return tab;
    }

    private static VBox prepareInvestmentFundTabContent(InvestmentFund investmentFund) {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Name: " + investmentFund.getName());
        items.add("ClientID: " + investmentFund.getClientID());
        items.add("Manager: " + investmentFund.getManagerName() + " " + investmentFund.getManagerSurname());
        ListView listView = new ListView(items);
        TableView table = makeMarketClientWallet(investmentFund);

        return new VBox(listView, table);
    }


    /**
     * Handle change of transaction per second through GUI.
     * @param e
     */
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
    /**
     * Handle change of bull/bear ratio through GUI.
     * @param e
     */
    @FXML
    public void bullBearRatioConfirmClicked(Event e)
    {
        TextField textField = (TextField)mainScene.lookup("#BullBearRatio");
        Label message = (Label) mainScene.lookup("#BullBearRatioMessage");
        message.setText("");
        try {
            ControlPanel.getInstance().setBullBearRatio(textField.getText());
            message.setText("Correctly changed bull-bear ratio to: " + ControlPanel.getInstance().getBullBearRatio());
            textField.clear();
        }catch(Exception exception)
        {
            message.setText(exception.getMessage());
        }
        textField.clear();
        textField.setText(ControlPanel.getInstance().getBullBearRatio().toString());

    }

    /**
     * handle adding new currency market through GUI
     * @param actionEvent
     */
    @FXML
    public void addCurrencyMarketClicked(ActionEvent actionEvent) {
        addMarket("Currency");
    }
    /**
     * handle adding new stock market through GUI
     * @param actionEvent
     */
    @FXML
    public void addStockMarketClicked(ActionEvent actionEvent) {
        addMarket("Stock");
    }
    /**
     * handle adding new commodity market through GUI
     * @param actionEvent
     */
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
    }
    /**
     * handle adding new investor through GUI
     * @param actionEvent
     */
    @FXML
    public void addInvestorClicked(ActionEvent actionEvent) {
        addMarketClient("Investor");
    }
    /**
     * handle adding new company through GUI
     * @param actionEvent
     */
    @FXML
    public void addCompanyClicked(ActionEvent actionEvent) {
        addMarketClient("Company");
    }
    /**
     * handle adding new investment fund through GUI
     * @param actionEvent
     */
    @FXML
    public void addInvestmentFundClicked(ActionEvent actionEvent) {
        addMarketClient("InvestmentFund");
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
                case "InvestmentFund" -> ControlPanel.getInstance().getGenerator().generateInvestmentFund(field.getText());
            }
            field.clear();
        }catch (AppInputException exception)
        {
            message.setText(exception.getMessage());
        }
    }
    /**
     * handle adding new currency through GUI
     * @param actionEvent
     */
    @FXML
    public void addCurrencyClicked(ActionEvent actionEvent) {
        TextField nameField = (TextField)mainScene.lookup("#CurrencyName");
        Label nameMessage = (Label) mainScene.lookup("#CurrencyNameMessage");
        nameMessage.setText("");
        TextField priceField = (TextField)mainScene.lookup("#CurrencyPrice");
        Label priceMessage = (Label) mainScene.lookup("#CurrencyPriceMessage");
        priceMessage.setText("");
        try{
            ControlPanel.getInstance().getGenerator().generateCurrency(nameField.getText(), priceField.getText());
            nameField.clear();
            priceField.clear();
        }catch (AppInputException exception)
        {
            switch (exception.field) {
                case "name" -> nameMessage.setText(exception.getMessage());
                case "price" -> priceMessage.setText(exception.getMessage());
                default -> nameMessage.setText("invalid exception: " + exception.getMessage());
            }
        }
    }
    /**
     * handle adding new commodity through GUI
     * @param actionEvent
     */
    @FXML
    public void addCommodityClicked(ActionEvent actionEvent) {
        TextField nameField = (TextField)mainScene.lookup("#CommodityName");
        Label nameMessage = (Label) mainScene.lookup("#CommodityNameMessage");
        nameMessage.setText("");
        TextField priceField = (TextField)mainScene.lookup("#CommodityPrice");
        Label priceMessage = (Label) mainScene.lookup("#CommodityPriceMessage");
        priceMessage.setText("");
        TextField unitField = (TextField)mainScene.lookup("#CommodityUnit");
        Label unitMessage = (Label) mainScene.lookup("#CommodityUnitMessage");
        unitMessage.setText("");
        try{
            ControlPanel.getInstance().getGenerator().generateCommodity(nameField.getText(), priceField.getText(), unitField.getText());
            nameField.clear();
            priceField.clear();
            unitField.clear();
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
    /**
     * handle adding new index through GUI
     * @param actionEvent
     */
    @FXML
    public void addIndexClicked(ActionEvent actionEvent) {
        TextField nameField = (TextField)mainScene.lookup("#IndexName");
        Label nameMessage = (Label) mainScene.lookup("#IndexNameMessage");
        nameMessage.setText("");
        TextField marketField = (TextField)mainScene.lookup("#IndexMarketName");
        Label marketMessage = (Label) mainScene.lookup("#IndexMarketNameMessage");
        marketMessage.setText("");
        TextField noCompaniesField = (TextField)mainScene.lookup("#IndexNOCompanies");
        Label noCompaniesMessage = (Label) mainScene.lookup("#IndexNOCompaniesMessage");
        noCompaniesMessage.setText("");
        try{
            ControlPanel.getInstance().getGenerator().generateIndex(nameField.getText(), marketField.getText(), noCompaniesField.getText());
            nameField.clear();

            marketField.clear();
            noCompaniesField.clear();
        }catch (AppInputException exception)
        {
            switch (exception.field) {
                case "name" -> nameMessage.setText(exception.getMessage());
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
            this.price = new SimpleStringProperty(price.toString());

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
            this.price = new SimpleStringProperty(String.valueOf(valuable.getPrice()));
            this.totalPrice = new SimpleStringProperty(String.valueOf(valuable.getPrice()*amount));
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