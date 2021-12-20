package main.valuables;

import app.exceptions.wrongValuableParamException;
import app.valuables.Currency;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyTest {
    Currency currency;


    @Test
    void constructorValidTest()
    {
        setUpValid();
        assertEquals("euro",currency.getName());
        assertEquals(100, currency.getPrice());
    }
    @Test
    void constructorExceptionsTest()
    {
        assertThrows(wrongValuableParamException.class, () -> new Currency("", 100, new ArrayList<>()));
        assertThrows(wrongValuableParamException.class, () -> new Currency("euro", -20, new ArrayList<>()));
        assertThrows(wrongValuableParamException.class, () -> new Currency("euro", 0, new ArrayList<>()));
    }
    void setUpValid()
    {
        try {
            currency = new Currency("euro", 100, new ArrayList<>() );
        }catch(wrongValuableParamException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
