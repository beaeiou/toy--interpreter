package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void evaluatesAllOperatorsCorrectly() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LT, num(2), num(5)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LT, num(5), num(2)).eval(symTable));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LE, num(5), num(5)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LE, num(6), num(5)).eval(symTable));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.EQ, num(5), num(5)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.EQ, num(5), num(6)).eval(symTable));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.NE, num(5), num(6)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.NE, num(5), num(5)).eval(symTable));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GT, num(6), num(5)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GT, num(5), num(6)).eval(symTable));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GE, num(5), num(5)).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GE, num(4), num(5)).eval(symTable));
    }

    @Test
    void nonIntegerOperandThrowsException() {
        IExp stringExp = new ValueExp(new StringValue("test"));

        var ex1 = assertThrows(ExpressionException.class,
                () -> new RelationalExp(RelOp.LT, stringExp, num(5)).eval(symTable));
        assertEquals("first operand is not an integer", ex1.getMessage());

        var ex2 = assertThrows(ExpressionException.class,
                () -> new RelationalExp(RelOp.LT, num(5), stringExp).eval(symTable));
        assertEquals("second operand is not an integer", ex2.getMessage());
    }
}