package main.valuables;

import app.exceptions.WrongValuableParamException;
import app.valuables.Commodity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommodityTest {
    Commodity commodity;


    @Test
    void constructorValidTest()
    {
        setUpValid();
        assertEquals("gold", commodity.getName());
        assertEquals(100, commodity.getPrice());
    }
    @Test
    void constructorExceptionsTest()
    {
        assertThrows(WrongValuableParamException.class, () -> new Commodity("", 100, "ounce", 80, 120));
        assertThrows(WrongValuableParamException.class, () -> new Commodity("gold", 0, "ounce", 80, 120));
        assertThrows(WrongValuableParamException.class, () -> new Commodity("gold", -100, "ounce", 80, 120));
        assertThrows(WrongValuableParamException.class, () -> new Commodity("gold", 100, "", 80, 120));
        assertThrows(WrongValuableParamException.class, () -> new Commodity("gold", 100, "ounce", -80, 120));
        assertThrows(WrongValuableParamException.class, () -> new Commodity("gold", 100, "ounce", -80, 0));
        assertThrows(WrongValuableParamException.class, () -> new Commodity("gold", 100, "ounce", 80, 10));
    }
    void setUpValid()
    {
        try {
            commodity = new Commodity("gold", 100, "ounce", 80, 120);
        }catch(WrongValuableParamException e)
        {
            System.out.println(e.getMessage());
        }
    }
}