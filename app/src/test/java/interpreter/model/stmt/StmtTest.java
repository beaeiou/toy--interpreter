package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        // Pop the original program so we have an empty stack to test specific statements
        try { state.getExeStack().pop(); } catch (Exception ignored) {}
    }

    @Test
    void varDeclStmtDeclaresVariableWithDefaultValue() throws Exception {
        IStmt stmt = new VarDeclStmt("v", new IntType());
        stmt.execute(state);
        assertTrue(state.getSymTable().isDefined("v"));
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void varDeclStmtThrowsIfAlreadyDeclared() throws Exception {
        IStmt stmt = new VarDeclStmt("v", new IntType());
        stmt.execute(state);
        var ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals("variable v is already declared", ex.getMessage());
    }

    @Test
    void assignStmtUpdatesVariable() throws Exception {
        new VarDeclStmt("v", new IntType()).execute(state);
        IStmt assign = new AssignStmt("v", new ValueExp(new IntValue(5)));
        assign.execute(state);
        assertEquals(new IntValue(5), state.getSymTable().lookup("v"));
    }

    @Test
    void assignStmtThrowsOnTypeMismatch() throws Exception {
        new VarDeclStmt("v", new IntType()).execute(state);
        IStmt assign = new AssignStmt("v", new ValueExp(new BoolValue(true)));
        var ex = assertThrows(StatementException.class, () -> assign.execute(state));
        assertEquals("declared type of variable v and type of the assigned expression do not match", ex.getMessage());
    }

    @Test
    void ifStmtPushesCorrectBranch() throws Exception {
        IStmt thenStmt = new NopStmt();
        IStmt elseStmt = new NopStmt();
        IStmt ifStmt = new IfStmt(new ValueExp(new BoolValue(true)), thenStmt, elseStmt);
        ifStmt.execute(state);
        assertEquals(thenStmt, state.getExeStack().pop());
    }
}