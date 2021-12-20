package main.valuables;

import app.exceptions.wrongValuableParamException;
import app.world.Company;
import app.valuables.Share;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShareTest {
    Share share;


    @Test
    void constructorValidTest()
    {
        setUpValid();
        assertEquals("CD Projekt SA", share.getName());
        assertEquals(100, share.getPrice());
    }
    @Test
    void constructorExceptionsTest()
    {
        ArrayList<Company> validCompanies = new ArrayList<>();
        validCompanies.add(new Company());
        assertThrows(wrongValuableParamException.class, () -> new Share("", 100,  new Company()));
        assertThrows(wrongValuableParamException.class, () -> new Share("CD Projekt SA", 0,  new Company()));
        assertThrows(wrongValuableParamException.class, () -> new Share("CD Projekt SA", -100,  new Company()));
        assertThrows(wrongValuableParamException.class, () -> new Share("CD Projekt SA", 100,  null));
    }
    void setUpValid()
    {
        try {
            share = new Share("CD Projekt SA", 100,  new Company());
        }catch(wrongValuableParamException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
