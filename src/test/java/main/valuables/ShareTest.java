package main.valuables;

import app.controls.ControlPanel;
import app.exceptions.WrongValuableParamException;
import app.world.Company;
import app.valuables.Share;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShareTest {
    Share share;
    Company company;


    @Test
    void constructorTest()
    {
        setUpValid();
        assertEquals("CD Projekt SA", share.getName());
        assertEquals(100, share.getPrice());
        assertEquals(company, share.getCompany());
        cleanUpValid();
    }
    @Test
    void constructorExceptionsTest()
    {
        assertThrows(WrongValuableParamException.class, () -> new Share("", 100));
        assertThrows(WrongValuableParamException.class, () -> new Share("CD Projekt SA", 0));
        assertThrows(WrongValuableParamException.class, () -> new Share("CD Projekt SA", -100));
        assertThrows(WrongValuableParamException.class, () -> new Share("CD Projekt SA", 100));
    }

    void setUpValid()
    {
        try {
            company  = new Company("CD Projekt SA", "13.09.1775", 30, 40, 50, 30, 687.9f, 789.0f,56.8f, 300, 132.4f);
            share = new Share("CD Projekt SA", 100);
        } catch (WrongValuableParamException e) {
            System.out.println("failed set up valid");
        }
    }

    void cleanUpValid()
    {
        ControlPanel.getInstance().removeCompany(company.getName());
        ControlPanel.getInstance().removeShare(share.getName());
    }
}
