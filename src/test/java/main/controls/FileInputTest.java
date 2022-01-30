package main.controls;

import app.controls.ControlPanel;
import app.controls.FileInput;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class FileInputTest {
    @Test
    public void BasicInputTest()
    {
        FileInput.readFromBasicFile();

        assertTrue(ControlPanel.getInstance().companyExist("Orlen"));
        assertTrue(ControlPanel.getInstance().companyExist("Lotos"));
        assertTrue(ControlPanel.getInstance().companyExist("Orlen&Lotos"));

        assertTrue(ControlPanel.getInstance().investorExist("Najman"));

        assertTrue(ControlPanel.getInstance().currencyExist("zloty"));
        assertTrue(ControlPanel.getInstance().currencyExist("euro"));
        assertTrue(ControlPanel.getInstance().currencyExist("franc"));
        assertTrue(ControlPanel.getInstance().currencyExist("dollar"));
        assertTrue(ControlPanel.getInstance().currencyExist("pound"));

        assertTrue(ControlPanel.getInstance().commodityExist("gold"));
        assertTrue(ControlPanel.getInstance().commodityExist("silver"));
        assertTrue(ControlPanel.getInstance().commodityExist("platinum"));
        assertTrue(ControlPanel.getInstance().commodityExist("palladium"));
        assertTrue(ControlPanel.getInstance().commodityExist("oil"));
        assertTrue(ControlPanel.getInstance().commodityExist("uranium"));

        assertTrue(ControlPanel.getInstance().shareExist("Orlen"));
        assertTrue(ControlPanel.getInstance().shareExist("Lotos"));
        assertTrue(ControlPanel.getInstance().shareExist("Orlen&Lotos"));

        assertTrue(ControlPanel.getInstance().indexExist("pap3"));

        assertTrue(ControlPanel.getInstance().marketExist("coins"));
        assertTrue(ControlPanel.getInstance().marketExist("stuff"));
        assertTrue(ControlPanel.getInstance().marketExist("papers"));

        assertTrue(ControlPanel.getInstance().investmentFundExist("great_idea"));

        ControlPanel.removeInstance();
    }
}
