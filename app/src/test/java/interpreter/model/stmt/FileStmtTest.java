package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {
    @TempDir
    Path dir;
    private PrgState state;
    private ValueExp fileExp;

    @BeforeEach
    void setup() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "7\n");
        fileExp = new ValueExp(new StringValue(f.toString()));

        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    @Test
    void readGivesTheNextNumberThenZeroAtTheEnd() throws MyException {
        new OpenRFileStmt(fileExp).execute(state);

        new ReadFileStmt(fileExp, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));

        new ReadFileStmt(fileExp, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));

        new CloseRFileStmt(fileExp).execute(state);
    }

    @Test
    void openingTheSameFileTwiceThrows() throws MyException {
        new OpenRFileStmt(fileExp).execute(state);
        assertThrows(StatementException.class, () -> new OpenRFileStmt(fileExp).execute(state));

        new CloseRFileStmt(fileExp).execute(state);
    }

    @Test
    void closeRemovesFileFromTable() throws MyException {
        new OpenRFileStmt(fileExp).execute(state);
        assertTrue(state.getFileTable().isDefined((StringValue) fileExp.value()));

        new CloseRFileStmt(fileExp).execute(state);
        assertFalse(state.getFileTable().isDefined((StringValue) fileExp.value()));
    }

    @Test
    void closeMissingFileThrows() {
        assertThrows(StatementException.class, () -> new CloseRFileStmt(fileExp).execute(state));
    }
}