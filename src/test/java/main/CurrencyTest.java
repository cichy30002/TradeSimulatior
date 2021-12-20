package main;

import app.valuables.Currency;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyTest {
    @Test
    void nameTest()
    {
        Currency currency = new Currency("euro", 100, new ArrayList<String>() );
        assertEquals("euro",currency.getName());
    }
}
