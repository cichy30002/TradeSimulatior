package main.valuables;

import app.exceptions.WrongValuableParamException;
import app.valuables.Index;
import app.world.Company;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IndexTest {
    Index index;


    @Test
    void constructorValidTest()
    {
        setUpValid();
        assertEquals("wig20", index.getName());
        assertEquals(100, index.getPrice());
    }
    @Test
    void constructorExceptionsTest()
    {
        ArrayList<String> validCompanies = new ArrayList<>();
        validCompanies.add("x");
        assertThrows(WrongValuableParamException.class, () -> new Index("", 100, validCompanies));
        assertThrows(WrongValuableParamException.class, () -> new Index("wig20", -20, validCompanies));
        assertThrows(WrongValuableParamException.class, () -> new Index("wig20", 0, validCompanies));
        assertThrows(WrongValuableParamException.class, () -> new Index("wig20", 100, new ArrayList<>()));
    }
    void setUpValid()
    {
        try {
            ArrayList<String> companies = new ArrayList<>();
            companies.add("x");
            new Company("x");
            index = new Index("wig20", 100,  companies);
        }catch(WrongValuableParamException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
