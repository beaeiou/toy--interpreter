package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();

        if (!symTable.isDefined(varName)) {
            throw new StatementException("readFile: variable " + varName + " is not defined");
        }
        if (!symTable.lookup(varName).getType().equals(new IntType())) {
            throw new StatementException("readFile: variable " + varName + " is not of type int");
        }

        if (!(exp.eval(symTable) instanceof StringValue fileName)) {
            throw new StatementException("readFile: expression is not a string");
        }

        var fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName)) {
            throw new StatementException("readFile: file " + fileName + " is not in the FileTable");
        }

        try {
            BufferedReader reader = fileTable.lookup(fileName);
            String line = reader.readLine();
            int val = 0;
            if (line != null) {
                val = Integer.parseInt(line.strip());
            }
            symTable.update(varName, new IntValue(val));
        } catch (IOException e) {
            throw new StatementException("readFile: I/O error: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new StatementException("readFile: line is not an integer: " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}