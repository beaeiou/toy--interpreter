package interpreter.model.type;

import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTypeTest {

    @Test
    void stringTypeProvidesEmptyStringDefaultValue() {
        IType stringType = new StringType();
        assertEquals(new StringValue(""), stringType.defaultValue());
    }

    @Test
    void stringValuesWithSameContentAreEqualAndHaveSameHashCode() {
        StringValue val1 = new StringValue("test.in");
        StringValue val2 = new StringValue("test.in");
        StringValue val3 = new StringValue("other.in");

        assertEquals(val1, val2);
        assertNotEquals(val1, val3);

        assertEquals(val1.hashCode(), val2.hashCode());
        assertNotEquals(val1.hashCode(), val3.hashCode());
    }
}