package main.valuables;

import app.controls.ControlPanel;
import app.exceptions.WrongValuableParamException;
import app.valuables.Index;
import app.world.Company;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IndexTest {
    Index index;
    Company company1;
    Company company2;

    @Test
    void constructorTest()
    {
        setUpValid();
        assertEquals("CD Projekt SA x2", index.getName());
        assertEquals(200, index.getPrice());
        cleanUpValid();
    }
    @Test
    void constructorExceptionsTest()
    {
        setUpValid();
        ArrayList<String> validCompanies = new ArrayList<>();
        validCompanies.add("CD Projekt SA");
        validCompanies.add("not CD Projekt SA");
        assertThrows(WrongValuableParamException.class, () -> new Index("", 100, validCompanies));
        assertThrows(WrongValuableParamException.class, () -> new Index("wig20", -20, validCompanies));
        assertThrows(WrongValuableParamException.class, () -> new Index("wig20", 0, validCompanies));
        assertThrows(WrongValuableParamException.class, () -> new Index("wig20", 100, new ArrayList<>()));
        assertDoesNotThrow(() -> new Index("wig20", 100, validCompanies));

        ArrayList<String> companies = new ArrayList<>();
        companies.add("CD Projekt");
        companies.add("not CD Projekt");
        assertThrows(WrongValuableParamException.class, () -> new Index("wig20", 100, companies));
        cleanUpValid();
    }
    void setUpValid()
    {
        company1  = new Company("CD Projekt SA", "13.09.1775", 30, 40, 50, 30, 687.9f, 789.0f,56.8f, 300, 132.4f);
        company2  = new Company("not CD Projekt SA", "13.09.1775", 30, 40, 50, 30, 687.9f, 789.0f,56.8f, 300, 132.4f);
        ArrayList<String> companies = new ArrayList<>();
        companies.add("CD Projekt SA");
        companies.add("not CD Projekt SA");
        try {
            index = new Index("CD Projekt SA x2", 200, companies);
        } catch (WrongValuableParamException e) {
            System.out.println("failed set up valid");
        }
    }
    void cleanUpValid()
    {
        ControlPanel.getInstance().removeCompany(company1.getName());
        ControlPanel.getInstance().removeCompany(company2.getName());
        ControlPanel.getInstance().removeIndex(index.getName());
    }

}
