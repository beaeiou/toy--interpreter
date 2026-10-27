package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }

    @Test
    void putAndLookupWorkCorrectly() throws ADTException {
        dict.put("v", 10);
        assertTrue(dict.isDefined("v"));
        assertEquals(10, dict.lookup("v"));
    }

    @Test
    void updateChangesExistingValue() throws ADTException {
        dict.put("v", 10);
        dict.update("v", 20);
        assertEquals(20, dict.lookup("v"));
    }

    @Test
    void lookupOnMissingKeyThrowsADTException() {
        ADTException exception = assertThrows(ADTException.class, () -> dict.lookup("missing"));
        assertEquals("Key not found in dictionary", exception.getMessage());
    }
}