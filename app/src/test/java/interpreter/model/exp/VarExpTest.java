package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VarExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();

    @Test
    void undefinedVariableThrows() {
        IExp e = new VarExp("missing");
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable));
        assertEquals("variable missing is not defined", ex.getMessage());
    }
}