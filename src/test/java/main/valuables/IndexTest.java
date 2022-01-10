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
    void constructorExceptionsTest()
    {
        ArrayList<String> validCompanies = new ArrayList<>();
        validCompanies.add("x");
        assertThrows(WrongValuableParamException.class, () -> new Index("", 100, validCompanies));
        assertThrows(WrongValuableParamException.class, () -> new Index("wig20", -20, validCompanies));
        assertThrows(WrongValuableParamException.class, () -> new Index("wig20", 0, validCompanies));
        assertThrows(WrongValuableParamException.class, () -> new Index("wig20", 100, new ArrayList<>()));
    }

}
