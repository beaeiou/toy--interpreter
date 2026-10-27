package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private IList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void addAndGetWorkCorrectly() throws ADTException {
        list.add("test1");
        list.add("test2");

        assertEquals(2, list.size());
        assertEquals("test1", list.get(0));
        assertEquals("test2", list.get(1));
    }

    @Test
    void indexOutOfBounds(){
        assertThrows(ADTException.class,() -> list.get(1));
    }

}