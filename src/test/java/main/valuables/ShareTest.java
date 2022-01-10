package main.valuables;

import app.exceptions.WrongValuableParamException;
import app.world.Company;
import app.valuables.Share;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShareTest {
    Share share;



    @Test
    void constructorExceptionsTest()
    {
        assertThrows(WrongValuableParamException.class, () -> new Share("", 100));
        assertThrows(WrongValuableParamException.class, () -> new Share("CD Projekt SA", 0));
        assertThrows(WrongValuableParamException.class, () -> new Share("CD Projekt SA", -100));
        assertThrows(WrongValuableParamException.class, () -> new Share("CD Projekt SA", 100));
    }

}
