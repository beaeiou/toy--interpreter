package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp cond, IStmt thens, IStmt elses) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = cond.eval(state.getSymTable());
        if (!(val instanceof BoolValue(boolean b))) {
            throw new StatementException("condition is not a boolean");
        }
        if (b) {
            state.getExeStack().push(thens);
        } else {
            state.getExeStack().push(elses);
        }
        return state;
    }

    @Override
    public String toString() {
        return "if " + cond + " then " + thens + " else " + elses;
    }
}